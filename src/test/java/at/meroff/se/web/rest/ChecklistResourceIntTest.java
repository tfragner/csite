package at.meroff.se.web.rest;

import at.meroff.se.CsiteApp;

import at.meroff.se.domain.Checklist;
import at.meroff.se.repository.ChecklistRepository;
import at.meroff.se.service.ChecklistService;
import at.meroff.se.service.dto.ChecklistDTO;
import at.meroff.se.service.mapper.ChecklistMapper;
import at.meroff.se.web.rest.errors.ExceptionTranslator;
import at.meroff.se.service.dto.ChecklistCriteria;
import at.meroff.se.service.ChecklistQueryService;

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

/**
 * Test class for the ChecklistResource REST controller.
 *
 * @see ChecklistResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsiteApp.class)
public class ChecklistResourceIntTest {

    private static final Boolean DEFAULT_IN_TIME = false;
    private static final Boolean UPDATED_IN_TIME = true;

    private static final Boolean DEFAULT_COMPLETE = false;
    private static final Boolean UPDATED_COMPLETE = true;

    private static final Boolean DEFAULT_UNLOADING_OK = false;
    private static final Boolean UPDATED_UNLOADING_OK = true;

    private static final Boolean DEFAULT_NOT_DAMAGED = false;
    private static final Boolean UPDATED_NOT_DAMAGED = true;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CLAIM = false;
    private static final Boolean UPDATED_CLAIM = true;

    @Autowired
    private ChecklistRepository checklistRepository;

    @Autowired
    private ChecklistMapper checklistMapper;

    @Autowired
    private ChecklistService checklistService;

    @Autowired
    private ChecklistQueryService checklistQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restChecklistMockMvc;

    private Checklist checklist;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChecklistResource checklistResource = new ChecklistResource(checklistService, checklistQueryService);
        this.restChecklistMockMvc = MockMvcBuilders.standaloneSetup(checklistResource)
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
    public static Checklist createEntity(EntityManager em) {
        Checklist checklist = new Checklist()
            .inTime(DEFAULT_IN_TIME)
            .complete(DEFAULT_COMPLETE)
            .unloadingOk(DEFAULT_UNLOADING_OK)
            .notDamaged(DEFAULT_NOT_DAMAGED)
            .description(DEFAULT_DESCRIPTION)
            .claim(DEFAULT_CLAIM);
        return checklist;
    }

    @Before
    public void initTest() {
        checklist = createEntity(em);
    }

    @Test
    @Transactional
    public void createChecklist() throws Exception {
        int databaseSizeBeforeCreate = checklistRepository.findAll().size();

        // Create the Checklist
        ChecklistDTO checklistDTO = checklistMapper.toDto(checklist);
        restChecklistMockMvc.perform(post("/api/checklists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checklistDTO)))
            .andExpect(status().isCreated());

        // Validate the Checklist in the database
        List<Checklist> checklistList = checklistRepository.findAll();
        assertThat(checklistList).hasSize(databaseSizeBeforeCreate + 1);
        Checklist testChecklist = checklistList.get(checklistList.size() - 1);
        assertThat(testChecklist.isInTime()).isEqualTo(DEFAULT_IN_TIME);
        assertThat(testChecklist.isComplete()).isEqualTo(DEFAULT_COMPLETE);
        assertThat(testChecklist.isUnloadingOk()).isEqualTo(DEFAULT_UNLOADING_OK);
        assertThat(testChecklist.isNotDamaged()).isEqualTo(DEFAULT_NOT_DAMAGED);
        assertThat(testChecklist.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testChecklist.isClaim()).isEqualTo(DEFAULT_CLAIM);
    }

    @Test
    @Transactional
    public void createChecklistWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = checklistRepository.findAll().size();

        // Create the Checklist with an existing ID
        checklist.setId(1L);
        ChecklistDTO checklistDTO = checklistMapper.toDto(checklist);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChecklistMockMvc.perform(post("/api/checklists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checklistDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Checklist in the database
        List<Checklist> checklistList = checklistRepository.findAll();
        assertThat(checklistList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllChecklists() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);

        // Get all the checklistList
        restChecklistMockMvc.perform(get("/api/checklists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checklist.getId().intValue())))
            .andExpect(jsonPath("$.[*].inTime").value(hasItem(DEFAULT_IN_TIME.booleanValue())))
            .andExpect(jsonPath("$.[*].complete").value(hasItem(DEFAULT_COMPLETE.booleanValue())))
            .andExpect(jsonPath("$.[*].unloadingOk").value(hasItem(DEFAULT_UNLOADING_OK.booleanValue())))
            .andExpect(jsonPath("$.[*].notDamaged").value(hasItem(DEFAULT_NOT_DAMAGED.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].claim").value(hasItem(DEFAULT_CLAIM.booleanValue())));
    }

    @Test
    @Transactional
    public void getChecklist() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);

        // Get the checklist
        restChecklistMockMvc.perform(get("/api/checklists/{id}", checklist.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(checklist.getId().intValue()))
            .andExpect(jsonPath("$.inTime").value(DEFAULT_IN_TIME.booleanValue()))
            .andExpect(jsonPath("$.complete").value(DEFAULT_COMPLETE.booleanValue()))
            .andExpect(jsonPath("$.unloadingOk").value(DEFAULT_UNLOADING_OK.booleanValue()))
            .andExpect(jsonPath("$.notDamaged").value(DEFAULT_NOT_DAMAGED.booleanValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.claim").value(DEFAULT_CLAIM.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllChecklistsByInTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);

        // Get all the checklistList where inTime equals to DEFAULT_IN_TIME
        defaultChecklistShouldBeFound("inTime.equals=" + DEFAULT_IN_TIME);

        // Get all the checklistList where inTime equals to UPDATED_IN_TIME
        defaultChecklistShouldNotBeFound("inTime.equals=" + UPDATED_IN_TIME);
    }

    @Test
    @Transactional
    public void getAllChecklistsByInTimeIsInShouldWork() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);

        // Get all the checklistList where inTime in DEFAULT_IN_TIME or UPDATED_IN_TIME
        defaultChecklistShouldBeFound("inTime.in=" + DEFAULT_IN_TIME + "," + UPDATED_IN_TIME);

        // Get all the checklistList where inTime equals to UPDATED_IN_TIME
        defaultChecklistShouldNotBeFound("inTime.in=" + UPDATED_IN_TIME);
    }

    @Test
    @Transactional
    public void getAllChecklistsByInTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);

        // Get all the checklistList where inTime is not null
        defaultChecklistShouldBeFound("inTime.specified=true");

        // Get all the checklistList where inTime is null
        defaultChecklistShouldNotBeFound("inTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllChecklistsByCompleteIsEqualToSomething() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);

        // Get all the checklistList where complete equals to DEFAULT_COMPLETE
        defaultChecklistShouldBeFound("complete.equals=" + DEFAULT_COMPLETE);

        // Get all the checklistList where complete equals to UPDATED_COMPLETE
        defaultChecklistShouldNotBeFound("complete.equals=" + UPDATED_COMPLETE);
    }

    @Test
    @Transactional
    public void getAllChecklistsByCompleteIsInShouldWork() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);

        // Get all the checklistList where complete in DEFAULT_COMPLETE or UPDATED_COMPLETE
        defaultChecklistShouldBeFound("complete.in=" + DEFAULT_COMPLETE + "," + UPDATED_COMPLETE);

        // Get all the checklistList where complete equals to UPDATED_COMPLETE
        defaultChecklistShouldNotBeFound("complete.in=" + UPDATED_COMPLETE);
    }

    @Test
    @Transactional
    public void getAllChecklistsByCompleteIsNullOrNotNull() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);

        // Get all the checklistList where complete is not null
        defaultChecklistShouldBeFound("complete.specified=true");

        // Get all the checklistList where complete is null
        defaultChecklistShouldNotBeFound("complete.specified=false");
    }

    @Test
    @Transactional
    public void getAllChecklistsByUnloadingOkIsEqualToSomething() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);

        // Get all the checklistList where unloadingOk equals to DEFAULT_UNLOADING_OK
        defaultChecklistShouldBeFound("unloadingOk.equals=" + DEFAULT_UNLOADING_OK);

        // Get all the checklistList where unloadingOk equals to UPDATED_UNLOADING_OK
        defaultChecklistShouldNotBeFound("unloadingOk.equals=" + UPDATED_UNLOADING_OK);
    }

    @Test
    @Transactional
    public void getAllChecklistsByUnloadingOkIsInShouldWork() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);

        // Get all the checklistList where unloadingOk in DEFAULT_UNLOADING_OK or UPDATED_UNLOADING_OK
        defaultChecklistShouldBeFound("unloadingOk.in=" + DEFAULT_UNLOADING_OK + "," + UPDATED_UNLOADING_OK);

        // Get all the checklistList where unloadingOk equals to UPDATED_UNLOADING_OK
        defaultChecklistShouldNotBeFound("unloadingOk.in=" + UPDATED_UNLOADING_OK);
    }

    @Test
    @Transactional
    public void getAllChecklistsByUnloadingOkIsNullOrNotNull() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);

        // Get all the checklistList where unloadingOk is not null
        defaultChecklistShouldBeFound("unloadingOk.specified=true");

        // Get all the checklistList where unloadingOk is null
        defaultChecklistShouldNotBeFound("unloadingOk.specified=false");
    }

    @Test
    @Transactional
    public void getAllChecklistsByNotDamagedIsEqualToSomething() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);

        // Get all the checklistList where notDamaged equals to DEFAULT_NOT_DAMAGED
        defaultChecklistShouldBeFound("notDamaged.equals=" + DEFAULT_NOT_DAMAGED);

        // Get all the checklistList where notDamaged equals to UPDATED_NOT_DAMAGED
        defaultChecklistShouldNotBeFound("notDamaged.equals=" + UPDATED_NOT_DAMAGED);
    }

    @Test
    @Transactional
    public void getAllChecklistsByNotDamagedIsInShouldWork() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);

        // Get all the checklistList where notDamaged in DEFAULT_NOT_DAMAGED or UPDATED_NOT_DAMAGED
        defaultChecklistShouldBeFound("notDamaged.in=" + DEFAULT_NOT_DAMAGED + "," + UPDATED_NOT_DAMAGED);

        // Get all the checklistList where notDamaged equals to UPDATED_NOT_DAMAGED
        defaultChecklistShouldNotBeFound("notDamaged.in=" + UPDATED_NOT_DAMAGED);
    }

    @Test
    @Transactional
    public void getAllChecklistsByNotDamagedIsNullOrNotNull() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);

        // Get all the checklistList where notDamaged is not null
        defaultChecklistShouldBeFound("notDamaged.specified=true");

        // Get all the checklistList where notDamaged is null
        defaultChecklistShouldNotBeFound("notDamaged.specified=false");
    }

    @Test
    @Transactional
    public void getAllChecklistsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);

        // Get all the checklistList where description equals to DEFAULT_DESCRIPTION
        defaultChecklistShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the checklistList where description equals to UPDATED_DESCRIPTION
        defaultChecklistShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllChecklistsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);

        // Get all the checklistList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultChecklistShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the checklistList where description equals to UPDATED_DESCRIPTION
        defaultChecklistShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllChecklistsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);

        // Get all the checklistList where description is not null
        defaultChecklistShouldBeFound("description.specified=true");

        // Get all the checklistList where description is null
        defaultChecklistShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllChecklistsByClaimIsEqualToSomething() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);

        // Get all the checklistList where claim equals to DEFAULT_CLAIM
        defaultChecklistShouldBeFound("claim.equals=" + DEFAULT_CLAIM);

        // Get all the checklistList where claim equals to UPDATED_CLAIM
        defaultChecklistShouldNotBeFound("claim.equals=" + UPDATED_CLAIM);
    }

    @Test
    @Transactional
    public void getAllChecklistsByClaimIsInShouldWork() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);

        // Get all the checklistList where claim in DEFAULT_CLAIM or UPDATED_CLAIM
        defaultChecklistShouldBeFound("claim.in=" + DEFAULT_CLAIM + "," + UPDATED_CLAIM);

        // Get all the checklistList where claim equals to UPDATED_CLAIM
        defaultChecklistShouldNotBeFound("claim.in=" + UPDATED_CLAIM);
    }

    @Test
    @Transactional
    public void getAllChecklistsByClaimIsNullOrNotNull() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);

        // Get all the checklistList where claim is not null
        defaultChecklistShouldBeFound("claim.specified=true");

        // Get all the checklistList where claim is null
        defaultChecklistShouldNotBeFound("claim.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultChecklistShouldBeFound(String filter) throws Exception {
        restChecklistMockMvc.perform(get("/api/checklists?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checklist.getId().intValue())))
            .andExpect(jsonPath("$.[*].inTime").value(hasItem(DEFAULT_IN_TIME.booleanValue())))
            .andExpect(jsonPath("$.[*].complete").value(hasItem(DEFAULT_COMPLETE.booleanValue())))
            .andExpect(jsonPath("$.[*].unloadingOk").value(hasItem(DEFAULT_UNLOADING_OK.booleanValue())))
            .andExpect(jsonPath("$.[*].notDamaged").value(hasItem(DEFAULT_NOT_DAMAGED.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].claim").value(hasItem(DEFAULT_CLAIM.booleanValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultChecklistShouldNotBeFound(String filter) throws Exception {
        restChecklistMockMvc.perform(get("/api/checklists?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingChecklist() throws Exception {
        // Get the checklist
        restChecklistMockMvc.perform(get("/api/checklists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChecklist() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);
        int databaseSizeBeforeUpdate = checklistRepository.findAll().size();

        // Update the checklist
        Checklist updatedChecklist = checklistRepository.findOne(checklist.getId());
        updatedChecklist
            .inTime(UPDATED_IN_TIME)
            .complete(UPDATED_COMPLETE)
            .unloadingOk(UPDATED_UNLOADING_OK)
            .notDamaged(UPDATED_NOT_DAMAGED)
            .description(UPDATED_DESCRIPTION)
            .claim(UPDATED_CLAIM);
        ChecklistDTO checklistDTO = checklistMapper.toDto(updatedChecklist);

        restChecklistMockMvc.perform(put("/api/checklists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checklistDTO)))
            .andExpect(status().isOk());

        // Validate the Checklist in the database
        List<Checklist> checklistList = checklistRepository.findAll();
        assertThat(checklistList).hasSize(databaseSizeBeforeUpdate);
        Checklist testChecklist = checklistList.get(checklistList.size() - 1);
        assertThat(testChecklist.isInTime()).isEqualTo(UPDATED_IN_TIME);
        assertThat(testChecklist.isComplete()).isEqualTo(UPDATED_COMPLETE);
        assertThat(testChecklist.isUnloadingOk()).isEqualTo(UPDATED_UNLOADING_OK);
        assertThat(testChecklist.isNotDamaged()).isEqualTo(UPDATED_NOT_DAMAGED);
        assertThat(testChecklist.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testChecklist.isClaim()).isEqualTo(UPDATED_CLAIM);
    }

    @Test
    @Transactional
    public void updateNonExistingChecklist() throws Exception {
        int databaseSizeBeforeUpdate = checklistRepository.findAll().size();

        // Create the Checklist
        ChecklistDTO checklistDTO = checklistMapper.toDto(checklist);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restChecklistMockMvc.perform(put("/api/checklists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checklistDTO)))
            .andExpect(status().isCreated());

        // Validate the Checklist in the database
        List<Checklist> checklistList = checklistRepository.findAll();
        assertThat(checklistList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteChecklist() throws Exception {
        // Initialize the database
        checklistRepository.saveAndFlush(checklist);
        int databaseSizeBeforeDelete = checklistRepository.findAll().size();

        // Get the checklist
        restChecklistMockMvc.perform(delete("/api/checklists/{id}", checklist.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Checklist> checklistList = checklistRepository.findAll();
        assertThat(checklistList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Checklist.class);
        Checklist checklist1 = new Checklist();
        checklist1.setId(1L);
        Checklist checklist2 = new Checklist();
        checklist2.setId(checklist1.getId());
        assertThat(checklist1).isEqualTo(checklist2);
        checklist2.setId(2L);
        assertThat(checklist1).isNotEqualTo(checklist2);
        checklist1.setId(null);
        assertThat(checklist1).isNotEqualTo(checklist2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChecklistDTO.class);
        ChecklistDTO checklistDTO1 = new ChecklistDTO();
        checklistDTO1.setId(1L);
        ChecklistDTO checklistDTO2 = new ChecklistDTO();
        assertThat(checklistDTO1).isNotEqualTo(checklistDTO2);
        checklistDTO2.setId(checklistDTO1.getId());
        assertThat(checklistDTO1).isEqualTo(checklistDTO2);
        checklistDTO2.setId(2L);
        assertThat(checklistDTO1).isNotEqualTo(checklistDTO2);
        checklistDTO1.setId(null);
        assertThat(checklistDTO1).isNotEqualTo(checklistDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(checklistMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(checklistMapper.fromId(null)).isNull();
    }
}
