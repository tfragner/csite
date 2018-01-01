package at.meroff.se.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

import at.meroff.se.domain.Checklist;
import at.meroff.se.domain.enumeration.UnloadingType;
import at.meroff.se.domain.enumeration.AvisoType;
import at.meroff.se.domain.enumeration.DeliveryStatus;
import at.meroff.se.domain.enumeration.LKWType;

/**
 * A DTO for the Delivery entity.
 */
public class DeliveryDTO implements Serializable {

    private Long id;

    private String orderNumber;

    private Integer kalenderwoche;

    private ZonedDateTime date;

    private UnloadingType unloading;

    private AvisoType avisoType;

    private DeliveryStatus status;

    @Lob
    private byte[] image;
    private String imageContentType;

    private LKWType lkwType;

    private Long workpackageId;

    private String workpackageName;

    private Long personId;

    private String personLastName;

    private Long locationId;

    private String locationName;

    private Checklist checklist;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getKalenderwoche() {
        return kalenderwoche;
    }

    public void setKalenderwoche(Integer kalenderwoche) {
        this.kalenderwoche = kalenderwoche;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public UnloadingType getUnloading() {
        return unloading;
    }

    public void setUnloading(UnloadingType unloading) {
        this.unloading = unloading;
    }

    public AvisoType getAvisoType() {
        return avisoType;
    }

    public void setAvisoType(AvisoType avisoType) {
        this.avisoType = avisoType;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public LKWType getLkwType() {
        return lkwType;
    }

    public void setLkwType(LKWType lkwType) {
        this.lkwType = lkwType;
    }

    public Long getWorkpackageId() {
        return workpackageId;
    }

    public void setWorkpackageId(Long workPackageId) {
        this.workpackageId = workPackageId;
    }

    public String getWorkpackageName() {
        return workpackageName;
    }

    public void setWorkpackageName(String workPackageName) {
        this.workpackageName = workPackageName;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getPersonLastName() {
        return personLastName;
    }

    public void setPersonLastName(String personLastName) {
        this.personLastName = personLastName;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Checklist getChecklist() {return checklist;}

    public void setChecklist(Checklist checklist) {this.checklist = checklist;}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DeliveryDTO deliveryDTO = (DeliveryDTO) o;
        if(deliveryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), deliveryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DeliveryDTO{" +
            "id=" + getId() +
            ", orderNumber='" + getOrderNumber() + "'" +
            ", kalenderwoche='" + getKalenderwoche() + "'" +
            ", date='" + getDate() + "'" +
            ", unloading='" + getUnloading() + "'" +
            ", avisoType='" + getAvisoType() + "'" +
            ", status='" + getStatus() + "'" +
            ", image='" + getImage() + "'" +
            ", lkwType='" + getLkwType() + "'" +
            "}";
    }
}
