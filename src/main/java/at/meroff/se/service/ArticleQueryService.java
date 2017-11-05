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

import at.meroff.se.domain.Article;
import at.meroff.se.domain.*; // for static metamodels
import at.meroff.se.repository.ArticleRepository;
import at.meroff.se.service.dto.ArticleCriteria;

import at.meroff.se.service.dto.ArticleDTO;
import at.meroff.se.service.mapper.ArticleMapper;

/**
 * Service for executing complex queries for Article entities in the database.
 * The main input is a {@link ArticleCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link ArticleDTO} or a {@link Page} of {%link ArticleDTO} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class ArticleQueryService extends QueryService<Article> {

    private final Logger log = LoggerFactory.getLogger(ArticleQueryService.class);


    private final ArticleRepository articleRepository;

    private final ArticleMapper articleMapper;

    public ArticleQueryService(ArticleRepository articleRepository, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
    }

    /**
     * Return a {@link List} of {%link ArticleDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ArticleDTO> findByCriteria(ArticleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Article> specification = createSpecification(criteria);
        return articleMapper.toDto(articleRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {%link ArticleDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ArticleDTO> findByCriteria(ArticleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Article> specification = createSpecification(criteria);
        final Page<Article> result = articleRepository.findAll(specification, page);
        return result.map(articleMapper::toDto);
    }

    /**
     * Function to convert ArticleCriteria to a {@link Specifications}
     */
    private Specifications<Article> createSpecification(ArticleCriteria criteria) {
        Specifications<Article> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Article_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Article_.name));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), Article_.quantity));
            }
            if (criteria.getDeliveryId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getDeliveryId(), Article_.delivery, Delivery_.id));
            }
        }
        return specification;
    }

}
