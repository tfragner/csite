package at.meroff.se.web.rest;

import com.codahale.metrics.annotation.Timed;
import at.meroff.se.service.ChecklistService;
import at.meroff.se.web.rest.errors.BadRequestAlertException;
import at.meroff.se.web.rest.util.HeaderUtil;
import at.meroff.se.service.dto.ChecklistDTO;
import at.meroff.se.service.dto.ChecklistCriteria;
import at.meroff.se.service.ChecklistQueryService;
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
 * REST controller for managing Checklist.
 */
@RestController
@RequestMapping("/api")
public class ChecklistResource {

    private final Logger log = LoggerFactory.getLogger(ChecklistResource.class);

    private static final String ENTITY_NAME = "checklist";

    private final ChecklistService checklistService;

    private final ChecklistQueryService checklistQueryService;

    public ChecklistResource(ChecklistService checklistService, ChecklistQueryService checklistQueryService) {
        this.checklistService = checklistService;
        this.checklistQueryService = checklistQueryService;
    }

    /**
     * POST  /checklists : Create a new checklist.
     *
     * @param checklistDTO the checklistDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new checklistDTO, or with status 400 (Bad Request) if the checklist has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/checklists")
    @Timed
    public ResponseEntity<ChecklistDTO> createChecklist(@RequestBody ChecklistDTO checklistDTO) throws URISyntaxException {
        log.debug("REST request to save Checklist : {}", checklistDTO);
        if (checklistDTO.getId() != null) {
            throw new BadRequestAlertException("A new checklist cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChecklistDTO result = checklistService.save(checklistDTO);
        return ResponseEntity.created(new URI("/api/checklists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /checklists : Updates an existing checklist.
     *
     * @param checklistDTO the checklistDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated checklistDTO,
     * or with status 400 (Bad Request) if the checklistDTO is not valid,
     * or with status 500 (Internal Server Error) if the checklistDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/checklists")
    @Timed
    public ResponseEntity<ChecklistDTO> updateChecklist(@RequestBody ChecklistDTO checklistDTO) throws URISyntaxException {
        log.debug("REST request to update Checklist : {}", checklistDTO);
        if (checklistDTO.getId() == null) {
            return createChecklist(checklistDTO);
        }
        ChecklistDTO result = checklistService.save(checklistDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, checklistDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /checklists : get all the checklists.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of checklists in body
     */
    @GetMapping("/checklists")
    @Timed
    public ResponseEntity<List<ChecklistDTO>> getAllChecklists(ChecklistCriteria criteria) {
        log.debug("REST request to get Checklists by criteria: {}", criteria);
        List<ChecklistDTO> entityList = checklistQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /checklists/:id : get the "id" checklist.
     *
     * @param id the id of the checklistDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the checklistDTO, or with status 404 (Not Found)
     */
    @GetMapping("/checklists/{id}")
    @Timed
    public ResponseEntity<ChecklistDTO> getChecklist(@PathVariable Long id) {
        log.debug("REST request to get Checklist : {}", id);
        ChecklistDTO checklistDTO = checklistService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(checklistDTO));
    }

    /**
     * DELETE  /checklists/:id : delete the "id" checklist.
     *
     * @param id the id of the checklistDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/checklists/{id}")
    @Timed
    public ResponseEntity<Void> deleteChecklist(@PathVariable Long id) {
        log.debug("REST request to delete Checklist : {}", id);
        checklistService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
