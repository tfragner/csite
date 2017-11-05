package at.meroff.se.service.mapper;

import at.meroff.se.domain.*;
import at.meroff.se.service.dto.PersonDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Person and its DTO PersonDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PersonMapper extends EntityMapper<PersonDTO, Person> {

    

    @Mapping(target = "deliveries", ignore = true)
    @Mapping(target = "constructionsites", ignore = true)
    @Mapping(target = "constsitecustomers", ignore = true)
    Person toEntity(PersonDTO personDTO);

    default Person fromId(Long id) {
        if (id == null) {
            return null;
        }
        Person person = new Person();
        person.setId(id);
        return person;
    }
}
