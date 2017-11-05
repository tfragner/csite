package at.meroff.se.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Checklist.
 */
@Entity
@Table(name = "checklist")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Checklist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "in_time")
    private Boolean inTime;

    @Column(name = "jhi_complete")
    private Boolean complete;

    @Column(name = "unloading_ok")
    private Boolean unloadingOk;

    @Column(name = "not_damaged")
    private Boolean notDamaged;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(unique = true)
    private Delivery delivery;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isInTime() {
        return inTime;
    }

    public Checklist inTime(Boolean inTime) {
        this.inTime = inTime;
        return this;
    }

    public void setInTime(Boolean inTime) {
        this.inTime = inTime;
    }

    public Boolean isComplete() {
        return complete;
    }

    public Checklist complete(Boolean complete) {
        this.complete = complete;
        return this;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public Boolean isUnloadingOk() {
        return unloadingOk;
    }

    public Checklist unloadingOk(Boolean unloadingOk) {
        this.unloadingOk = unloadingOk;
        return this;
    }

    public void setUnloadingOk(Boolean unloadingOk) {
        this.unloadingOk = unloadingOk;
    }

    public Boolean isNotDamaged() {
        return notDamaged;
    }

    public Checklist notDamaged(Boolean notDamaged) {
        this.notDamaged = notDamaged;
        return this;
    }

    public void setNotDamaged(Boolean notDamaged) {
        this.notDamaged = notDamaged;
    }

    public String getDescription() {
        return description;
    }

    public Checklist description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public Checklist delivery(Delivery delivery) {
        this.delivery = delivery;
        return this;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
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
        Checklist checklist = (Checklist) o;
        if (checklist.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), checklist.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Checklist{" +
            "id=" + getId() +
            ", inTime='" + isInTime() + "'" +
            ", complete='" + isComplete() + "'" +
            ", unloadingOk='" + isUnloadingOk() + "'" +
            ", notDamaged='" + isNotDamaged() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
