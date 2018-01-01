package at.meroff.se.repository;

import at.meroff.se.domain.Location;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Set;


/**
 * Spring Data JPA repository for the Location entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocationRepository extends JpaRepository<Location, Long>, JpaSpecificationExecutor<Location> {

    Set<Location> findAllByConstructionSite_Id(@Param("csiteid") Long csiteId);

}
