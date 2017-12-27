package at.meroff.se.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import at.meroff.se.domain.enumeration.WorkPackageStatus;

/**
 * A DTO for the WorkPackage entity.
 */
public class WorkPackageDTO implements Serializable {

    private Long id;

    private String name;

    private ZonedDateTime startDate;

    private ZonedDateTime endDate;

    private WorkPackageStatus status;

    private Integer duration;

    private Double progress;

    private Long constructionsiteId;

    private String constructionsitePrjName;

    private ZonedDateTime start_date;

    private String text;

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

    public WorkPackageStatus getStatus() {
        return status;
    }

    public void setStatus(WorkPackageStatus status) {
        this.status = status;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStart_date() {
        //2017-04-15 00:00
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
        return startDate.format(format);
    }

    public void setStart_date(String start_date) {
        return;
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
            ", status='" + getStatus() + "'" +
            ", duration='" + getDuration() + "'" +
            ", progress='" + getProgress() + "'" +
            "}";
    }
}
