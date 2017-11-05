package at.meroff.se.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import at.meroff.se.domain.enumeration.PersonType;

/**
 * A Person.
 */
@Entity
@Table(name = "person")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private PersonType type;

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Delivery> deliveries = new HashSet<>();

    @OneToMany(mappedBy = "container")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ConstructionSite> constructionsites = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ConstructionSite> constsitecustomers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public Person lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Person firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public PersonType getType() {
        return type;
    }

    public Person type(PersonType type) {
        this.type = type;
        return this;
    }

    public void setType(PersonType type) {
        this.type = type;
    }

    public Set<Delivery> getDeliveries() {
        return deliveries;
    }

    public Person deliveries(Set<Delivery> deliveries) {
        this.deliveries = deliveries;
        return this;
    }

    public Person addDelivery(Delivery delivery) {
        this.deliveries.add(delivery);
        delivery.setPerson(this);
        return this;
    }

    public Person removeDelivery(Delivery delivery) {
        this.deliveries.remove(delivery);
        delivery.setPerson(null);
        return this;
    }

    public void setDeliveries(Set<Delivery> deliveries) {
        this.deliveries = deliveries;
    }

    public Set<ConstructionSite> getConstructionsites() {
        return constructionsites;
    }

    public Person constructionsites(Set<ConstructionSite> constructionSites) {
        this.constructionsites = constructionSites;
        return this;
    }

    public Person addConstructionsite(ConstructionSite constructionSite) {
        this.constructionsites.add(constructionSite);
        constructionSite.setContainer(this);
        return this;
    }

    public Person removeConstructionsite(ConstructionSite constructionSite) {
        this.constructionsites.remove(constructionSite);
        constructionSite.setContainer(null);
        return this;
    }

    public void setConstructionsites(Set<ConstructionSite> constructionSites) {
        this.constructionsites = constructionSites;
    }

    public Set<ConstructionSite> getConstsitecustomers() {
        return constsitecustomers;
    }

    public Person constsitecustomers(Set<ConstructionSite> constructionSites) {
        this.constsitecustomers = constructionSites;
        return this;
    }

    public Person addConstsitecustomer(ConstructionSite constructionSite) {
        this.constsitecustomers.add(constructionSite);
        constructionSite.setCustomer(this);
        return this;
    }

    public Person removeConstsitecustomer(ConstructionSite constructionSite) {
        this.constsitecustomers.remove(constructionSite);
        constructionSite.setCustomer(null);
        return this;
    }

    public void setConstsitecustomers(Set<ConstructionSite> constructionSites) {
        this.constsitecustomers = constructionSites;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        if (person.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), person.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", lastName='" + getLastName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
