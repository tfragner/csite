package at.meroff.se.service.mapper;

import at.meroff.se.domain.*;
import at.meroff.se.service.dto.LocationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Location and its DTO LocationDTO.
 */
@Mapper(componentModel = "spring", uses = {ConstructionSiteMapper.class})
public interface LocationMapper extends EntityMapper<LocationDTO, Location> {

    @Mapping(source = "constructionSite.id", target = "constructionSiteId")
    @Mapping(source = "constructionSite.prjName", target = "constructionSitePrjName")
    LocationDTO toDto(Location location); 

    @Mapping(target = "deliveries", ignore = true)
    @Mapping(source = "constructionSiteId", target = "constructionSite")
    Location toEntity(LocationDTO locationDTO);

    default Location fromId(Long id) {
        if (id == null) {
            return null;
        }
        Location location = new Location();
        location.setId(id);
        return location;
    }
}
