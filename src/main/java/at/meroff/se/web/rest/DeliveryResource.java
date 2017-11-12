package at.meroff.se.web.rest;

import com.codahale.metrics.annotation.Timed;
import at.meroff.se.service.DeliveryService;
import at.meroff.se.web.rest.errors.BadRequestAlertException;
import at.meroff.se.web.rest.util.HeaderUtil;
import at.meroff.se.service.dto.DeliveryDTO;
import at.meroff.se.service.dto.DeliveryCriteria;
import at.meroff.se.service.DeliveryQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Delivery.
 */
@RestController
@RequestMapping("/api")
public class DeliveryResource {

    private final Logger log = LoggerFactory.getLogger(DeliveryResource.class);

    private static final String ENTITY_NAME = "delivery";

    private final DeliveryService deliveryService;

    private final DeliveryQueryService deliveryQueryService;

    public DeliveryResource(DeliveryService deliveryService, DeliveryQueryService deliveryQueryService) {
        this.deliveryService = deliveryService;
        this.deliveryQueryService = deliveryQueryService;
    }

    /**
     * POST  /deliveries : Create a new delivery.
     *
     * @param deliveryDTO the deliveryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new deliveryDTO, or with status 400 (Bad Request) if the delivery has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/deliveries")
    @Timed
    public ResponseEntity<DeliveryDTO> createDelivery(@RequestBody DeliveryDTO deliveryDTO) throws URISyntaxException {
        log.debug("REST request to save Delivery : {}", deliveryDTO);
        if (deliveryDTO.getId() != null) {
            throw new BadRequestAlertException("A new delivery cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeliveryDTO result = deliveryService.save(deliveryDTO);
        return ResponseEntity.created(new URI("/api/deliveries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /deliveries : Updates an existing delivery.
     *
     * @param deliveryDTO the deliveryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated deliveryDTO,
     * or with status 400 (Bad Request) if the deliveryDTO is not valid,
     * or with status 500 (Internal Server Error) if the deliveryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/deliveries")
    @Timed
    public ResponseEntity<DeliveryDTO> updateDelivery(@RequestBody DeliveryDTO deliveryDTO) throws URISyntaxException {
        log.debug("REST request to update Delivery : {}", deliveryDTO);
        if (deliveryDTO.getId() == null) {
            return createDelivery(deliveryDTO);
        }
        DeliveryDTO result = deliveryService.save(deliveryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, deliveryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /deliveries : get all the deliveries.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of deliveries in body
     */
    @GetMapping("/deliveries")
    @Timed
    public ResponseEntity<List<DeliveryDTO>> getAllDeliveries(DeliveryCriteria criteria) {
        log.debug("REST request to get Deliveries by criteria: {}", criteria);
        List<DeliveryDTO> entityList = deliveryQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /deliveries : get all the deliveries.
     *
     * @param id the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of deliveries in body
     */
    @GetMapping("/deliveries/byCsiteId/{id}")
    @Timed
    public ResponseEntity<List<DeliveryDTO>> getAllDeliveriesByCsiteId(@PathVariable Long id) {
        log.debug("REST request to get Deliveries by csite id: {}", id);
        List<DeliveryDTO> entityList = deliveryService.findAllByCsiteId(id);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /deliveries/:id : get the "id" delivery.
     *
     * @param id the id of the deliveryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the deliveryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/deliveries/{id}")
    @Timed
    public ResponseEntity<DeliveryDTO> getDelivery(@PathVariable Long id) {
        log.debug("REST request to get Delivery : {}", id);
        DeliveryDTO deliveryDTO = deliveryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(deliveryDTO));
    }

    /**
     * DELETE  /deliveries/:id : delete the "id" delivery.
     *
     * @param id the id of the deliveryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/deliveries/{id}")
    @Timed
    public ResponseEntity<Void> deleteDelivery(@PathVariable Long id) {
        log.debug("REST request to delete Delivery : {}", id);
        deliveryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
