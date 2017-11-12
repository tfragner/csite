package at.meroff.se.repository;

import at.meroff.se.domain.Delivery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Set;


/**
 * Spring Data JPA repository for the Delivery entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long>, JpaSpecificationExecutor<Delivery> {

    Set<Delivery> findAllByWorkpackage_Constructionsite_Id(@Param("csiteId") Long csiteId);

}
