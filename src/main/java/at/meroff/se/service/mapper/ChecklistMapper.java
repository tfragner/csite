package at.meroff.se.service.mapper;

import at.meroff.se.domain.*;
import at.meroff.se.service.dto.ChecklistDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Checklist and its DTO ChecklistDTO.
 */
@Mapper(componentModel = "spring", uses = {DeliveryMapper.class})
public interface ChecklistMapper extends EntityMapper<ChecklistDTO, Checklist> {

    @Mapping(source = "delivery.id", target = "deliveryId")
    ChecklistDTO toDto(Checklist checklist); 

    @Mapping(source = "deliveryId", target = "delivery")
    Checklist toEntity(ChecklistDTO checklistDTO);

    default Checklist fromId(Long id) {
        if (id == null) {
            return null;
        }
        Checklist checklist = new Checklist();
        checklist.setId(id);
        return checklist;
    }
}
