package at.meroff.se.web.rest;

import com.codahale.metrics.annotation.Timed;
import at.meroff.se.service.ConstructionSiteService;
import at.meroff.se.web.rest.errors.BadRequestAlertException;
import at.meroff.se.web.rest.util.HeaderUtil;
import at.meroff.se.service.dto.ConstructionSiteDTO;
import at.meroff.se.service.dto.ConstructionSiteCriteria;
import at.meroff.se.service.ConstructionSiteQueryService;
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
 * REST controller for managing ConstructionSite.
 */
@RestController
@RequestMapping("/api")
public class ConstructionSiteResource {

    private final Logger log = LoggerFactory.getLogger(ConstructionSiteResource.class);

    private static final String ENTITY_NAME = "constructionSite";

    private final ConstructionSiteService constructionSiteService;

    private final ConstructionSiteQueryService constructionSiteQueryService;

    public ConstructionSiteResource(ConstructionSiteService constructionSiteService, ConstructionSiteQueryService constructionSiteQueryService) {
        this.constructionSiteService = constructionSiteService;
        this.constructionSiteQueryService = constructionSiteQueryService;
    }

    /**
     * POST  /construction-sites : Create a new constructionSite.
     *
     * @param constructionSiteDTO the constructionSiteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new constructionSiteDTO, or with status 400 (Bad Request) if the constructionSite has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/construction-sites")
    @Timed
    public ResponseEntity<ConstructionSiteDTO> createConstructionSite(@RequestBody ConstructionSiteDTO constructionSiteDTO) throws URISyntaxException {
        log.debug("REST request to save ConstructionSite : {}", constructionSiteDTO);
        if (constructionSiteDTO.getId() != null) {
            throw new BadRequestAlertException("A new constructionSite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConstructionSiteDTO result = constructionSiteService.save(constructionSiteDTO);
        return ResponseEntity.created(new URI("/api/construction-sites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /construction-sites : Updates an existing constructionSite.
     *
     * @param constructionSiteDTO the constructionSiteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated constructionSiteDTO,
     * or with status 400 (Bad Request) if the constructionSiteDTO is not valid,
     * or with status 500 (Internal Server Error) if the constructionSiteDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/construction-sites")
    @Timed
    public ResponseEntity<ConstructionSiteDTO> updateConstructionSite(@RequestBody ConstructionSiteDTO constructionSiteDTO) throws URISyntaxException {
        log.debug("REST request to update ConstructionSite : {}", constructionSiteDTO);
        if (constructionSiteDTO.getId() == null) {
            return createConstructionSite(constructionSiteDTO);
        }
        ConstructionSiteDTO result = constructionSiteService.save(constructionSiteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, constructionSiteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /construction-sites : get all the constructionSites.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of constructionSites in body
     */
    @GetMapping("/construction-sites")
    @Timed
    public ResponseEntity<List<ConstructionSiteDTO>> getAllConstructionSites(ConstructionSiteCriteria criteria) {
        log.debug("REST request to get ConstructionSites by criteria: {}", criteria);
        List<ConstructionSiteDTO> entityList = constructionSiteQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /construction-sites/:id : get the "id" constructionSite.
     *
     * @param id the id of the constructionSiteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the constructionSiteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/construction-sites/{id}")
    @Timed
    public ResponseEntity<ConstructionSiteDTO> getConstructionSite(@PathVariable Long id) {
        log.debug("REST request to get ConstructionSite : {}", id);
        ConstructionSiteDTO constructionSiteDTO = constructionSiteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(constructionSiteDTO));
    }

    /**
     * DELETE  /construction-sites/:id : delete the "id" constructionSite.
     *
     * @param id the id of the constructionSiteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/construction-sites/{id}")
    @Timed
    public ResponseEntity<Void> deleteConstructionSite(@PathVariable Long id) {
        log.debug("REST request to delete ConstructionSite : {}", id);
        constructionSiteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
