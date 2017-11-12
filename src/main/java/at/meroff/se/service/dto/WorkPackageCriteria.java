package at.meroff.se.service.dto;

import java.io.Serializable;
import at.meroff.se.domain.enumeration.WorkPackageStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;



import io.github.jhipster.service.filter.ZonedDateTimeFilter;


/**
 * Criteria class for the WorkPackage entity. This class is used in WorkPackageResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /work-packages?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class WorkPackageCriteria implements Serializable {
    /**
     * Class for filtering WorkPackageStatus
     */
    public static class WorkPackageStatusFilter extends Filter<WorkPackageStatus> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter name;

    private ZonedDateTimeFilter startDate;

    private ZonedDateTimeFilter endDate;

    private WorkPackageStatusFilter status;

    private LongFilter constructionsiteId;

    public WorkPackageCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public ZonedDateTimeFilter getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTimeFilter startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTimeFilter getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTimeFilter endDate) {
        this.endDate = endDate;
    }

    public WorkPackageStatusFilter getStatus() {
        return status;
    }

    public void setStatus(WorkPackageStatusFilter status) {
        this.status = status;
    }

    public LongFilter getConstructionsiteId() {
        return constructionsiteId;
    }

    public void setConstructionsiteId(LongFilter constructionsiteId) {
        this.constructionsiteId = constructionsiteId;
    }

    @Override
    public String toString() {
        return "WorkPackageCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (constructionsiteId != null ? "constructionsiteId=" + constructionsiteId + ", " : "") +
            "}";
    }

}
