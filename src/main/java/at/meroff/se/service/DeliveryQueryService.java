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

import at.meroff.se.domain.Delivery;
import at.meroff.se.domain.*; // for static metamodels
import at.meroff.se.repository.DeliveryRepository;
import at.meroff.se.service.dto.DeliveryCriteria;

import at.meroff.se.service.dto.DeliveryDTO;
import at.meroff.se.service.mapper.DeliveryMapper;
import at.meroff.se.domain.enumeration.UnloadingType;
import at.meroff.se.domain.enumeration.AvisoType;
import at.meroff.se.domain.enumeration.DeliveryStatus;
import at.meroff.se.domain.enumeration.LKWType;

/**
 * Service for executing complex queries for Delivery entities in the database.
 * The main input is a {@link DeliveryCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link DeliveryDTO} or a {@link Page} of {%link DeliveryDTO} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class DeliveryQueryService extends QueryService<Delivery> {

    private final Logger log = LoggerFactory.getLogger(DeliveryQueryService.class);


    private final DeliveryRepository deliveryRepository;

    private final DeliveryMapper deliveryMapper;

    public DeliveryQueryService(DeliveryRepository deliveryRepository, DeliveryMapper deliveryMapper) {
        this.deliveryRepository = deliveryRepository;
        this.deliveryMapper = deliveryMapper;
    }

    /**
     * Return a {@link List} of {%link DeliveryDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DeliveryDTO> findByCriteria(DeliveryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Delivery> specification = createSpecification(criteria);
        return deliveryMapper.toDto(deliveryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {%link DeliveryDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DeliveryDTO> findByCriteria(DeliveryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Delivery> specification = createSpecification(criteria);
        final Page<Delivery> result = deliveryRepository.findAll(specification, page);
        return result.map(deliveryMapper::toDto);
    }

    /**
     * Function to convert DeliveryCriteria to a {@link Specifications}
     */
    private Specifications<Delivery> createSpecification(DeliveryCriteria criteria) {
        Specifications<Delivery> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Delivery_.id));
            }
            if (criteria.getOrderNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrderNumber(), Delivery_.orderNumber));
            }
            if (criteria.getKalenderwoche() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKalenderwoche(), Delivery_.kalenderwoche));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Delivery_.date));
            }
            if (criteria.getUnloading() != null) {
                specification = specification.and(buildSpecification(criteria.getUnloading(), Delivery_.unloading));
            }
            if (criteria.getAvisoType() != null) {
                specification = specification.and(buildSpecification(criteria.getAvisoType(), Delivery_.avisoType));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Delivery_.status));
            }
            if (criteria.getLkwType() != null) {
                specification = specification.and(buildSpecification(criteria.getLkwType(), Delivery_.lkwType));
            }
            if (criteria.getChecklistId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getChecklistId(), Delivery_.checklist, Checklist_.id));
            }
            if (criteria.getWorkpackageId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getWorkpackageId(), Delivery_.workpackage, WorkPackage_.id));
            }
            if (criteria.getPersonId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getPersonId(), Delivery_.person, Person_.id));
            }
            if (criteria.getLocationId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getLocationId(), Delivery_.location, Location_.id));
            }
        }
        return specification;
    }

}
