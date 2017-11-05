package at.meroff.se.repository;

import at.meroff.se.domain.ConstructionSite;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ConstructionSite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConstructionSiteRepository extends JpaRepository<ConstructionSite, Long>, JpaSpecificationExecutor<ConstructionSite> {

}
