package com.epam.esm.model.entity;

import com.epam.esm.model.validation.marker.OnCreate;
import com.epam.esm.model.validation.marker.OnUpdate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The GiftCertificate entity.
 */
@Entity
@Table(name = "gift_certificates")
public class GiftCertificate extends RepresentationModel<GiftCertificate> {
    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(GiftCertificate.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Null(groups = {OnCreate.class, OnUpdate.class}, message = "{cert.id.null}")
    private Long id;

    @Column(name = "name")
    @NotNull(groups = OnCreate.class, message = "{cert.name.not.null}")
    @NotBlank(groups = {OnCreate.class}, message = "{cert.name.not.blank}")
    @Size(groups = {OnCreate.class, OnUpdate.class}, min = 1, max = 30, message = "{cert.name.size}")
    private String name;

    @Column(name = "description")
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "{cert.description.not.blank}")
    @Size(groups = {OnCreate.class, OnUpdate.class}, min = 1, max = 2000, message = "{cert.description.size}")
    private String description;

    @Column(name = "price")
    @NotNull(groups = OnCreate.class, message = "{cert.price.not.null}")
    @DecimalMin(groups = {OnCreate.class, OnUpdate.class}, value = "0.0", message = "{cert.price.decimal.min}")
    @Digits(groups = {OnCreate.class, OnUpdate.class}, integer = 9, fraction = 2, message = "{cert.price.digits}")
    private BigDecimal price;

    @Column(name = "duration")
    @NotNull(groups = OnCreate.class, message = "{cert.duration.not.null}")
    @DecimalMin(groups = {OnCreate.class, OnUpdate.class}, value = "1", message = "{cert.duration.decimal.min}")
    @DecimalMax(groups = {OnCreate.class, OnUpdate.class}, value = "365", message = "{cert.duration.decimal.max}")
    private Integer duration;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "gift_certificate_has_tag",
            joinColumns = @JoinColumn(name = "gift_certificate_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    private List<Tag> tags;

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Gets duration.
     *
     * @return the duration
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * Sets duration.
     *
     * @param duration the duration
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * Gets create date.
     *
     * @return the create date
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Sets create date.
     *
     * @param createDate the create date
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets last update date.
     *
     * @return the last update date
     */
    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * Sets last update date.
     *
     * @param lastUpdateDate the last update date
     */
    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    /**
     * Gets tags.
     *
     * @return the tags
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * Sets tags.
     *
     * @param tags the tags
     */
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    /**
     * On pre persist action.
     */
    @PrePersist
    public void onPrePersist() {
        logger.info("{}: insert new gift certificate", LocalDateTime.now());
    }

    /**
     * On pre update action.
     */
    @PreUpdate
    public void onPreUpdate() {
        logger.info("{}: update gift certificate with id={}", LocalDateTime.now(), this.id);
    }

    /**
     * On pre remove action.
     */
    @PreRemove
    public void onPreRemove() {
        logger.info("{}: delete gift certificate with id={}", LocalDateTime.now(), this.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        GiftCertificate that = (GiftCertificate) o;
        return getId().equals(that.getId()) &&
                getName().equals(that.getName()) &&
                getDescription().equals(that.getDescription()) &&
                getPrice().equals(that.getPrice()) &&
                getDuration().equals(that.getDuration()) &&
                getCreateDate().equals(that.getCreateDate()) &&
                getLastUpdateDate().equals(that.getLastUpdateDate()) &&
                getTags().equals(that.getTags());
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = result * 31 + name.hashCode();
        result = result * 31 + description.hashCode();
        result = result * 31 + price.hashCode();
        result = result * 31 + duration.hashCode();
        result = result * 31 + createDate.hashCode();
        result = result * 31 + lastUpdateDate.hashCode();
        result = result * 31 + tags.hashCode();
        return result;
    }
}
