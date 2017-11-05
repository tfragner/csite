package at.meroff.se.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Checklist entity.
 */
public class ChecklistDTO implements Serializable {

    private Long id;

    private Boolean inTime;

    private Boolean complete;

    private Boolean unloadingOk;

    private Boolean notDamaged;

    private String description;

    private Long deliveryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isInTime() {
        return inTime;
    }

    public void setInTime(Boolean inTime) {
        this.inTime = inTime;
    }

    public Boolean isComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public Boolean isUnloadingOk() {
        return unloadingOk;
    }

    public void setUnloadingOk(Boolean unloadingOk) {
        this.unloadingOk = unloadingOk;
    }

    public Boolean isNotDamaged() {
        return notDamaged;
    }

    public void setNotDamaged(Boolean notDamaged) {
        this.notDamaged = notDamaged;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ChecklistDTO checklistDTO = (ChecklistDTO) o;
        if(checklistDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), checklistDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ChecklistDTO{" +
            "id=" + getId() +
            ", inTime='" + isInTime() + "'" +
            ", complete='" + isComplete() + "'" +
            ", unloadingOk='" + isUnloadingOk() + "'" +
            ", notDamaged='" + isNotDamaged() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
