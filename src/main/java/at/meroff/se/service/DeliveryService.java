package at.meroff.se.service;

import at.meroff.se.domain.Checklist;
import at.meroff.se.domain.Delivery;
import at.meroff.se.domain.enumeration.DeliveryStatus;
import at.meroff.se.repository.ChecklistRepository;
import at.meroff.se.repository.DeliveryRepository;
import at.meroff.se.service.dto.DeliveryDTO;
import at.meroff.se.service.mapper.DeliveryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Delivery.
 */
@Service
@Transactional
public class DeliveryService {

    private final Logger log = LoggerFactory.getLogger(DeliveryService.class);

    private final DeliveryRepository deliveryRepository;

    private final DeliveryMapper deliveryMapper;

    private final ChecklistRepository checklistRepository;

    public DeliveryService(DeliveryRepository deliveryRepository, DeliveryMapper deliveryMapper, ChecklistRepository checklistRepository) {
        this.deliveryRepository = deliveryRepository;
        this.deliveryMapper = deliveryMapper;
        this.checklistRepository = checklistRepository;
    }

    /**
     * Save a delivery.
     *
     * @param deliveryDTO the entity to save
     * @return the persisted entity
     */
    public DeliveryDTO save(DeliveryDTO deliveryDTO) {
        log.debug("Request to save Delivery : {}", deliveryDTO);
        Delivery delivery = deliveryMapper.toEntity(deliveryDTO);
        delivery = deliveryRepository.save(delivery);

        if (Objects.isNull(deliveryDTO.getId())) {
            Checklist newChecklist = new Checklist();
            newChecklist.setComplete(false);
            newChecklist.setDescription("");
            newChecklist.setInTime(false);
            newChecklist.setNotDamaged(false);
            newChecklist.setUnloadingOk(false);
            newChecklist.setDelivery(delivery);
            newChecklist = checklistRepository.save(newChecklist);
            delivery.setChecklist(newChecklist);
        }


        delivery = deliveryRepository.save(delivery);
        return deliveryMapper.toDto(delivery);
    }

    /**
     *  Get all the deliveries.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<DeliveryDTO> findAll() {
        log.debug("Request to get all Deliveries");
        return deliveryRepository.findAll().stream()
            .map(deliveryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the deliveries.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<DeliveryDTO> findAllByCsiteId(Long id) {
        log.debug("Request to get all Deliveries");
        LinkedList<Delivery> collect = deliveryRepository.findAllByWorkpackage_Constructionsite_Id(id).stream()
            .collect(Collectors.toCollection(LinkedList::new));
        LinkedList<DeliveryDTO> collect1 = collect.stream()
            .map(deliveryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
        return collect1;
    }


    /**
     *  get all the deliveries where Checklist is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<DeliveryDTO> findAllWhereChecklistIsNull() {
        log.debug("Request to get all deliveries where Checklist is null");
        return StreamSupport
            .stream(deliveryRepository.findAll().spliterator(), false)
            .filter(delivery -> delivery.getChecklist() == null)
            .map(deliveryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one delivery by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public DeliveryDTO findOne(Long id) {
        log.debug("Request to get Delivery : {}", id);
        Delivery delivery = deliveryRepository.findOne(id);
        return deliveryMapper.toDto(delivery);
    }

    /**
     *  Delete the  delivery by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Delivery : {}", id);
        deliveryRepository.delete(id);
    }
}
