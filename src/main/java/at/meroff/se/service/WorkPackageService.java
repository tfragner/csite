package at.meroff.se.service;

import at.meroff.se.domain.WorkPackage;
import at.meroff.se.repository.WorkPackageRepository;
import at.meroff.se.service.dto.WorkPackageDTO;
import at.meroff.se.service.mapper.WorkPackageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing WorkPackage.
 */
@Service
@Transactional
public class WorkPackageService {

    private final Logger log = LoggerFactory.getLogger(WorkPackageService.class);

    private final WorkPackageRepository workPackageRepository;

    private final WorkPackageMapper workPackageMapper;

    public WorkPackageService(WorkPackageRepository workPackageRepository, WorkPackageMapper workPackageMapper) {
        this.workPackageRepository = workPackageRepository;
        this.workPackageMapper = workPackageMapper;
    }

    /**
     * Save a workPackage.
     *
     * @param workPackageDTO the entity to save
     * @return the persisted entity
     */
    public WorkPackageDTO save(WorkPackageDTO workPackageDTO) {
        log.debug("Request to save WorkPackage : {}", workPackageDTO);
        WorkPackage workPackage = workPackageMapper.toEntity(workPackageDTO);
        workPackage = workPackageRepository.save(workPackage);
        return workPackageMapper.toDto(workPackage);
    }

    /**
     *  Get all the workPackages.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<WorkPackageDTO> findAll() {
        log.debug("Request to get all WorkPackages");
        return workPackageRepository.findAll().stream()
            .map(workPackageMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one workPackage by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public WorkPackageDTO findOne(Long id) {
        log.debug("Request to get WorkPackage : {}", id);
        WorkPackage workPackage = workPackageRepository.findOne(id);
        return workPackageMapper.toDto(workPackage);
    }

    /**
     *  Delete the  workPackage by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkPackage : {}", id);
        workPackageRepository.delete(id);
    }
}
