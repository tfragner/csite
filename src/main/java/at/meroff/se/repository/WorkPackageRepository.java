package at.meroff.se.repository;

import at.meroff.se.domain.WorkPackage;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Set;


/**
 * Spring Data JPA repository for the WorkPackage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkPackageRepository extends JpaRepository<WorkPackage, Long>, JpaSpecificationExecutor<WorkPackage> {

    Set<WorkPackage> findAllByConstructionsite_Id(@Param("csiteid") Long csiteId);

}
