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

import at.meroff.se.domain.ConstructionSite;
import at.meroff.se.domain.*; // for static metamodels
import at.meroff.se.repository.ConstructionSiteRepository;
import at.meroff.se.service.dto.ConstructionSiteCriteria;

import at.meroff.se.service.dto.ConstructionSiteDTO;
import at.meroff.se.service.mapper.ConstructionSiteMapper;
import at.meroff.se.domain.enumeration.LKWType;

/**
 * Service for executing complex queries for ConstructionSite entities in the database.
 * The main input is a {@link ConstructionSiteCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link ConstructionSiteDTO} or a {@link Page} of {%link ConstructionSiteDTO} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class ConstructionSiteQueryService extends QueryService<ConstructionSite> {

    private final Logger log = LoggerFactory.getLogger(ConstructionSiteQueryService.class);


    private final ConstructionSiteRepository constructionSiteRepository;

    private final ConstructionSiteMapper constructionSiteMapper;

    public ConstructionSiteQueryService(ConstructionSiteRepository constructionSiteRepository, ConstructionSiteMapper constructionSiteMapper) {
        this.constructionSiteRepository = constructionSiteRepository;
        this.constructionSiteMapper = constructionSiteMapper;
    }

    /**
     * Return a {@link List} of {%link ConstructionSiteDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ConstructionSiteDTO> findByCriteria(ConstructionSiteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<ConstructionSite> specification = createSpecification(criteria);
        return constructionSiteMapper.toDto(constructionSiteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {%link ConstructionSiteDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ConstructionSiteDTO> findByCriteria(ConstructionSiteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<ConstructionSite> specification = createSpecification(criteria);
        final Page<ConstructionSite> result = constructionSiteRepository.findAll(specification, page);
        return result.map(constructionSiteMapper::toDto);
    }

    /**
     * Function to convert ConstructionSiteCriteria to a {@link Specifications}
     */
    private Specifications<ConstructionSite> createSpecification(ConstructionSiteCriteria criteria) {
        Specifications<ConstructionSite> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ConstructionSite_.id));
            }
            if (criteria.getPrjNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrjNumber(), ConstructionSite_.prjNumber));
            }
            if (criteria.getPrjName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrjName(), ConstructionSite_.prjName));
            }
            if (criteria.getKran() != null) {
                specification = specification.and(buildSpecification(criteria.getKran(), ConstructionSite_.kran));
            }
            if (criteria.getStapler() != null) {
                specification = specification.and(buildSpecification(criteria.getStapler(), ConstructionSite_.stapler));
            }
            if (criteria.getMaxLKWType() != null) {
                specification = specification.and(buildSpecification(criteria.getMaxLKWType(), ConstructionSite_.maxLKWType));
            }
            if (criteria.getContainerId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getContainerId(), ConstructionSite_.container, Person_.id));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCustomerId(), ConstructionSite_.customer, Person_.id));
            }
        }
        return specification;
    }

}
