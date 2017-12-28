package at.meroff.se.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import at.meroff.se.domain.enumeration.UnloadingType;

import at.meroff.se.domain.enumeration.AvisoType;

import at.meroff.se.domain.enumeration.DeliveryStatus;

import at.meroff.se.domain.enumeration.LKWType;

/**
 * A Delivery.
 */
@Entity
@Table(name = "delivery")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Delivery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "kalenderwoche")
    private Integer kalenderwoche;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(name = "unloading")
    private UnloadingType unloading;

    @Enumerated(EnumType.STRING)
    @Column(name = "aviso_type")
    private AvisoType avisoType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DeliveryStatus status;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "lkw_type")
    private LKWType lkwType;

    @OneToMany(mappedBy = "delivery")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Article> articles = new HashSet<>();

    @OneToOne(mappedBy = "delivery")
    private Checklist checklist;

    @ManyToOne
    private WorkPackage workpackage;

    @ManyToOne
    private Person person;

    @ManyToOne
    private Location location;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public Delivery orderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getKalenderwoche() {
        return kalenderwoche;
    }

    public Delivery kalenderwoche(Integer kalenderwoche) {
        this.kalenderwoche = kalenderwoche;
        return this;
    }

    public void setKalenderwoche(Integer kalenderwoche) {
        this.kalenderwoche = kalenderwoche;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Delivery date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public UnloadingType getUnloading() {
        return unloading;
    }

    public Delivery unloading(UnloadingType unloading) {
        this.unloading = unloading;
        return this;
    }

    public void setUnloading(UnloadingType unloading) {
        this.unloading = unloading;
    }

    public AvisoType getAvisoType() {
        return avisoType;
    }

    public Delivery avisoType(AvisoType avisoType) {
        this.avisoType = avisoType;
        return this;
    }

    public void setAvisoType(AvisoType avisoType) {
        this.avisoType = avisoType;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public Delivery status(DeliveryStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }

    public byte[] getImage() {
        return image;
    }

    public Delivery image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Delivery imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public LKWType getLkwType() {
        return lkwType;
    }

    public Delivery lkwType(LKWType lkwType) {
        this.lkwType = lkwType;
        return this;
    }

    public void setLkwType(LKWType lkwType) {
        this.lkwType = lkwType;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public Delivery articles(Set<Article> articles) {
        this.articles = articles;
        return this;
    }

    public Delivery addArticle(Article article) {
        this.articles.add(article);
        article.setDelivery(this);
        return this;
    }

    public Delivery removeArticle(Article article) {
        this.articles.remove(article);
        article.setDelivery(null);
        return this;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public Checklist getChecklist() {
        return checklist;
    }

    public Delivery checklist(Checklist checklist) {
        this.checklist = checklist;
        return this;
    }

    public void setChecklist(Checklist checklist) {
        this.checklist = checklist;
    }

    public WorkPackage getWorkpackage() {
        return workpackage;
    }

    public Delivery workpackage(WorkPackage workPackage) {
        this.workpackage = workPackage;
        return this;
    }

    public void setWorkpackage(WorkPackage workPackage) {
        this.workpackage = workPackage;
    }

    public Person getPerson() {
        return person;
    }

    public Delivery person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Location getLocation() {
        return location;
    }

    public Delivery location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
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
        Delivery delivery = (Delivery) o;
        if (delivery.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), delivery.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Delivery{" +
            "id=" + getId() +
            ", orderNumber='" + getOrderNumber() + "'" +
            ", kalenderwoche='" + getKalenderwoche() + "'" +
            ", date='" + getDate() + "'" +
            ", unloading='" + getUnloading() + "'" +
            ", avisoType='" + getAvisoType() + "'" +
            ", status='" + getStatus() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + imageContentType + "'" +
            ", lkwType='" + getLkwType() + "'" +
            "}";
    }
}
