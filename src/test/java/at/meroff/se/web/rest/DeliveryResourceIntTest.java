package at.meroff.se.web.rest;

import at.meroff.se.CsiteApp;

import at.meroff.se.domain.Delivery;
import at.meroff.se.repository.DeliveryRepository;
import at.meroff.se.service.DeliveryService;
import at.meroff.se.service.dto.DeliveryDTO;
import at.meroff.se.service.mapper.DeliveryMapper;
import at.meroff.se.web.rest.errors.ExceptionTranslator;
import at.meroff.se.service.dto.DeliveryCriteria;
import at.meroff.se.service.DeliveryQueryService;

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
import org.springframework.util.Base64Utils;

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

import at.meroff.se.domain.enumeration.UnloadingType;
import at.meroff.se.domain.enumeration.AvisoType;
import at.meroff.se.domain.enumeration.DeliveryStatus;
import at.meroff.se.domain.enumeration.LKWType;
/**
 * Test class for the DeliveryResource REST controller.
 *
 * @see DeliveryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CsiteApp.class)
public class DeliveryResourceIntTest {

    private static final String DEFAULT_ORDER_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_NUMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_KALENDERWOCHE = 1;
    private static final Integer UPDATED_KALENDERWOCHE = 2;

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final UnloadingType DEFAULT_UNLOADING = UnloadingType.KRAN;
    private static final UnloadingType UPDATED_UNLOADING = UnloadingType.STAPLER;

    private static final AvisoType DEFAULT_AVISO_TYPE = AvisoType.MATANLIEFERUNG;
    private static final AvisoType UPDATED_AVISO_TYPE = AvisoType.ENTSORGUNG;

    private static final DeliveryStatus DEFAULT_STATUS = DeliveryStatus.OPEN;
    private static final DeliveryStatus UPDATED_STATUS = DeliveryStatus.CLOSED;

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final LKWType DEFAULT_LKW_TYPE = LKWType.T35;
    private static final LKWType UPDATED_LKW_TYPE = LKWType.T70;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private DeliveryMapper deliveryMapper;

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private DeliveryQueryService deliveryQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDeliveryMockMvc;

    private Delivery delivery;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeliveryResource deliveryResource = new DeliveryResource(deliveryService, deliveryQueryService);
        this.restDeliveryMockMvc = MockMvcBuilders.standaloneSetup(deliveryResource)
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
    public static Delivery createEntity(EntityManager em) {
        Delivery delivery = new Delivery()
            .orderNumber(DEFAULT_ORDER_NUMBER)
            .kalenderwoche(DEFAULT_KALENDERWOCHE)
            .date(DEFAULT_DATE)
            .unloading(DEFAULT_UNLOADING)
            .avisoType(DEFAULT_AVISO_TYPE)
            .status(DEFAULT_STATUS)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .lkwType(DEFAULT_LKW_TYPE);
        return delivery;
    }

    @Before
    public void initTest() {
        delivery = createEntity(em);
    }

    @Test
    @Transactional
    public void createDelivery() throws Exception {
        int databaseSizeBeforeCreate = deliveryRepository.findAll().size();

        // Create the Delivery
        DeliveryDTO deliveryDTO = deliveryMapper.toDto(delivery);
        restDeliveryMockMvc.perform(post("/api/deliveries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryDTO)))
            .andExpect(status().isCreated());

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll();
        assertThat(deliveryList).hasSize(databaseSizeBeforeCreate + 1);
        Delivery testDelivery = deliveryList.get(deliveryList.size() - 1);
        assertThat(testDelivery.getOrderNumber()).isEqualTo(DEFAULT_ORDER_NUMBER);
        assertThat(testDelivery.getKalenderwoche()).isEqualTo(DEFAULT_KALENDERWOCHE);
        assertThat(testDelivery.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testDelivery.getUnloading()).isEqualTo(DEFAULT_UNLOADING);
        assertThat(testDelivery.getAvisoType()).isEqualTo(DEFAULT_AVISO_TYPE);
        assertThat(testDelivery.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDelivery.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testDelivery.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testDelivery.getLkwType()).isEqualTo(DEFAULT_LKW_TYPE);
    }

    @Test
    @Transactional
    public void createDeliveryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deliveryRepository.findAll().size();

        // Create the Delivery with an existing ID
        delivery.setId(1L);
        DeliveryDTO deliveryDTO = deliveryMapper.toDto(delivery);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeliveryMockMvc.perform(post("/api/deliveries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll();
        assertThat(deliveryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDeliveries() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        // Get all the deliveryList
        restDeliveryMockMvc.perform(get("/api/deliveries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(delivery.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderNumber").value(hasItem(DEFAULT_ORDER_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].kalenderwoche").value(hasItem(DEFAULT_KALENDERWOCHE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].unloading").value(hasItem(DEFAULT_UNLOADING.toString())))
            .andExpect(jsonPath("$.[*].avisoType").value(hasItem(DEFAULT_AVISO_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].lkwType").value(hasItem(DEFAULT_LKW_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getDelivery() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        // Get the delivery
        restDeliveryMockMvc.perform(get("/api/deliveries/{id}", delivery.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(delivery.getId().intValue()))
            .andExpect(jsonPath("$.orderNumber").value(DEFAULT_ORDER_NUMBER.toString()))
            .andExpect(jsonPath("$.kalenderwoche").value(DEFAULT_KALENDERWOCHE))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.unloading").value(DEFAULT_UNLOADING.toString()))
            .andExpect(jsonPath("$.avisoType").value(DEFAULT_AVISO_TYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.lkwType").value(DEFAULT_LKW_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getAllDeliveriesByOrderNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        // Get all the deliveryList where orderNumber equals to DEFAULT_ORDER_NUMBER
        defaultDeliveryShouldBeFound("orderNumber.equals=" + DEFAULT_ORDER_NUMBER);

        // Get all the deliveryList where orderNumber equals to UPDATED_ORDER_NUMBER
        defaultDeliveryShouldNotBeFound("orderNumber.equals=" + UPDATED_ORDER_NUMBER);
    }

    @Test
    @Transactional
    public void getAllDeliveriesByOrderNumberIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        // Get all the deliveryList where orderNumber in DEFAULT_ORDER_NUMBER or UPDATED_ORDER_NUMBER
        defaultDeliveryShouldBeFound("orderNumber.in=" + DEFAULT_ORDER_NUMBER + "," + UPDATED_ORDER_NUMBER);

        // Get all the deliveryList where orderNumber equals to UPDATED_ORDER_NUMBER
        defaultDeliveryShouldNotBeFound("orderNumber.in=" + UPDATED_ORDER_NUMBER);
    }

    @Test
    @Transactional
    public void getAllDeliveriesByOrderNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        // Get all the deliveryList where orderNumber is not null
        defaultDeliveryShouldBeFound("orderNumber.specified=true");

        // Get all the deliveryList where orderNumber is null
        defaultDeliveryShouldNotBeFound("orderNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeliveriesByKalenderwocheIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        // Get all the deliveryList where kalenderwoche equals to DEFAULT_KALENDERWOCHE
        defaultDeliveryShouldBeFound("kalenderwoche.equals=" + DEFAULT_KALENDERWOCHE);

        // Get all the deliveryList where kalenderwoche equals to UPDATED_KALENDERWOCHE
        defaultDeliveryShouldNotBeFound("kalenderwoche.equals=" + UPDATED_KALENDERWOCHE);
    }

    @Test
    @Transactional
    public void getAllDeliveriesByKalenderwocheIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        // Get all the deliveryList where kalenderwoche in DEFAULT_KALENDERWOCHE or UPDATED_KALENDERWOCHE
        defaultDeliveryShouldBeFound("kalenderwoche.in=" + DEFAULT_KALENDERWOCHE + "," + UPDATED_KALENDERWOCHE);

        // Get all the deliveryList where kalenderwoche equals to UPDATED_KALENDERWOCHE
        defaultDeliveryShouldNotBeFound("kalenderwoche.in=" + UPDATED_KALENDERWOCHE);
    }

    @Test
    @Transactional
    public void getAllDeliveriesByKalenderwocheIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        // Get all the deliveryList where kalenderwoche is not null
        defaultDeliveryShouldBeFound("kalenderwoche.specified=true");

        // Get all the deliveryList where kalenderwoche is null
        defaultDeliveryShouldNotBeFound("kalenderwoche.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeliveriesByKalenderwocheIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        // Get all the deliveryList where kalenderwoche greater than or equals to DEFAULT_KALENDERWOCHE
        defaultDeliveryShouldBeFound("kalenderwoche.greaterOrEqualThan=" + DEFAULT_KALENDERWOCHE);

        // Get all the deliveryList where kalenderwoche greater than or equals to UPDATED_KALENDERWOCHE
        defaultDeliveryShouldNotBeFound("kalenderwoche.greaterOrEqualThan=" + UPDATED_KALENDERWOCHE);
    }

    @Test
    @Transactional
    public void getAllDeliveriesByKalenderwocheIsLessThanSomething() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        // Get all the deliveryList where kalenderwoche less than or equals to DEFAULT_KALENDERWOCHE
        defaultDeliveryShouldNotBeFound("kalenderwoche.lessThan=" + DEFAULT_KALENDERWOCHE);

        // Get all the deliveryList where kalenderwoche less than or equals to UPDATED_KALENDERWOCHE
        defaultDeliveryShouldBeFound("kalenderwoche.lessThan=" + UPDATED_KALENDERWOCHE);
    }


    @Test
    @Transactional
    public void getAllDeliveriesByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        // Get all the deliveryList where date equals to DEFAULT_DATE
        defaultDeliveryShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the deliveryList where date equals to UPDATED_DATE
        defaultDeliveryShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllDeliveriesByDateIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        // Get all the deliveryList where date in DEFAULT_DATE or UPDATED_DATE
        defaultDeliveryShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the deliveryList where date equals to UPDATED_DATE
        defaultDeliveryShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllDeliveriesByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        // Get all the deliveryList where date is not null
        defaultDeliveryShouldBeFound("date.specified=true");

        // Get all the deliveryList where date is null
        defaultDeliveryShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeliveriesByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        // Get all the deliveryList where date greater than or equals to DEFAULT_DATE
        defaultDeliveryShouldBeFound("date.greaterOrEqualThan=" + DEFAULT_DATE);

        // Get all the deliveryList where date greater than or equals to UPDATED_DATE
        defaultDeliveryShouldNotBeFound("date.greaterOrEqualThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllDeliveriesByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        // Get all the deliveryList where date less than or equals to DEFAULT_DATE
        defaultDeliveryShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the deliveryList where date less than or equals to UPDATED_DATE
        defaultDeliveryShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllDeliveriesByUnloadingIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        // Get all the deliveryList where unloading equals to DEFAULT_UNLOADING
        defaultDeliveryShouldBeFound("unloading.equals=" + DEFAULT_UNLOADING);

        // Get all the deliveryList where unloading equals to UPDATED_UNLOADING
        defaultDeliveryShouldNotBeFound("unloading.equals=" + UPDATED_UNLOADING);
    }

    @Test
    @Transactional
    public void getAllDeliveriesByUnloadingIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        // Get all the deliveryList where unloading in DEFAULT_UNLOADING or UPDATED_UNLOADING
        defaultDeliveryShouldBeFound("unloading.in=" + DEFAULT_UNLOADING + "," + UPDATED_UNLOADING);

        // Get all the deliveryList where unloading equals to UPDATED_UNLOADING
        defaultDeliveryShouldNotBeFound("unloading.in=" + UPDATED_UNLOADING);
    }

    @Test
    @Transactional
    public void getAllDeliveriesByUnloadingIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        // Get all the deliveryList where unloading is not null
        defaultDeliveryShouldBeFound("unloading.specified=true");

        // Get all the deliveryList where unloading is null
        defaultDeliveryShouldNotBeFound("unloading.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeliveriesByAvisoTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        // Get all the deliveryList where avisoType equals to DEFAULT_AVISO_TYPE
        defaultDeliveryShouldBeFound("avisoType.equals=" + DEFAULT_AVISO_TYPE);

        // Get all the deliveryList where avisoType equals to UPDATED_AVISO_TYPE
        defaultDeliveryShouldNotBeFound("avisoType.equals=" + UPDATED_AVISO_TYPE);
    }

    @Test
    @Transactional
    public void getAllDeliveriesByAvisoTypeIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        // Get all the deliveryList where avisoType in DEFAULT_AVISO_TYPE or UPDATED_AVISO_TYPE
        defaultDeliveryShouldBeFound("avisoType.in=" + DEFAULT_AVISO_TYPE + "," + UPDATED_AVISO_TYPE);

        // Get all the deliveryList where avisoType equals to UPDATED_AVISO_TYPE
        defaultDeliveryShouldNotBeFound("avisoType.in=" + UPDATED_AVISO_TYPE);
    }

    @Test
    @Transactional
    public void getAllDeliveriesByAvisoTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        // Get all the deliveryList where avisoType is not null
        defaultDeliveryShouldBeFound("avisoType.specified=true");

        // Get all the deliveryList where avisoType is null
        defaultDeliveryShouldNotBeFound("avisoType.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeliveriesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        // Get all the deliveryList where status equals to DEFAULT_STATUS
        defaultDeliveryShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the deliveryList where status equals to UPDATED_STATUS
        defaultDeliveryShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllDeliveriesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        // Get all the deliveryList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultDeliveryShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the deliveryList where status equals to UPDATED_STATUS
        defaultDeliveryShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllDeliveriesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        // Get all the deliveryList where status is not null
        defaultDeliveryShouldBeFound("status.specified=true");

        // Get all the deliveryList where status is null
        defaultDeliveryShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeliveriesByLkwTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        // Get all the deliveryList where lkwType equals to DEFAULT_LKW_TYPE
        defaultDeliveryShouldBeFound("lkwType.equals=" + DEFAULT_LKW_TYPE);

        // Get all the deliveryList where lkwType equals to UPDATED_LKW_TYPE
        defaultDeliveryShouldNotBeFound("lkwType.equals=" + UPDATED_LKW_TYPE);
    }

    @Test
    @Transactional
    public void getAllDeliveriesByLkwTypeIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        // Get all the deliveryList where lkwType in DEFAULT_LKW_TYPE or UPDATED_LKW_TYPE
        defaultDeliveryShouldBeFound("lkwType.in=" + DEFAULT_LKW_TYPE + "," + UPDATED_LKW_TYPE);

        // Get all the deliveryList where lkwType equals to UPDATED_LKW_TYPE
        defaultDeliveryShouldNotBeFound("lkwType.in=" + UPDATED_LKW_TYPE);
    }

    @Test
    @Transactional
    public void getAllDeliveriesByLkwTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        // Get all the deliveryList where lkwType is not null
        defaultDeliveryShouldBeFound("lkwType.specified=true");

        // Get all the deliveryList where lkwType is null
        defaultDeliveryShouldNotBeFound("lkwType.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultDeliveryShouldBeFound(String filter) throws Exception {
        restDeliveryMockMvc.perform(get("/api/deliveries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(delivery.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderNumber").value(hasItem(DEFAULT_ORDER_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].kalenderwoche").value(hasItem(DEFAULT_KALENDERWOCHE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].unloading").value(hasItem(DEFAULT_UNLOADING.toString())))
            .andExpect(jsonPath("$.[*].avisoType").value(hasItem(DEFAULT_AVISO_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].lkwType").value(hasItem(DEFAULT_LKW_TYPE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultDeliveryShouldNotBeFound(String filter) throws Exception {
        restDeliveryMockMvc.perform(get("/api/deliveries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingDelivery() throws Exception {
        // Get the delivery
        restDeliveryMockMvc.perform(get("/api/deliveries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDelivery() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);
        int databaseSizeBeforeUpdate = deliveryRepository.findAll().size();

        // Update the delivery
        Delivery updatedDelivery = deliveryRepository.findOne(delivery.getId());
        updatedDelivery
            .orderNumber(UPDATED_ORDER_NUMBER)
            .kalenderwoche(UPDATED_KALENDERWOCHE)
            .date(UPDATED_DATE)
            .unloading(UPDATED_UNLOADING)
            .avisoType(UPDATED_AVISO_TYPE)
            .status(UPDATED_STATUS)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .lkwType(UPDATED_LKW_TYPE);
        DeliveryDTO deliveryDTO = deliveryMapper.toDto(updatedDelivery);

        restDeliveryMockMvc.perform(put("/api/deliveries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryDTO)))
            .andExpect(status().isOk());

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll();
        assertThat(deliveryList).hasSize(databaseSizeBeforeUpdate);
        Delivery testDelivery = deliveryList.get(deliveryList.size() - 1);
        assertThat(testDelivery.getOrderNumber()).isEqualTo(UPDATED_ORDER_NUMBER);
        assertThat(testDelivery.getKalenderwoche()).isEqualTo(UPDATED_KALENDERWOCHE);
        assertThat(testDelivery.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDelivery.getUnloading()).isEqualTo(UPDATED_UNLOADING);
        assertThat(testDelivery.getAvisoType()).isEqualTo(UPDATED_AVISO_TYPE);
        assertThat(testDelivery.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDelivery.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testDelivery.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testDelivery.getLkwType()).isEqualTo(UPDATED_LKW_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingDelivery() throws Exception {
        int databaseSizeBeforeUpdate = deliveryRepository.findAll().size();

        // Create the Delivery
        DeliveryDTO deliveryDTO = deliveryMapper.toDto(delivery);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDeliveryMockMvc.perform(put("/api/deliveries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryDTO)))
            .andExpect(status().isCreated());

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll();
        assertThat(deliveryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDelivery() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);
        int databaseSizeBeforeDelete = deliveryRepository.findAll().size();

        // Get the delivery
        restDeliveryMockMvc.perform(delete("/api/deliveries/{id}", delivery.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Delivery> deliveryList = deliveryRepository.findAll();
        assertThat(deliveryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Delivery.class);
        Delivery delivery1 = new Delivery();
        delivery1.setId(1L);
        Delivery delivery2 = new Delivery();
        delivery2.setId(delivery1.getId());
        assertThat(delivery1).isEqualTo(delivery2);
        delivery2.setId(2L);
        assertThat(delivery1).isNotEqualTo(delivery2);
        delivery1.setId(null);
        assertThat(delivery1).isNotEqualTo(delivery2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeliveryDTO.class);
        DeliveryDTO deliveryDTO1 = new DeliveryDTO();
        deliveryDTO1.setId(1L);
        DeliveryDTO deliveryDTO2 = new DeliveryDTO();
        assertThat(deliveryDTO1).isNotEqualTo(deliveryDTO2);
        deliveryDTO2.setId(deliveryDTO1.getId());
        assertThat(deliveryDTO1).isEqualTo(deliveryDTO2);
        deliveryDTO2.setId(2L);
        assertThat(deliveryDTO1).isNotEqualTo(deliveryDTO2);
        deliveryDTO1.setId(null);
        assertThat(deliveryDTO1).isNotEqualTo(deliveryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(deliveryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(deliveryMapper.fromId(null)).isNull();
    }
}
