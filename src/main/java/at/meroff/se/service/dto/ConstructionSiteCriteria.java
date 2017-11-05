package at.meroff.se.service.dto;

import java.io.Serializable;
import at.meroff.se.domain.enumeration.LKWType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the ConstructionSite entity. This class is used in ConstructionSiteResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /construction-sites?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ConstructionSiteCriteria implements Serializable {
    /**
     * Class for filtering LKWType
     */
    public static class LKWTypeFilter extends Filter<LKWType> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private IntegerFilter prjNumber;

    private StringFilter prjName;

    private BooleanFilter kran;

    private BooleanFilter stapler;

    private LKWTypeFilter maxLKWType;

    private LongFilter containerId;

    private LongFilter customerId;

    public ConstructionSiteCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getPrjNumber() {
        return prjNumber;
    }

    public void setPrjNumber(IntegerFilter prjNumber) {
        this.prjNumber = prjNumber;
    }

    public StringFilter getPrjName() {
        return prjName;
    }

    public void setPrjName(StringFilter prjName) {
        this.prjName = prjName;
    }

    public BooleanFilter getKran() {
        return kran;
    }

    public void setKran(BooleanFilter kran) {
        this.kran = kran;
    }

    public BooleanFilter getStapler() {
        return stapler;
    }

    public void setStapler(BooleanFilter stapler) {
        this.stapler = stapler;
    }

    public LKWTypeFilter getMaxLKWType() {
        return maxLKWType;
    }

    public void setMaxLKWType(LKWTypeFilter maxLKWType) {
        this.maxLKWType = maxLKWType;
    }

    public LongFilter getContainerId() {
        return containerId;
    }

    public void setContainerId(LongFilter containerId) {
        this.containerId = containerId;
    }

    public LongFilter getCustomerId() {
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "ConstructionSiteCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (prjNumber != null ? "prjNumber=" + prjNumber + ", " : "") +
                (prjName != null ? "prjName=" + prjName + ", " : "") +
                (kran != null ? "kran=" + kran + ", " : "") +
                (stapler != null ? "stapler=" + stapler + ", " : "") +
                (maxLKWType != null ? "maxLKWType=" + maxLKWType + ", " : "") +
                (containerId != null ? "containerId=" + containerId + ", " : "") +
                (customerId != null ? "customerId=" + customerId + ", " : "") +
            "}";
    }

}
