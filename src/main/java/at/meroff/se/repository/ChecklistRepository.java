package at.meroff.se.repository;

import at.meroff.se.domain.Checklist;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Checklist entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChecklistRepository extends JpaRepository<Checklist, Long>, JpaSpecificationExecutor<Checklist> {

}
