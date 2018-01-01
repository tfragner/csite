package at.meroff.se.web.rest;

import com.codahale.metrics.annotation.Timed;
import at.meroff.se.service.LocationService;
import at.meroff.se.web.rest.errors.BadRequestAlertException;
import at.meroff.se.web.rest.util.HeaderUtil;
import at.meroff.se.service.dto.LocationDTO;
import at.meroff.se.service.dto.LocationCriteria;
import at.meroff.se.service.LocationQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Location.
 */
@RestController
@RequestMapping("/api")
public class LocationResource {

    private final Logger log = LoggerFactory.getLogger(LocationResource.class);

    private static final String ENTITY_NAME = "location";

    private final LocationService locationService;

    private final LocationQueryService locationQueryService;

    public LocationResource(LocationService locationService, LocationQueryService locationQueryService) {
        this.locationService = locationService;
        this.locationQueryService = locationQueryService;
    }

    /**
     * POST  /locations : Create a new location.
     *
     * @param locationDTO the locationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new locationDTO, or with status 400 (Bad Request) if the location has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/locations")
    @Timed
    public ResponseEntity<LocationDTO> createLocation(@RequestBody LocationDTO locationDTO) throws URISyntaxException {
        log.debug("REST request to save Location : {}", locationDTO);
        if (locationDTO.getId() != null) {
            throw new BadRequestAlertException("A new location cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LocationDTO result = locationService.save(locationDTO);
        return ResponseEntity.created(new URI("/api/locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /locations : Updates an existing location.
     *
     * @param locationDTO the locationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated locationDTO,
     * or with status 400 (Bad Request) if the locationDTO is not valid,
     * or with status 500 (Internal Server Error) if the locationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/locations")
    @Timed
    public ResponseEntity<LocationDTO> updateLocation(@RequestBody LocationDTO locationDTO) throws URISyntaxException {
        log.debug("REST request to update Location : {}", locationDTO);
        if (locationDTO.getId() == null) {
            return createLocation(locationDTO);
        }
        LocationDTO result = locationService.save(locationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, locationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /locations : get all the locations.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of locations in body
     */
    @GetMapping("/locations")
    @Timed
    public ResponseEntity<List<LocationDTO>> getAllLocations(LocationCriteria criteria) {
        log.debug("REST request to get Locations by criteria: {}", criteria);
        List<LocationDTO> entityList = locationQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /locations/:id : get the "id" location.
     *
     * @param id the id of the locationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the locationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/locations/{id}")
    @Timed
    public ResponseEntity<LocationDTO> getLocation(@PathVariable Long id) {
        log.debug("REST request to get Location : {}", id);
        LocationDTO locationDTO = locationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(locationDTO));
    }

    /**
     * DELETE  /locations/:id : delete the "id" location.
     *
     * @param id the id of the locationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/locations/{id}")
    @Timed
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        log.debug("REST request to delete Location : {}", id);
        locationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /locations/:id : get the "id" location.
     *
     * @param id the id of the locationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the locationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/locations/construction_site/{id}")
    @Timed
    public ResponseEntity<List<LocationDTO>> getAllByConstructionsite_Id(@PathVariable Long id) {
        log.debug("REST request to get Location : {}", id);
        List<LocationDTO> entityList = locationService.findAllByConstructionsite_Id(id);
        return ResponseEntity.ok().body(entityList);
    }

}
