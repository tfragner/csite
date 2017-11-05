package at.meroff.se.service;

import at.meroff.se.domain.ConstructionSite;
import at.meroff.se.repository.ConstructionSiteRepository;
import at.meroff.se.service.dto.ConstructionSiteDTO;
import at.meroff.se.service.mapper.ConstructionSiteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ConstructionSite.
 */
@Service
@Transactional
public class ConstructionSiteService {

    private final Logger log = LoggerFactory.getLogger(ConstructionSiteService.class);

    private final ConstructionSiteRepository constructionSiteRepository;

    private final ConstructionSiteMapper constructionSiteMapper;

    public ConstructionSiteService(ConstructionSiteRepository constructionSiteRepository, ConstructionSiteMapper constructionSiteMapper) {
        this.constructionSiteRepository = constructionSiteRepository;
        this.constructionSiteMapper = constructionSiteMapper;
    }

    /**
     * Save a constructionSite.
     *
     * @param constructionSiteDTO the entity to save
     * @return the persisted entity
     */
    public ConstructionSiteDTO save(ConstructionSiteDTO constructionSiteDTO) {
        log.debug("Request to save ConstructionSite : {}", constructionSiteDTO);
        ConstructionSite constructionSite = constructionSiteMapper.toEntity(constructionSiteDTO);
        constructionSite = constructionSiteRepository.save(constructionSite);
        return constructionSiteMapper.toDto(constructionSite);
    }

    /**
     *  Get all the constructionSites.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ConstructionSiteDTO> findAll() {
        log.debug("Request to get all ConstructionSites");
        return constructionSiteRepository.findAll().stream()
            .map(constructionSiteMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one constructionSite by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ConstructionSiteDTO findOne(Long id) {
        log.debug("Request to get ConstructionSite : {}", id);
        ConstructionSite constructionSite = constructionSiteRepository.findOne(id);
        return constructionSiteMapper.toDto(constructionSite);
    }

    /**
     *  Delete the  constructionSite by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ConstructionSite : {}", id);
        constructionSiteRepository.delete(id);
    }
}
