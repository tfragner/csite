package at.meroff.se.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Checklist entity. This class is used in ChecklistResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /checklists?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ChecklistCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private BooleanFilter inTime;

    private BooleanFilter complete;

    private BooleanFilter unloadingOk;

    private BooleanFilter notDamaged;

    private StringFilter description;

    private LongFilter deliveryId;

    public ChecklistCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getInTime() {
        return inTime;
    }

    public void setInTime(BooleanFilter inTime) {
        this.inTime = inTime;
    }

    public BooleanFilter getComplete() {
        return complete;
    }

    public void setComplete(BooleanFilter complete) {
        this.complete = complete;
    }

    public BooleanFilter getUnloadingOk() {
        return unloadingOk;
    }

    public void setUnloadingOk(BooleanFilter unloadingOk) {
        this.unloadingOk = unloadingOk;
    }

    public BooleanFilter getNotDamaged() {
        return notDamaged;
    }

    public void setNotDamaged(BooleanFilter notDamaged) {
        this.notDamaged = notDamaged;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LongFilter getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(LongFilter deliveryId) {
        this.deliveryId = deliveryId;
    }

    @Override
    public String toString() {
        return "ChecklistCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (inTime != null ? "inTime=" + inTime + ", " : "") +
                (complete != null ? "complete=" + complete + ", " : "") +
                (unloadingOk != null ? "unloadingOk=" + unloadingOk + ", " : "") +
                (notDamaged != null ? "notDamaged=" + notDamaged + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (deliveryId != null ? "deliveryId=" + deliveryId + ", " : "") +
            "}";
    }

}
