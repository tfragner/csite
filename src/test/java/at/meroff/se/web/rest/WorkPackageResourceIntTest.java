package at.meroff.se.web.rest;

import at.meroff.se.CsiteApp;

import at.meroff.se.domain.WorkPackage;
import at.meroff.se.repository.WorkPackageRepository;
import at.meroff.se.service.WorkPackageService;
import at.meroff.se.service.dto.WorkPackageDTO;
import at.meroff.se.service.mapper.WorkPackageMapper;
import at.meroff.se.web.rest.errors.ExceptionTranslator;
import at.meroff.se.service.dto.WorkPackageCriteria;
import at.meroff.se.service.WorkPackageQueryService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static at.meroff.se.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import at.meroff.se.domain.enumeration.WorkPackageStatus;
/**
 * Test class for the WorkPackageResource REST controller.
 *
 * @see WorkPackageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsiteApp.class)
public class WorkPackageResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final WorkPackageStatus DEFAULT_STATUS = WorkPackageStatus.PLANNED;
    private static final WorkPackageStatus UPDATED_STATUS = WorkPackageStatus.OPEN;

    @Autowired
    private WorkPackageRepository workPackageRepository;

    @Autowired
    private WorkPackageMapper workPackageMapper;

    @Autowired
    private WorkPackageService workPackageService;

    @Autowired
    private WorkPackageQueryService workPackageQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWorkPackageMockMvc;

    private WorkPackage workPackage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WorkPackageResource workPackageResource = new WorkPackageResource(workPackageService, workPackageQueryService);
        this.restWorkPackageMockMvc = MockMvcBuilders.standaloneSetup(workPackageResource)
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
    public static WorkPackage createEntity(EntityManager em) {
        WorkPackage workPackage = new WorkPackage()
            .name(DEFAULT_NAME)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .status(DEFAULT_STATUS);
        return workPackage;
    }

    @Before
    public void initTest() {
        workPackage = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkPackage() throws Exception {
        int databaseSizeBeforeCreate = workPackageRepository.findAll().size();

        // Create the WorkPackage
        WorkPackageDTO workPackageDTO = workPackageMapper.toDto(workPackage);
        restWorkPackageMockMvc.perform(post("/api/work-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workPackageDTO)))
            .andExpect(status().isCreated());

        // Validate the WorkPackage in the database
        List<WorkPackage> workPackageList = workPackageRepository.findAll();
        assertThat(workPackageList).hasSize(databaseSizeBeforeCreate + 1);
        WorkPackage testWorkPackage = workPackageList.get(workPackageList.size() - 1);
        assertThat(testWorkPackage.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWorkPackage.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testWorkPackage.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testWorkPackage.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createWorkPackageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workPackageRepository.findAll().size();

        // Create the WorkPackage with an existing ID
        workPackage.setId(1L);
        WorkPackageDTO workPackageDTO = workPackageMapper.toDto(workPackage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkPackageMockMvc.perform(post("/api/work-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workPackageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WorkPackage in the database
        List<WorkPackage> workPackageList = workPackageRepository.findAll();
        assertThat(workPackageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWorkPackages() throws Exception {
        // Initialize the database
        workPackageRepository.saveAndFlush(workPackage);

        // Get all the workPackageList
        restWorkPackageMockMvc.perform(get("/api/work-packages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workPackage.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getWorkPackage() throws Exception {
        // Initialize the database
        workPackageRepository.saveAndFlush(workPackage);

        // Get the workPackage
        restWorkPackageMockMvc.perform(get("/api/work-packages/{id}", workPackage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(workPackage.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getAllWorkPackagesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        workPackageRepository.saveAndFlush(workPackage);

        // Get all the workPackageList where name equals to DEFAULT_NAME
        defaultWorkPackageShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the workPackageList where name equals to UPDATED_NAME
        defaultWorkPackageShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllWorkPackagesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        workPackageRepository.saveAndFlush(workPackage);

        // Get all the workPackageList where name in DEFAULT_NAME or UPDATED_NAME
        defaultWorkPackageShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the workPackageList where name equals to UPDATED_NAME
        defaultWorkPackageShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllWorkPackagesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        workPackageRepository.saveAndFlush(workPackage);

        // Get all the workPackageList where name is not null
        defaultWorkPackageShouldBeFound("name.specified=true");

        // Get all the workPackageList where name is null
        defaultWorkPackageShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllWorkPackagesByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        workPackageRepository.saveAndFlush(workPackage);

        // Get all the workPackageList where startDate equals to DEFAULT_START_DATE
        defaultWorkPackageShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the workPackageList where startDate equals to UPDATED_START_DATE
        defaultWorkPackageShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllWorkPackagesByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        workPackageRepository.saveAndFlush(workPackage);

        // Get all the workPackageList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultWorkPackageShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the workPackageList where startDate equals to UPDATED_START_DATE
        defaultWorkPackageShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllWorkPackagesByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        workPackageRepository.saveAndFlush(workPackage);

        // Get all the workPackageList where startDate is not null
        defaultWorkPackageShouldBeFound("startDate.specified=true");

        // Get all the workPackageList where startDate is null
        defaultWorkPackageShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllWorkPackagesByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workPackageRepository.saveAndFlush(workPackage);

        // Get all the workPackageList where startDate greater than or equals to DEFAULT_START_DATE
        defaultWorkPackageShouldBeFound("startDate.greaterOrEqualThan=" + DEFAULT_START_DATE);

        // Get all the workPackageList where startDate greater than or equals to UPDATED_START_DATE
        defaultWorkPackageShouldNotBeFound("startDate.greaterOrEqualThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllWorkPackagesByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        workPackageRepository.saveAndFlush(workPackage);

        // Get all the workPackageList where startDate less than or equals to DEFAULT_START_DATE
        defaultWorkPackageShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the workPackageList where startDate less than or equals to UPDATED_START_DATE
        defaultWorkPackageShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }


    @Test
    @Transactional
    public void getAllWorkPackagesByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        workPackageRepository.saveAndFlush(workPackage);

        // Get all the workPackageList where endDate equals to DEFAULT_END_DATE
        defaultWorkPackageShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the workPackageList where endDate equals to UPDATED_END_DATE
        defaultWorkPackageShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllWorkPackagesByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        workPackageRepository.saveAndFlush(workPackage);

        // Get all the workPackageList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultWorkPackageShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the workPackageList where endDate equals to UPDATED_END_DATE
        defaultWorkPackageShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllWorkPackagesByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        workPackageRepository.saveAndFlush(workPackage);

        // Get all the workPackageList where endDate is not null
        defaultWorkPackageShouldBeFound("endDate.specified=true");

        // Get all the workPackageList where endDate is null
        defaultWorkPackageShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllWorkPackagesByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workPackageRepository.saveAndFlush(workPackage);

        // Get all the workPackageList where endDate greater than or equals to DEFAULT_END_DATE
        defaultWorkPackageShouldBeFound("endDate.greaterOrEqualThan=" + DEFAULT_END_DATE);

        // Get all the workPackageList where endDate greater than or equals to UPDATED_END_DATE
        defaultWorkPackageShouldNotBeFound("endDate.greaterOrEqualThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllWorkPackagesByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        workPackageRepository.saveAndFlush(workPackage);

        // Get all the workPackageList where endDate less than or equals to DEFAULT_END_DATE
        defaultWorkPackageShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the workPackageList where endDate less than or equals to UPDATED_END_DATE
        defaultWorkPackageShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }


    @Test
    @Transactional
    public void getAllWorkPackagesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        workPackageRepository.saveAndFlush(workPackage);

        // Get all the workPackageList where status equals to DEFAULT_STATUS
        defaultWorkPackageShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the workPackageList where status equals to UPDATED_STATUS
        defaultWorkPackageShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllWorkPackagesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        workPackageRepository.saveAndFlush(workPackage);

        // Get all the workPackageList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultWorkPackageShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the workPackageList where status equals to UPDATED_STATUS
        defaultWorkPackageShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllWorkPackagesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        workPackageRepository.saveAndFlush(workPackage);

        // Get all the workPackageList where status is not null
        defaultWorkPackageShouldBeFound("status.specified=true");

        // Get all the workPackageList where status is null
        defaultWorkPackageShouldNotBeFound("status.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultWorkPackageShouldBeFound(String filter) throws Exception {
        restWorkPackageMockMvc.perform(get("/api/work-packages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workPackage.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultWorkPackageShouldNotBeFound(String filter) throws Exception {
        restWorkPackageMockMvc.perform(get("/api/work-packages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingWorkPackage() throws Exception {
        // Get the workPackage
        restWorkPackageMockMvc.perform(get("/api/work-packages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkPackage() throws Exception {
        // Initialize the database
        workPackageRepository.saveAndFlush(workPackage);
        int databaseSizeBeforeUpdate = workPackageRepository.findAll().size();

        // Update the workPackage
        WorkPackage updatedWorkPackage = workPackageRepository.findOne(workPackage.getId());
        updatedWorkPackage
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .status(UPDATED_STATUS);
        WorkPackageDTO workPackageDTO = workPackageMapper.toDto(updatedWorkPackage);

        restWorkPackageMockMvc.perform(put("/api/work-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workPackageDTO)))
            .andExpect(status().isOk());

        // Validate the WorkPackage in the database
        List<WorkPackage> workPackageList = workPackageRepository.findAll();
        assertThat(workPackageList).hasSize(databaseSizeBeforeUpdate);
        WorkPackage testWorkPackage = workPackageList.get(workPackageList.size() - 1);
        assertThat(testWorkPackage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWorkPackage.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testWorkPackage.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testWorkPackage.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkPackage() throws Exception {
        int databaseSizeBeforeUpdate = workPackageRepository.findAll().size();

        // Create the WorkPackage
        WorkPackageDTO workPackageDTO = workPackageMapper.toDto(workPackage);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWorkPackageMockMvc.perform(put("/api/work-packages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workPackageDTO)))
            .andExpect(status().isCreated());

        // Validate the WorkPackage in the database
        List<WorkPackage> workPackageList = workPackageRepository.findAll();
        assertThat(workPackageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWorkPackage() throws Exception {
        // Initialize the database
        workPackageRepository.saveAndFlush(workPackage);
        int databaseSizeBeforeDelete = workPackageRepository.findAll().size();

        // Get the workPackage
        restWorkPackageMockMvc.perform(delete("/api/work-packages/{id}", workPackage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WorkPackage> workPackageList = workPackageRepository.findAll();
        assertThat(workPackageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkPackage.class);
        WorkPackage workPackage1 = new WorkPackage();
        workPackage1.setId(1L);
        WorkPackage workPackage2 = new WorkPackage();
        workPackage2.setId(workPackage1.getId());
        assertThat(workPackage1).isEqualTo(workPackage2);
        workPackage2.setId(2L);
        assertThat(workPackage1).isNotEqualTo(workPackage2);
        workPackage1.setId(null);
        assertThat(workPackage1).isNotEqualTo(workPackage2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkPackageDTO.class);
        WorkPackageDTO workPackageDTO1 = new WorkPackageDTO();
        workPackageDTO1.setId(1L);
        WorkPackageDTO workPackageDTO2 = new WorkPackageDTO();
        assertThat(workPackageDTO1).isNotEqualTo(workPackageDTO2);
        workPackageDTO2.setId(workPackageDTO1.getId());
        assertThat(workPackageDTO1).isEqualTo(workPackageDTO2);
        workPackageDTO2.setId(2L);
        assertThat(workPackageDTO1).isNotEqualTo(workPackageDTO2);
        workPackageDTO1.setId(null);
        assertThat(workPackageDTO1).isNotEqualTo(workPackageDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(workPackageMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(workPackageMapper.fromId(null)).isNull();
    }
}
