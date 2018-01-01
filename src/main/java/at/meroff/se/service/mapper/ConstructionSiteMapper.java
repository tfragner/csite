package at.meroff.se.service.mapper;

import at.meroff.se.domain.*;
import at.meroff.se.service.dto.ConstructionSiteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ConstructionSite and its DTO ConstructionSiteDTO.
 */
@Mapper(componentModel = "spring", uses = {PersonMapper.class})
public interface ConstructionSiteMapper extends EntityMapper<ConstructionSiteDTO, ConstructionSite> {

    @Mapping(source = "container.id", target = "containerId")
    @Mapping(source = "container.lastName", target = "containerLastName")
    @Mapping(source = "container.firstName", target = "containerFirstName")
    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "customer.lastName", target = "customerLastName")
    @Mapping(source = "customer.firstName", target = "customerFirstName")

    ConstructionSiteDTO toDto(ConstructionSite constructionSite);

    @Mapping(target = "workpackages", ignore = true)
    @Mapping(target = "locations", ignore = true)
    @Mapping(source = "containerId", target = "container")
    @Mapping(source = "customerId", target = "customer")
    ConstructionSite toEntity(ConstructionSiteDTO constructionSiteDTO);

    default ConstructionSite fromId(Long id) {
        if (id == null) {
            return null;
        }
        ConstructionSite constructionSite = new ConstructionSite();
        constructionSite.setId(id);
        return constructionSite;
    }
}
