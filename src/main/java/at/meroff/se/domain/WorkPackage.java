package at.meroff.se.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import at.meroff.se.domain.enumeration.WorkPackageStatus;

/**
 * A WorkPackage.
 */
@Entity
@Table(name = "work_package")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WorkPackage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

    @Column(name = "end_date")
    private ZonedDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private WorkPackageStatus status;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "progress")
    private Double progress;

    @OneToMany(mappedBy = "workpackage")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Delivery> deliveries = new HashSet<>();

    @ManyToOne
    private ConstructionSite constructionsite;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public WorkPackage name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public WorkPackage startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public WorkPackage endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public WorkPackageStatus getStatus() {
        return status;
    }

    public WorkPackage status(WorkPackageStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(WorkPackageStatus status) {
        this.status = status;
    }

    public Integer getDuration() {
        return duration;
    }

    public WorkPackage duration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getProgress() {
        return progress;
    }

    public WorkPackage progress(Double progress) {
        this.progress = progress;
        return this;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    public Set<Delivery> getDeliveries() {
        return deliveries;
    }

    public WorkPackage deliveries(Set<Delivery> deliveries) {
        this.deliveries = deliveries;
        return this;
    }

    public WorkPackage addDelivery(Delivery delivery) {
        this.deliveries.add(delivery);
        delivery.setWorkpackage(this);
        return this;
    }

    public WorkPackage removeDelivery(Delivery delivery) {
        this.deliveries.remove(delivery);
        delivery.setWorkpackage(null);
        return this;
    }

    public void setDeliveries(Set<Delivery> deliveries) {
        this.deliveries = deliveries;
    }

    public ConstructionSite getConstructionsite() {
        return constructionsite;
    }

    public WorkPackage constructionsite(ConstructionSite constructionSite) {
        this.constructionsite = constructionSite;
        return this;
    }

    public void setConstructionsite(ConstructionSite constructionSite) {
        this.constructionsite = constructionSite;
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
        WorkPackage workPackage = (WorkPackage) o;
        if (workPackage.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), workPackage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WorkPackage{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", duration='" + getDuration() + "'" +
            ", progress='" + getProgress() + "'" +
            "}";
    }
}
