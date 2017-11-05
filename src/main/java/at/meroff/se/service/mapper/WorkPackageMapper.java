package at.meroff.se.service.mapper;

import at.meroff.se.domain.*;
import at.meroff.se.service.dto.WorkPackageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity WorkPackage and its DTO WorkPackageDTO.
 */
@Mapper(componentModel = "spring", uses = {ConstructionSiteMapper.class})
public interface WorkPackageMapper extends EntityMapper<WorkPackageDTO, WorkPackage> {

    @Mapping(source = "constructionsite.id", target = "constructionsiteId")
    @Mapping(source = "constructionsite.prjName", target = "constructionsitePrjName")
    WorkPackageDTO toDto(WorkPackage workPackage); 

    @Mapping(target = "deliveries", ignore = true)
    @Mapping(source = "constructionsiteId", target = "constructionsite")
    WorkPackage toEntity(WorkPackageDTO workPackageDTO);

    default WorkPackage fromId(Long id) {
        if (id == null) {
            return null;
        }
        WorkPackage workPackage = new WorkPackage();
        workPackage.setId(id);
        return workPackage;
    }
}
