package at.meroff.se.service;

import at.meroff.se.domain.Checklist;
import at.meroff.se.domain.Delivery;
import at.meroff.se.domain.enumeration.DeliveryStatus;
import at.meroff.se.repository.ChecklistRepository;
import at.meroff.se.repository.DeliveryRepository;
import at.meroff.se.service.dto.ChecklistDTO;
import at.meroff.se.service.mapper.ChecklistMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Checklist.
 */
@Service
@Transactional
public class ChecklistService {

    private final Logger log = LoggerFactory.getLogger(ChecklistService.class);

    private final ChecklistRepository checklistRepository;

    private final ChecklistMapper checklistMapper;

    private final DeliveryRepository deliveryRepository;

    public ChecklistService(ChecklistRepository checklistRepository, ChecklistMapper checklistMapper, DeliveryRepository deliveryRepository) {
        this.checklistRepository = checklistRepository;
        this.checklistMapper = checklistMapper;
        this.deliveryRepository = deliveryRepository;
    }

    /**
     * Save a checklist.
     *
     * @param checklistDTO the entity to save
     * @return the persisted entity
     */
    public ChecklistDTO save(ChecklistDTO checklistDTO) {
        if (checklistDTO.isComplete() && checklistDTO.isInTime() && checklistDTO.isNotDamaged() && checklistDTO.isUnloadingOk()) {
            Delivery one = deliveryRepository.findOne(checklistDTO.getDeliveryId());
            one.setStatus(DeliveryStatus.CLOSED);
            deliveryRepository.save(one);
        }
        log.debug("Request to save Checklist : {}", checklistDTO);
        Checklist checklist = checklistMapper.toEntity(checklistDTO);
        checklist = checklistRepository.save(checklist);
        return checklistMapper.toDto(checklist);
    }

    /**
     *  Get all the checklists.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ChecklistDTO> findAll() {
        log.debug("Request to get all Checklists");
        return checklistRepository.findAll().stream()
            .map(checklistMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one checklist by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ChecklistDTO findOne(Long id) {
        log.debug("Request to get Checklist : {}", id);
        Checklist checklist = checklistRepository.findOne(id);
        return checklistMapper.toDto(checklist);
    }

    /**
     *  Delete the  checklist by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Checklist : {}", id);
        checklistRepository.delete(id);
    }
}
