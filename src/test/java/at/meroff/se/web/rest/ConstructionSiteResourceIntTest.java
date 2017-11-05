package at.meroff.se.web.rest;

import at.meroff.se.CsiteApp;

import at.meroff.se.domain.ConstructionSite;
import at.meroff.se.repository.ConstructionSiteRepository;
import at.meroff.se.service.ConstructionSiteService;
import at.meroff.se.service.dto.ConstructionSiteDTO;
import at.meroff.se.service.mapper.ConstructionSiteMapper;
import at.meroff.se.web.rest.errors.ExceptionTranslator;
import at.meroff.se.service.dto.ConstructionSiteCriteria;
import at.meroff.se.service.ConstructionSiteQueryService;

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

import at.meroff.se.domain.enumeration.LKWType;
/**
 * Test class for the ConstructionSiteResource REST controller.
 *
 * @see ConstructionSiteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsiteApp.class)
public class ConstructionSiteResourceIntTest {

    private static final Integer DEFAULT_PRJ_NUMBER = 1;
    private static final Integer UPDATED_PRJ_NUMBER = 2;

    private static final String DEFAULT_PRJ_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRJ_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_KRAN = false;
    private static final Boolean UPDATED_KRAN = true;

    private static final Boolean DEFAULT_STAPLER = false;
    private static final Boolean UPDATED_STAPLER = true;

    private static final LKWType DEFAULT_MAX_LKW_TYPE = LKWType.T35;
    private static final LKWType UPDATED_MAX_LKW_TYPE = LKWType.T70;

    @Autowired
    private ConstructionSiteRepository constructionSiteRepository;

    @Autowired
    private ConstructionSiteMapper constructionSiteMapper;

    @Autowired
    private ConstructionSiteService constructionSiteService;

    @Autowired
    private ConstructionSiteQueryService constructionSiteQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restConstructionSiteMockMvc;

    private ConstructionSite constructionSite;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConstructionSiteResource constructionSiteResource = new ConstructionSiteResource(constructionSiteService, constructionSiteQueryService);
        this.restConstructionSiteMockMvc = MockMvcBuilders.standaloneSetup(constructionSiteResource)
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
    public static ConstructionSite createEntity(EntityManager em) {
        ConstructionSite constructionSite = new ConstructionSite()
            .prjNumber(DEFAULT_PRJ_NUMBER)
            .prjName(DEFAULT_PRJ_NAME)
            .kran(DEFAULT_KRAN)
            .stapler(DEFAULT_STAPLER)
            .maxLKWType(DEFAULT_MAX_LKW_TYPE);
        return constructionSite;
    }

    @Before
    public void initTest() {
        constructionSite = createEntity(em);
    }

    @Test
    @Transactional
    public void createConstructionSite() throws Exception {
        int databaseSizeBeforeCreate = constructionSiteRepository.findAll().size();

        // Create the ConstructionSite
        ConstructionSiteDTO constructionSiteDTO = constructionSiteMapper.toDto(constructionSite);
        restConstructionSiteMockMvc.perform(post("/api/construction-sites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(constructionSiteDTO)))
            .andExpect(status().isCreated());

        // Validate the ConstructionSite in the database
        List<ConstructionSite> constructionSiteList = constructionSiteRepository.findAll();
        assertThat(constructionSiteList).hasSize(databaseSizeBeforeCreate + 1);
        ConstructionSite testConstructionSite = constructionSiteList.get(constructionSiteList.size() - 1);
        assertThat(testConstructionSite.getPrjNumber()).isEqualTo(DEFAULT_PRJ_NUMBER);
        assertThat(testConstructionSite.getPrjName()).isEqualTo(DEFAULT_PRJ_NAME);
        assertThat(testConstructionSite.isKran()).isEqualTo(DEFAULT_KRAN);
        assertThat(testConstructionSite.isStapler()).isEqualTo(DEFAULT_STAPLER);
        assertThat(testConstructionSite.getMaxLKWType()).isEqualTo(DEFAULT_MAX_LKW_TYPE);
    }

    @Test
    @Transactional
    public void createConstructionSiteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = constructionSiteRepository.findAll().size();

        // Create the ConstructionSite with an existing ID
        constructionSite.setId(1L);
        ConstructionSiteDTO constructionSiteDTO = constructionSiteMapper.toDto(constructionSite);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConstructionSiteMockMvc.perform(post("/api/construction-sites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(constructionSiteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConstructionSite in the database
        List<ConstructionSite> constructionSiteList = constructionSiteRepository.findAll();
        assertThat(constructionSiteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllConstructionSites() throws Exception {
        // Initialize the database
        constructionSiteRepository.saveAndFlush(constructionSite);

        // Get all the constructionSiteList
        restConstructionSiteMockMvc.perform(get("/api/construction-sites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(constructionSite.getId().intValue())))
            .andExpect(jsonPath("$.[*].prjNumber").value(hasItem(DEFAULT_PRJ_NUMBER)))
            .andExpect(jsonPath("$.[*].prjName").value(hasItem(DEFAULT_PRJ_NAME.toString())))
            .andExpect(jsonPath("$.[*].kran").value(hasItem(DEFAULT_KRAN.booleanValue())))
            .andExpect(jsonPath("$.[*].stapler").value(hasItem(DEFAULT_STAPLER.booleanValue())))
            .andExpect(jsonPath("$.[*].maxLKWType").value(hasItem(DEFAULT_MAX_LKW_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getConstructionSite() throws Exception {
        // Initialize the database
        constructionSiteRepository.saveAndFlush(constructionSite);

        // Get the constructionSite
        restConstructionSiteMockMvc.perform(get("/api/construction-sites/{id}", constructionSite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(constructionSite.getId().intValue()))
            .andExpect(jsonPath("$.prjNumber").value(DEFAULT_PRJ_NUMBER))
            .andExpect(jsonPath("$.prjName").value(DEFAULT_PRJ_NAME.toString()))
            .andExpect(jsonPath("$.kran").value(DEFAULT_KRAN.booleanValue()))
            .andExpect(jsonPath("$.stapler").value(DEFAULT_STAPLER.booleanValue()))
            .andExpect(jsonPath("$.maxLKWType").value(DEFAULT_MAX_LKW_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getAllConstructionSitesByPrjNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        constructionSiteRepository.saveAndFlush(constructionSite);

        // Get all the constructionSiteList where prjNumber equals to DEFAULT_PRJ_NUMBER
        defaultConstructionSiteShouldBeFound("prjNumber.equals=" + DEFAULT_PRJ_NUMBER);

        // Get all the constructionSiteList where prjNumber equals to UPDATED_PRJ_NUMBER
        defaultConstructionSiteShouldNotBeFound("prjNumber.equals=" + UPDATED_PRJ_NUMBER);
    }

    @Test
    @Transactional
    public void getAllConstructionSitesByPrjNumberIsInShouldWork() throws Exception {
        // Initialize the database
        constructionSiteRepository.saveAndFlush(constructionSite);

        // Get all the constructionSiteList where prjNumber in DEFAULT_PRJ_NUMBER or UPDATED_PRJ_NUMBER
        defaultConstructionSiteShouldBeFound("prjNumber.in=" + DEFAULT_PRJ_NUMBER + "," + UPDATED_PRJ_NUMBER);

        // Get all the constructionSiteList where prjNumber equals to UPDATED_PRJ_NUMBER
        defaultConstructionSiteShouldNotBeFound("prjNumber.in=" + UPDATED_PRJ_NUMBER);
    }

    @Test
    @Transactional
    public void getAllConstructionSitesByPrjNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        constructionSiteRepository.saveAndFlush(constructionSite);

        // Get all the constructionSiteList where prjNumber is not null
        defaultConstructionSiteShouldBeFound("prjNumber.specified=true");

        // Get all the constructionSiteList where prjNumber is null
        defaultConstructionSiteShouldNotBeFound("prjNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllConstructionSitesByPrjNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        constructionSiteRepository.saveAndFlush(constructionSite);

        // Get all the constructionSiteList where prjNumber greater than or equals to DEFAULT_PRJ_NUMBER
        defaultConstructionSiteShouldBeFound("prjNumber.greaterOrEqualThan=" + DEFAULT_PRJ_NUMBER);

        // Get all the constructionSiteList where prjNumber greater than or equals to UPDATED_PRJ_NUMBER
        defaultConstructionSiteShouldNotBeFound("prjNumber.greaterOrEqualThan=" + UPDATED_PRJ_NUMBER);
    }

    @Test
    @Transactional
    public void getAllConstructionSitesByPrjNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        constructionSiteRepository.saveAndFlush(constructionSite);

        // Get all the constructionSiteList where prjNumber less than or equals to DEFAULT_PRJ_NUMBER
        defaultConstructionSiteShouldNotBeFound("prjNumber.lessThan=" + DEFAULT_PRJ_NUMBER);

        // Get all the constructionSiteList where prjNumber less than or equals to UPDATED_PRJ_NUMBER
        defaultConstructionSiteShouldBeFound("prjNumber.lessThan=" + UPDATED_PRJ_NUMBER);
    }


    @Test
    @Transactional
    public void getAllConstructionSitesByPrjNameIsEqualToSomething() throws Exception {
        // Initialize the database
        constructionSiteRepository.saveAndFlush(constructionSite);

        // Get all the constructionSiteList where prjName equals to DEFAULT_PRJ_NAME
        defaultConstructionSiteShouldBeFound("prjName.equals=" + DEFAULT_PRJ_NAME);

        // Get all the constructionSiteList where prjName equals to UPDATED_PRJ_NAME
        defaultConstructionSiteShouldNotBeFound("prjName.equals=" + UPDATED_PRJ_NAME);
    }

    @Test
    @Transactional
    public void getAllConstructionSitesByPrjNameIsInShouldWork() throws Exception {
        // Initialize the database
        constructionSiteRepository.saveAndFlush(constructionSite);

        // Get all the constructionSiteList where prjName in DEFAULT_PRJ_NAME or UPDATED_PRJ_NAME
        defaultConstructionSiteShouldBeFound("prjName.in=" + DEFAULT_PRJ_NAME + "," + UPDATED_PRJ_NAME);

        // Get all the constructionSiteList where prjName equals to UPDATED_PRJ_NAME
        defaultConstructionSiteShouldNotBeFound("prjName.in=" + UPDATED_PRJ_NAME);
    }

    @Test
    @Transactional
    public void getAllConstructionSitesByPrjNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        constructionSiteRepository.saveAndFlush(constructionSite);

        // Get all the constructionSiteList where prjName is not null
        defaultConstructionSiteShouldBeFound("prjName.specified=true");

        // Get all the constructionSiteList where prjName is null
        defaultConstructionSiteShouldNotBeFound("prjName.specified=false");
    }

    @Test
    @Transactional
    public void getAllConstructionSitesByKranIsEqualToSomething() throws Exception {
        // Initialize the database
        constructionSiteRepository.saveAndFlush(constructionSite);

        // Get all the constructionSiteList where kran equals to DEFAULT_KRAN
        defaultConstructionSiteShouldBeFound("kran.equals=" + DEFAULT_KRAN);

        // Get all the constructionSiteList where kran equals to UPDATED_KRAN
        defaultConstructionSiteShouldNotBeFound("kran.equals=" + UPDATED_KRAN);
    }

    @Test
    @Transactional
    public void getAllConstructionSitesByKranIsInShouldWork() throws Exception {
        // Initialize the database
        constructionSiteRepository.saveAndFlush(constructionSite);

        // Get all the constructionSiteList where kran in DEFAULT_KRAN or UPDATED_KRAN
        defaultConstructionSiteShouldBeFound("kran.in=" + DEFAULT_KRAN + "," + UPDATED_KRAN);

        // Get all the constructionSiteList where kran equals to UPDATED_KRAN
        defaultConstructionSiteShouldNotBeFound("kran.in=" + UPDATED_KRAN);
    }

    @Test
    @Transactional
    public void getAllConstructionSitesByKranIsNullOrNotNull() throws Exception {
        // Initialize the database
        constructionSiteRepository.saveAndFlush(constructionSite);

        // Get all the constructionSiteList where kran is not null
        defaultConstructionSiteShouldBeFound("kran.specified=true");

        // Get all the constructionSiteList where kran is null
        defaultConstructionSiteShouldNotBeFound("kran.specified=false");
    }

    @Test
    @Transactional
    public void getAllConstructionSitesByStaplerIsEqualToSomething() throws Exception {
        // Initialize the database
        constructionSiteRepository.saveAndFlush(constructionSite);

        // Get all the constructionSiteList where stapler equals to DEFAULT_STAPLER
        defaultConstructionSiteShouldBeFound("stapler.equals=" + DEFAULT_STAPLER);

        // Get all the constructionSiteList where stapler equals to UPDATED_STAPLER
        defaultConstructionSiteShouldNotBeFound("stapler.equals=" + UPDATED_STAPLER);
    }

    @Test
    @Transactional
    public void getAllConstructionSitesByStaplerIsInShouldWork() throws Exception {
        // Initialize the database
        constructionSiteRepository.saveAndFlush(constructionSite);

        // Get all the constructionSiteList where stapler in DEFAULT_STAPLER or UPDATED_STAPLER
        defaultConstructionSiteShouldBeFound("stapler.in=" + DEFAULT_STAPLER + "," + UPDATED_STAPLER);

        // Get all the constructionSiteList where stapler equals to UPDATED_STAPLER
        defaultConstructionSiteShouldNotBeFound("stapler.in=" + UPDATED_STAPLER);
    }

    @Test
    @Transactional
    public void getAllConstructionSitesByStaplerIsNullOrNotNull() throws Exception {
        // Initialize the database
        constructionSiteRepository.saveAndFlush(constructionSite);

        // Get all the constructionSiteList where stapler is not null
        defaultConstructionSiteShouldBeFound("stapler.specified=true");

        // Get all the constructionSiteList where stapler is null
        defaultConstructionSiteShouldNotBeFound("stapler.specified=false");
    }

    @Test
    @Transactional
    public void getAllConstructionSitesByMaxLKWTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        constructionSiteRepository.saveAndFlush(constructionSite);

        // Get all the constructionSiteList where maxLKWType equals to DEFAULT_MAX_LKW_TYPE
        defaultConstructionSiteShouldBeFound("maxLKWType.equals=" + DEFAULT_MAX_LKW_TYPE);

        // Get all the constructionSiteList where maxLKWType equals to UPDATED_MAX_LKW_TYPE
        defaultConstructionSiteShouldNotBeFound("maxLKWType.equals=" + UPDATED_MAX_LKW_TYPE);
    }

    @Test
    @Transactional
    public void getAllConstructionSitesByMaxLKWTypeIsInShouldWork() throws Exception {
        // Initialize the database
        constructionSiteRepository.saveAndFlush(constructionSite);

        // Get all the constructionSiteList where maxLKWType in DEFAULT_MAX_LKW_TYPE or UPDATED_MAX_LKW_TYPE
        defaultConstructionSiteShouldBeFound("maxLKWType.in=" + DEFAULT_MAX_LKW_TYPE + "," + UPDATED_MAX_LKW_TYPE);

        // Get all the constructionSiteList where maxLKWType equals to UPDATED_MAX_LKW_TYPE
        defaultConstructionSiteShouldNotBeFound("maxLKWType.in=" + UPDATED_MAX_LKW_TYPE);
    }

    @Test
    @Transactional
    public void getAllConstructionSitesByMaxLKWTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        constructionSiteRepository.saveAndFlush(constructionSite);

        // Get all the constructionSiteList where maxLKWType is not null
        defaultConstructionSiteShouldBeFound("maxLKWType.specified=true");

        // Get all the constructionSiteList where maxLKWType is null
        defaultConstructionSiteShouldNotBeFound("maxLKWType.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultConstructionSiteShouldBeFound(String filter) throws Exception {
        restConstructionSiteMockMvc.perform(get("/api/construction-sites?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(constructionSite.getId().intValue())))
            .andExpect(jsonPath("$.[*].prjNumber").value(hasItem(DEFAULT_PRJ_NUMBER)))
            .andExpect(jsonPath("$.[*].prjName").value(hasItem(DEFAULT_PRJ_NAME.toString())))
            .andExpect(jsonPath("$.[*].kran").value(hasItem(DEFAULT_KRAN.booleanValue())))
            .andExpect(jsonPath("$.[*].stapler").value(hasItem(DEFAULT_STAPLER.booleanValue())))
            .andExpect(jsonPath("$.[*].maxLKWType").value(hasItem(DEFAULT_MAX_LKW_TYPE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultConstructionSiteShouldNotBeFound(String filter) throws Exception {
        restConstructionSiteMockMvc.perform(get("/api/construction-sites?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingConstructionSite() throws Exception {
        // Get the constructionSite
        restConstructionSiteMockMvc.perform(get("/api/construction-sites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConstructionSite() throws Exception {
        // Initialize the database
        constructionSiteRepository.saveAndFlush(constructionSite);
        int databaseSizeBeforeUpdate = constructionSiteRepository.findAll().size();

        // Update the constructionSite
        ConstructionSite updatedConstructionSite = constructionSiteRepository.findOne(constructionSite.getId());
        updatedConstructionSite
            .prjNumber(UPDATED_PRJ_NUMBER)
            .prjName(UPDATED_PRJ_NAME)
            .kran(UPDATED_KRAN)
            .stapler(UPDATED_STAPLER)
            .maxLKWType(UPDATED_MAX_LKW_TYPE);
        ConstructionSiteDTO constructionSiteDTO = constructionSiteMapper.toDto(updatedConstructionSite);

        restConstructionSiteMockMvc.perform(put("/api/construction-sites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(constructionSiteDTO)))
            .andExpect(status().isOk());

        // Validate the ConstructionSite in the database
        List<ConstructionSite> constructionSiteList = constructionSiteRepository.findAll();
        assertThat(constructionSiteList).hasSize(databaseSizeBeforeUpdate);
        ConstructionSite testConstructionSite = constructionSiteList.get(constructionSiteList.size() - 1);
        assertThat(testConstructionSite.getPrjNumber()).isEqualTo(UPDATED_PRJ_NUMBER);
        assertThat(testConstructionSite.getPrjName()).isEqualTo(UPDATED_PRJ_NAME);
        assertThat(testConstructionSite.isKran()).isEqualTo(UPDATED_KRAN);
        assertThat(testConstructionSite.isStapler()).isEqualTo(UPDATED_STAPLER);
        assertThat(testConstructionSite.getMaxLKWType()).isEqualTo(UPDATED_MAX_LKW_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingConstructionSite() throws Exception {
        int databaseSizeBeforeUpdate = constructionSiteRepository.findAll().size();

        // Create the ConstructionSite
        ConstructionSiteDTO constructionSiteDTO = constructionSiteMapper.toDto(constructionSite);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restConstructionSiteMockMvc.perform(put("/api/construction-sites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(constructionSiteDTO)))
            .andExpect(status().isCreated());

        // Validate the ConstructionSite in the database
        List<ConstructionSite> constructionSiteList = constructionSiteRepository.findAll();
        assertThat(constructionSiteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteConstructionSite() throws Exception {
        // Initialize the database
        constructionSiteRepository.saveAndFlush(constructionSite);
        int databaseSizeBeforeDelete = constructionSiteRepository.findAll().size();

        // Get the constructionSite
        restConstructionSiteMockMvc.perform(delete("/api/construction-sites/{id}", constructionSite.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ConstructionSite> constructionSiteList = constructionSiteRepository.findAll();
        assertThat(constructionSiteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConstructionSite.class);
        ConstructionSite constructionSite1 = new ConstructionSite();
        constructionSite1.setId(1L);
        ConstructionSite constructionSite2 = new ConstructionSite();
        constructionSite2.setId(constructionSite1.getId());
        assertThat(constructionSite1).isEqualTo(constructionSite2);
        constructionSite2.setId(2L);
        assertThat(constructionSite1).isNotEqualTo(constructionSite2);
        constructionSite1.setId(null);
        assertThat(constructionSite1).isNotEqualTo(constructionSite2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConstructionSiteDTO.class);
        ConstructionSiteDTO constructionSiteDTO1 = new ConstructionSiteDTO();
        constructionSiteDTO1.setId(1L);
        ConstructionSiteDTO constructionSiteDTO2 = new ConstructionSiteDTO();
        assertThat(constructionSiteDTO1).isNotEqualTo(constructionSiteDTO2);
        constructionSiteDTO2.setId(constructionSiteDTO1.getId());
        assertThat(constructionSiteDTO1).isEqualTo(constructionSiteDTO2);
        constructionSiteDTO2.setId(2L);
        assertThat(constructionSiteDTO1).isNotEqualTo(constructionSiteDTO2);
        constructionSiteDTO1.setId(null);
        assertThat(constructionSiteDTO1).isNotEqualTo(constructionSiteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(constructionSiteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(constructionSiteMapper.fromId(null)).isNull();
    }
}
