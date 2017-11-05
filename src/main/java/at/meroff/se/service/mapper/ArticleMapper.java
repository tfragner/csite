package at.meroff.se.service.mapper;

import at.meroff.se.domain.*;
import at.meroff.se.service.dto.ArticleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Article and its DTO ArticleDTO.
 */
@Mapper(componentModel = "spring", uses = {DeliveryMapper.class})
public interface ArticleMapper extends EntityMapper<ArticleDTO, Article> {

    @Mapping(source = "delivery.id", target = "deliveryId")
    ArticleDTO toDto(Article article); 

    @Mapping(source = "deliveryId", target = "delivery")
    Article toEntity(ArticleDTO articleDTO);

    default Article fromId(Long id) {
        if (id == null) {
            return null;
        }
        Article article = new Article();
        article.setId(id);
        return article;
    }
}
