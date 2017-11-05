package at.meroff.se.service.mapper;

import at.meroff.se.domain.*;
import at.meroff.se.service.dto.DeliveryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Delivery and its DTO DeliveryDTO.
 */
@Mapper(componentModel = "spring", uses = {WorkPackageMapper.class, PersonMapper.class, LocationMapper.class})
public interface DeliveryMapper extends EntityMapper<DeliveryDTO, Delivery> {

    @Mapping(source = "workpackage.id", target = "workpackageId")
    @Mapping(source = "workpackage.name", target = "workpackageName")
    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "person.lastName", target = "personLastName")
    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "location.name", target = "locationName")
    DeliveryDTO toDto(Delivery delivery); 

    @Mapping(target = "articles", ignore = true)
    @Mapping(target = "checklist", ignore = true)
    @Mapping(source = "workpackageId", target = "workpackage")
    @Mapping(source = "personId", target = "person")
    @Mapping(source = "locationId", target = "location")
    Delivery toEntity(DeliveryDTO deliveryDTO);

    default Delivery fromId(Long id) {
        if (id == null) {
            return null;
        }
        Delivery delivery = new Delivery();
        delivery.setId(id);
        return delivery;
    }
}
