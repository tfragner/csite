package at.meroff.se.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the WorkPackage entity.
 */
public class WorkPackageDTO implements Serializable {

    private Long id;

    private String name;

    private ZonedDateTime startDate;

    private ZonedDateTime endDate;

    private Long constructionsiteId;

    private String constructionsitePrjName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public Long getConstructionsiteId() {
        return constructionsiteId;
    }

    public void setConstructionsiteId(Long constructionSiteId) {
        this.constructionsiteId = constructionSiteId;
    }

    public String getConstructionsitePrjName() {
        return constructionsitePrjName;
    }

    public void setConstructionsitePrjName(String constructionSitePrjName) {
        this.constructionsitePrjName = constructionSitePrjName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WorkPackageDTO workPackageDTO = (WorkPackageDTO) o;
        if(workPackageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), workPackageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WorkPackageDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}
