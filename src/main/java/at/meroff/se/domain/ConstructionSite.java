package at.meroff.se.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import at.meroff.se.domain.enumeration.LKWType;

/**
 * A ConstructionSite.
 */
@Entity
@Table(name = "construction_site")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ConstructionSite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prj_number")
    private Integer prjNumber;

    @Column(name = "prj_name")
    private String prjName;

    @Column(name = "kran")
    private Boolean kran;

    @Column(name = "stapler")
    private Boolean stapler;

    @Enumerated(EnumType.STRING)
    @Column(name = "max_lkw_type")
    private LKWType maxLKWType;

    @OneToMany(mappedBy = "constructionsite")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<WorkPackage> workpackages = new HashSet<>();

    @OneToMany(mappedBy = "constructionSite")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Location> locations = new HashSet<>();

    @ManyToOne
    private Person container;

    @ManyToOne
    private Person customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPrjNumber() {
        return prjNumber;
    }

    public ConstructionSite prjNumber(Integer prjNumber) {
        this.prjNumber = prjNumber;
        return this;
    }

    public void setPrjNumber(Integer prjNumber) {
        this.prjNumber = prjNumber;
    }

    public String getPrjName() {
        return prjName;
    }

    public ConstructionSite prjName(String prjName) {
        this.prjName = prjName;
        return this;
    }

    public void setPrjName(String prjName) {
        this.prjName = prjName;
    }

    public Boolean isKran() {
        return kran;
    }

    public ConstructionSite kran(Boolean kran) {
        this.kran = kran;
        return this;
    }

    public void setKran(Boolean kran) {
        this.kran = kran;
    }

    public Boolean isStapler() {
        return stapler;
    }

    public ConstructionSite stapler(Boolean stapler) {
        this.stapler = stapler;
        return this;
    }

    public void setStapler(Boolean stapler) {
        this.stapler = stapler;
    }

    public LKWType getMaxLKWType() {
        return maxLKWType;
    }

    public ConstructionSite maxLKWType(LKWType maxLKWType) {
        this.maxLKWType = maxLKWType;
        return this;
    }

    public void setMaxLKWType(LKWType maxLKWType) {
        this.maxLKWType = maxLKWType;
    }

    public Set<WorkPackage> getWorkpackages() {
        return workpackages;
    }

    public ConstructionSite workpackages(Set<WorkPackage> workPackages) {
        this.workpackages = workPackages;
        return this;
    }

    public ConstructionSite addWorkpackage(WorkPackage workPackage) {
        this.workpackages.add(workPackage);
        workPackage.setConstructionsite(this);
        return this;
    }

    public ConstructionSite removeWorkpackage(WorkPackage workPackage) {
        this.workpackages.remove(workPackage);
        workPackage.setConstructionsite(null);
        return this;
    }

    public void setWorkpackages(Set<WorkPackage> workPackages) {
        this.workpackages = workPackages;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public ConstructionSite locations(Set<Location> locations) {
        this.locations = locations;
        return this;
    }

    public ConstructionSite addLocation(Location location) {
        this.locations.add(location);
        location.setConstructionSite(this);
        return this;
    }

    public ConstructionSite removeLocation(Location location) {
        this.locations.remove(location);
        location.setConstructionSite(null);
        return this;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }

    public Person getContainer() {
        return container;
    }

    public ConstructionSite container(Person person) {
        this.container = person;
        return this;
    }

    public void setContainer(Person person) {
        this.container = person;
    }

    public Person getCustomer() {
        return customer;
    }

    public ConstructionSite customer(Person person) {
        this.customer = person;
        return this;
    }

    public void setCustomer(Person person) {
        this.customer = person;
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
        ConstructionSite constructionSite = (ConstructionSite) o;
        if (constructionSite.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), constructionSite.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConstructionSite{" +
            "id=" + getId() +
            ", prjNumber='" + getPrjNumber() + "'" +
            ", prjName='" + getPrjName() + "'" +
            ", kran='" + isKran() + "'" +
            ", stapler='" + isStapler() + "'" +
            ", maxLKWType='" + getMaxLKWType() + "'" +
            "}";
    }
}
