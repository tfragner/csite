package at.meroff.se.web.rest;

import com.codahale.metrics.annotation.Timed;
import at.meroff.se.service.WorkPackageService;
import at.meroff.se.web.rest.errors.BadRequestAlertException;
import at.meroff.se.web.rest.util.HeaderUtil;
import at.meroff.se.service.dto.WorkPackageDTO;
import at.meroff.se.service.dto.WorkPackageCriteria;
import at.meroff.se.service.WorkPackageQueryService;
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
 * REST controller for managing WorkPackage.
 */
@RestController
@RequestMapping("/api")
public class WorkPackageResource {

    private final Logger log = LoggerFactory.getLogger(WorkPackageResource.class);

    private static final String ENTITY_NAME = "workPackage";

    private final WorkPackageService workPackageService;

    private final WorkPackageQueryService workPackageQueryService;

    public WorkPackageResource(WorkPackageService workPackageService, WorkPackageQueryService workPackageQueryService) {
        this.workPackageService = workPackageService;
        this.workPackageQueryService = workPackageQueryService;
    }

    /**
     * POST  /work-packages : Create a new workPackage.
     *
     * @param workPackageDTO the workPackageDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new workPackageDTO, or with status 400 (Bad Request) if the workPackage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/work-packages")
    @Timed
    public ResponseEntity<WorkPackageDTO> createWorkPackage(@RequestBody WorkPackageDTO workPackageDTO) throws URISyntaxException {
        log.debug("REST request to save WorkPackage : {}", workPackageDTO);
        if (workPackageDTO.getId() != null) {
            throw new BadRequestAlertException("A new workPackage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkPackageDTO result = workPackageService.save(workPackageDTO);
        return ResponseEntity.created(new URI("/api/work-packages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /work-packages : Updates an existing workPackage.
     *
     * @param workPackageDTO the workPackageDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated workPackageDTO,
     * or with status 400 (Bad Request) if the workPackageDTO is not valid,
     * or with status 500 (Internal Server Error) if the workPackageDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/work-packages")
    @Timed
    public ResponseEntity<WorkPackageDTO> updateWorkPackage(@RequestBody WorkPackageDTO workPackageDTO) throws URISyntaxException {
        log.debug("REST request to update WorkPackage : {}", workPackageDTO);
        if (workPackageDTO.getId() == null) {
            return createWorkPackage(workPackageDTO);
        }
        WorkPackageDTO result = workPackageService.save(workPackageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, workPackageDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /work-packages : get all the workPackages.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of workPackages in body
     */
    @GetMapping("/work-packages")
    @Timed
    public ResponseEntity<List<WorkPackageDTO>> getAllWorkPackages(WorkPackageCriteria criteria) {
        log.debug("REST request to get WorkPackages by criteria: {}", criteria);
        List<WorkPackageDTO> entityList = workPackageQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /work-packages/:id : get the "id" workPackage.
     *
     * @param id the id of the workPackageDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the workPackageDTO, or with status 404 (Not Found)
     */
    @GetMapping("/work-packages/{id}")
    @Timed
    public ResponseEntity<WorkPackageDTO> getWorkPackage(@PathVariable Long id) {
        log.debug("REST request to get WorkPackage : {}", id);
        WorkPackageDTO workPackageDTO = workPackageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(workPackageDTO));
    }

    /**
     * DELETE  /work-packages/:id : delete the "id" workPackage.
     *
     * @param id the id of the workPackageDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/work-packages/{id}")
    @Timed
    public ResponseEntity<Void> deleteWorkPackage(@PathVariable Long id) {
        log.debug("REST request to delete WorkPackage : {}", id);
        workPackageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /work-packages/construction_site/:id : get all the workPackages by construction site id.
     *
     * @param id the construction site id
     * @return the ResponseEntity with status 200 (OK) and the list of workPackages in body
     */
    @GetMapping("/work-packages/construction_site/{id}")
    @Timed
    public ResponseEntity<List<WorkPackageDTO>> getAllByConstructionsite_Id(@PathVariable Long id) {
        log.debug("REST request to get WorkPackages by construction site id: {}", id);
        List<WorkPackageDTO> entityList = workPackageService.findAllByConstructionsite_Id(id);
        return ResponseEntity.ok().body(entityList);
    }
}
