package at.meroff.se.service.dto;

import java.io.Serializable;
import at.meroff.se.domain.enumeration.UnloadingType;
import at.meroff.se.domain.enumeration.AvisoType;
import at.meroff.se.domain.enumeration.DeliveryStatus;
import at.meroff.se.domain.enumeration.LKWType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;



import io.github.jhipster.service.filter.ZonedDateTimeFilter;


/**
 * Criteria class for the Delivery entity. This class is used in DeliveryResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /deliveries?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DeliveryCriteria implements Serializable {
    /**
     * Class for filtering UnloadingType
     */
    public static class UnloadingTypeFilter extends Filter<UnloadingType> {
    }

    /**
     * Class for filtering AvisoType
     */
    public static class AvisoTypeFilter extends Filter<AvisoType> {
    }

    /**
     * Class for filtering DeliveryStatus
     */
    public static class DeliveryStatusFilter extends Filter<DeliveryStatus> {
    }

    /**
     * Class for filtering LKWType
     */
    public static class LKWTypeFilter extends Filter<LKWType> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter orderNumber;

    private IntegerFilter kalenderwoche;

    private ZonedDateTimeFilter date;

    private UnloadingTypeFilter unloading;

    private AvisoTypeFilter avisoType;

    private DeliveryStatusFilter status;

    private LKWTypeFilter lkwType;

    private LongFilter checklistId;

    private LongFilter workpackageId;

    private LongFilter personId;

    private LongFilter locationId;

    public DeliveryCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(StringFilter orderNumber) {
        this.orderNumber = orderNumber;
    }

    public IntegerFilter getKalenderwoche() {
        return kalenderwoche;
    }

    public void setKalenderwoche(IntegerFilter kalenderwoche) {
        this.kalenderwoche = kalenderwoche;
    }

    public ZonedDateTimeFilter getDate() {
        return date;
    }

    public void setDate(ZonedDateTimeFilter date) {
        this.date = date;
    }

    public UnloadingTypeFilter getUnloading() {
        return unloading;
    }

    public void setUnloading(UnloadingTypeFilter unloading) {
        this.unloading = unloading;
    }

    public AvisoTypeFilter getAvisoType() {
        return avisoType;
    }

    public void setAvisoType(AvisoTypeFilter avisoType) {
        this.avisoType = avisoType;
    }

    public DeliveryStatusFilter getStatus() {
        return status;
    }

    public void setStatus(DeliveryStatusFilter status) {
        this.status = status;
    }

    public LKWTypeFilter getLkwType() {
        return lkwType;
    }

    public void setLkwType(LKWTypeFilter lkwType) {
        this.lkwType = lkwType;
    }

    public LongFilter getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(LongFilter checklistId) {
        this.checklistId = checklistId;
    }

    public LongFilter getWorkpackageId() {
        return workpackageId;
    }

    public void setWorkpackageId(LongFilter workpackageId) {
        this.workpackageId = workpackageId;
    }

    public LongFilter getPersonId() {
        return personId;
    }

    public void setPersonId(LongFilter personId) {
        this.personId = personId;
    }

    public LongFilter getLocationId() {
        return locationId;
    }

    public void setLocationId(LongFilter locationId) {
        this.locationId = locationId;
    }

    @Override
    public String toString() {
        return "DeliveryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (orderNumber != null ? "orderNumber=" + orderNumber + ", " : "") +
                (kalenderwoche != null ? "kalenderwoche=" + kalenderwoche + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (unloading != null ? "unloading=" + unloading + ", " : "") +
                (avisoType != null ? "avisoType=" + avisoType + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (lkwType != null ? "lkwType=" + lkwType + ", " : "") +
                (checklistId != null ? "checklistId=" + checklistId + ", " : "") +
                (workpackageId != null ? "workpackageId=" + workpackageId + ", " : "") +
                (personId != null ? "personId=" + personId + ", " : "") +
                (locationId != null ? "locationId=" + locationId + ", " : "") +
            "}";
    }

}
