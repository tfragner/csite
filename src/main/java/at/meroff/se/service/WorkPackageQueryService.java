package at.meroff.se.service;


import java.time.ZonedDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import at.meroff.se.domain.WorkPackage;
import at.meroff.se.domain.*; // for static metamodels
import at.meroff.se.repository.WorkPackageRepository;
import at.meroff.se.service.dto.WorkPackageCriteria;

import at.meroff.se.service.dto.WorkPackageDTO;
import at.meroff.se.service.mapper.WorkPackageMapper;

/**
 * Service for executing complex queries for WorkPackage entities in the database.
 * The main input is a {@link WorkPackageCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link WorkPackageDTO} or a {@link Page} of {%link WorkPackageDTO} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class WorkPackageQueryService extends QueryService<WorkPackage> {

    private final Logger log = LoggerFactory.getLogger(WorkPackageQueryService.class);


    private final WorkPackageRepository workPackageRepository;

    private final WorkPackageMapper workPackageMapper;

    public WorkPackageQueryService(WorkPackageRepository workPackageRepository, WorkPackageMapper workPackageMapper) {
        this.workPackageRepository = workPackageRepository;
        this.workPackageMapper = workPackageMapper;
    }

    /**
     * Return a {@link List} of {%link WorkPackageDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WorkPackageDTO> findByCriteria(WorkPackageCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<WorkPackage> specification = createSpecification(criteria);
        return workPackageMapper.toDto(workPackageRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {%link WorkPackageDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkPackageDTO> findByCriteria(WorkPackageCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<WorkPackage> specification = createSpecification(criteria);
        final Page<WorkPackage> result = workPackageRepository.findAll(specification, page);
        return result.map(workPackageMapper::toDto);
    }

    /**
     * Function to convert WorkPackageCriteria to a {@link Specifications}
     */
    private Specifications<WorkPackage> createSpecification(WorkPackageCriteria criteria) {
        Specifications<WorkPackage> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), WorkPackage_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), WorkPackage_.name));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), WorkPackage_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), WorkPackage_.endDate));
            }
            if (criteria.getConstructionsiteId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getConstructionsiteId(), WorkPackage_.constructionsite, ConstructionSite_.id));
            }
        }
        return specification;
    }

}
