package at.meroff.se.repository;

import at.meroff.se.domain.WorkPackage;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the WorkPackage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkPackageRepository extends JpaRepository<WorkPackage, Long>, JpaSpecificationExecutor<WorkPackage> {

}
