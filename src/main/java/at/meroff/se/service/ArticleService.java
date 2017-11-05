package at.meroff.se.service;

import at.meroff.se.domain.Article;
import at.meroff.se.repository.ArticleRepository;
import at.meroff.se.service.dto.ArticleDTO;
import at.meroff.se.service.mapper.ArticleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Article.
 */
@Service
@Transactional
public class ArticleService {

    private final Logger log = LoggerFactory.getLogger(ArticleService.class);

    private final ArticleRepository articleRepository;

    private final ArticleMapper articleMapper;

    public ArticleService(ArticleRepository articleRepository, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
    }

    /**
     * Save a article.
     *
     * @param articleDTO the entity to save
     * @return the persisted entity
     */
    public ArticleDTO save(ArticleDTO articleDTO) {
        log.debug("Request to save Article : {}", articleDTO);
        Article article = articleMapper.toEntity(articleDTO);
        article = articleRepository.save(article);
        return articleMapper.toDto(article);
    }

    /**
     *  Get all the articles.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ArticleDTO> findAll() {
        log.debug("Request to get all Articles");
        return articleRepository.findAll().stream()
            .map(articleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one article by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ArticleDTO findOne(Long id) {
        log.debug("Request to get Article : {}", id);
        Article article = articleRepository.findOne(id);
        return articleMapper.toDto(article);
    }

    /**
     *  Delete the  article by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Article : {}", id);
        articleRepository.delete(id);
    }
}
