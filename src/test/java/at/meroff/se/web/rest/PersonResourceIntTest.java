package at.meroff.se.web.rest;

import at.meroff.se.CsiteApp;

import at.meroff.se.domain.Person;
import at.meroff.se.repository.PersonRepository;
import at.meroff.se.service.PersonService;
import at.meroff.se.service.dto.PersonDTO;
import at.meroff.se.service.mapper.PersonMapper;
import at.meroff.se.web.rest.errors.ExceptionTranslator;
import at.meroff.se.service.dto.PersonCriteria;
import at.meroff.se.service.PersonQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import at.meroff.se.domain.enumeration.PersonType;
/**
 * Test class for the PersonResource REST controller.
 *
 * @see PersonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsiteApp.class)
public class PersonResourceIntTest {

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final PersonType DEFAULT_TYPE = PersonType.SUPPLIER;
    private static final PersonType UPDATED_TYPE = PersonType.CUSTOMER;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonQueryService personQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPersonMockMvc;

    private Person person;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonResource personResource = new PersonResource(personService, personQueryService);
        this.restPersonMockMvc = MockMvcBuilders.standaloneSetup(personResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Person createEntity(EntityManager em) {
        Person person = new Person()
            .lastName(DEFAULT_LAST_NAME)
            .firstName(DEFAULT_FIRST_NAME)
            .type(DEFAULT_TYPE);
        return person;
    }

    @Before
    public void initTest() {
        person = createEntity(em);
    }

    @Test
    @Transactional
    public void createPerson() throws Exception {
        int databaseSizeBeforeCreate = personRepository.findAll().size();

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);
        restPersonMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isCreated());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeCreate + 1);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testPerson.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testPerson.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createPersonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personRepository.findAll().size();

        // Create the Person with an existing ID
        person.setId(1L);
        PersonDTO personDTO = personMapper.toDto(person);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPeople() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList
        restPersonMockMvc.perform(get("/api/people?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(person.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getPerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get the person
        restPersonMockMvc.perform(get("/api/people/{id}", person.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(person.getId().intValue()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getAllPeopleByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList where lastName equals to DEFAULT_LAST_NAME
        defaultPersonShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the personList where lastName equals to UPDATED_LAST_NAME
        defaultPersonShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllPeopleByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultPersonShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the personList where lastName equals to UPDATED_LAST_NAME
        defaultPersonShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllPeopleByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList where lastName is not null
        defaultPersonShouldBeFound("lastName.specified=true");

        // Get all the personList where lastName is null
        defaultPersonShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    public void getAllPeopleByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList where firstName equals to DEFAULT_FIRST_NAME
        defaultPersonShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the personList where firstName equals to UPDATED_FIRST_NAME
        defaultPersonShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllPeopleByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultPersonShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the personList where firstName equals to UPDATED_FIRST_NAME
        defaultPersonShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllPeopleByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList where firstName is not null
        defaultPersonShouldBeFound("firstName.specified=true");

        // Get all the personList where firstName is null
        defaultPersonShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    public void getAllPeopleByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList where type equals to DEFAULT_TYPE
        defaultPersonShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the personList where type equals to UPDATED_TYPE
        defaultPersonShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPeopleByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultPersonShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the personList where type equals to UPDATED_TYPE
        defaultPersonShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPeopleByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList where type is not null
        defaultPersonShouldBeFound("type.specified=true");

        // Get all the personList where type is null
        defaultPersonShouldNotBeFound("type.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPersonShouldBeFound(String filter) throws Exception {
        restPersonMockMvc.perform(get("/api/people?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(person.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPersonShouldNotBeFound(String filter) throws Exception {
        restPersonMockMvc.perform(get("/api/people?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingPerson() throws Exception {
        // Get the person
        restPersonMockMvc.perform(get("/api/people/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);
        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Update the person
        Person updatedPerson = personRepository.findOne(person.getId());
        updatedPerson
            .lastName(UPDATED_LAST_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .type(UPDATED_TYPE);
        PersonDTO personDTO = personMapper.toDto(updatedPerson);

        restPersonMockMvc.perform(put("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isOk());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPerson.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPerson.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonMockMvc.perform(put("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isCreated());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);
        int databaseSizeBeforeDelete = personRepository.findAll().size();

        // Get the person
        restPersonMockMvc.perform(delete("/api/people/{id}", person.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Person.class);
        Person person1 = new Person();
        person1.setId(1L);
        Person person2 = new Person();
        person2.setId(person1.getId());
        assertThat(person1).isEqualTo(person2);
        person2.setId(2L);
        assertThat(person1).isNotEqualTo(person2);
        person1.setId(null);
        assertThat(person1).isNotEqualTo(person2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonDTO.class);
        PersonDTO personDTO1 = new PersonDTO();
        personDTO1.setId(1L);
        PersonDTO personDTO2 = new PersonDTO();
        assertThat(personDTO1).isNotEqualTo(personDTO2);
        personDTO2.setId(personDTO1.getId());
        assertThat(personDTO1).isEqualTo(personDTO2);
        personDTO2.setId(2L);
        assertThat(personDTO1).isNotEqualTo(personDTO2);
        personDTO1.setId(null);
        assertThat(personDTO1).isNotEqualTo(personDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(personMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(personMapper.fromId(null)).isNull();
    }
}
