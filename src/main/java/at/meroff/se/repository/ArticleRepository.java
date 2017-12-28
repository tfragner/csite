package at.meroff.se.repository;

import at.meroff.se.domain.Article;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Set;


/**
 * Spring Data JPA repository for the Article entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {

    Set<Article> findAllByDelivery_Id(@Param("deliveryId") Long deliveryId);
}
