package at.meroff.se.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import at.meroff.se.domain.Checklist;
import at.meroff.se.domain.*; // for static metamodels
import at.meroff.se.repository.ChecklistRepository;
import at.meroff.se.service.dto.ChecklistCriteria;

import at.meroff.se.service.dto.ChecklistDTO;
import at.meroff.se.service.mapper.ChecklistMapper;

/**
 * Service for executing complex queries for Checklist entities in the database.
 * The main input is a {@link ChecklistCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link ChecklistDTO} or a {@link Page} of {%link ChecklistDTO} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class ChecklistQueryService extends QueryService<Checklist> {

    private final Logger log = LoggerFactory.getLogger(ChecklistQueryService.class);


    private final ChecklistRepository checklistRepository;

    private final ChecklistMapper checklistMapper;

    public ChecklistQueryService(ChecklistRepository checklistRepository, ChecklistMapper checklistMapper) {
        this.checklistRepository = checklistRepository;
        this.checklistMapper = checklistMapper;
    }

    /**
     * Return a {@link List} of {%link ChecklistDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ChecklistDTO> findByCriteria(ChecklistCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Checklist> specification = createSpecification(criteria);
        return checklistMapper.toDto(checklistRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {%link ChecklistDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ChecklistDTO> findByCriteria(ChecklistCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Checklist> specification = createSpecification(criteria);
        final Page<Checklist> result = checklistRepository.findAll(specification, page);
        return result.map(checklistMapper::toDto);
    }

    /**
     * Function to convert ChecklistCriteria to a {@link Specifications}
     */
    private Specifications<Checklist> createSpecification(ChecklistCriteria criteria) {
        Specifications<Checklist> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Checklist_.id));
            }
            if (criteria.getInTime() != null) {
                specification = specification.and(buildSpecification(criteria.getInTime(), Checklist_.inTime));
            }
            if (criteria.getComplete() != null) {
                specification = specification.and(buildSpecification(criteria.getComplete(), Checklist_.complete));
            }
            if (criteria.getUnloadingOk() != null) {
                specification = specification.and(buildSpecification(criteria.getUnloadingOk(), Checklist_.unloadingOk));
            }
            if (criteria.getNotDamaged() != null) {
                specification = specification.and(buildSpecification(criteria.getNotDamaged(), Checklist_.notDamaged));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Checklist_.description));
            }
            if (criteria.getClaim() != null) {
                specification = specification.and(buildSpecification(criteria.getClaim(), Checklist_.claim));
            }
            if (criteria.getDeliveryId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getDeliveryId(), Checklist_.delivery, Delivery_.id));
            }
        }
        return specification;
    }

}
