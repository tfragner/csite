package at.meroff.se.service.dto;

import java.io.Serializable;
import at.meroff.se.domain.enumeration.PersonType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Person entity. This class is used in PersonResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /people?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PersonCriteria implements Serializable {
    /**
     * Class for filtering PersonType
     */
    public static class PersonTypeFilter extends Filter<PersonType> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter lastName;

    private StringFilter firstName;

    private PersonTypeFilter type;

    public PersonCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public PersonTypeFilter getType() {
        return type;
    }

    public void setType(PersonTypeFilter type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PersonCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (lastName != null ? "lastName=" + lastName + ", " : "") +
                (firstName != null ? "firstName=" + firstName + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
            "}";
    }

}
