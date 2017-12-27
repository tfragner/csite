package at.meroff.se.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import at.meroff.se.domain.enumeration.LKWType;

/**
 * A DTO for the ConstructionSite entity.
 */
public class ConstructionSiteDTO implements Serializable {

    private Long id;

    private Integer prjNumber;

    private String prjName;

    private Boolean kran;

    private Boolean stapler;

    private LKWType maxLKWType;

    private Long containerId;

    private String containerLastName;

    private String containerFirstName;

    private Long customerId;

    private String customerLastName;

    private String customerFirstName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPrjNumber() {
        return prjNumber;
    }

    public void setPrjNumber(Integer prjNumber) {
        this.prjNumber = prjNumber;
    }

    public String getPrjName() {
        return prjName;
    }

    public void setPrjName(String prjName) {
        this.prjName = prjName;
    }

    public Boolean isKran() {
        return kran;
    }

    public void setKran(Boolean kran) {
        this.kran = kran;
    }

    public Boolean isStapler() {
        return stapler;
    }

    public void setStapler(Boolean stapler) {
        this.stapler = stapler;
    }

    public LKWType getMaxLKWType() {
        return maxLKWType;
    }

    public void setMaxLKWType(LKWType maxLKWType) {
        this.maxLKWType = maxLKWType;
    }

    public Long getContainerId() {
        return containerId;
    }

    public void setContainerId(Long personId) {
        this.containerId = personId;
    }

    public String getContainerLastName() {
        return containerLastName;
    }

    public void setContainerLastName(String personLastName) {
        this.containerLastName = personLastName;
    }

    public String getContainerFirstName() {
        return containerFirstName;
    }

    public void setContainerFirstName(String personFirstName) {
        this.containerFirstName = personFirstName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long personId) {
        this.customerId = personId;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String personLastName) {
        this.customerLastName = personLastName;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String personFirstName) {
        this.customerFirstName = personFirstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConstructionSiteDTO constructionSiteDTO = (ConstructionSiteDTO) o;
        if(constructionSiteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), constructionSiteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConstructionSiteDTO{" +
            "id=" + getId() +
            ", prjNumber='" + getPrjNumber() + "'" +
            ", prjName='" + getPrjName() + "'" +
            ", kran='" + isKran() + "'" +
            ", stapler='" + isStapler() + "'" +
            ", maxLKWType='" + getMaxLKWType() + "'" +
            "}";
    }
}
