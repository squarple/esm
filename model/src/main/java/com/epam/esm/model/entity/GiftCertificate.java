package com.epam.esm.model.entity;

import com.epam.esm.model.validation.marker.OnCreate;
import com.epam.esm.model.validation.marker.OnUpdate;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The type Gift certificate.
 */
public class GiftCertificate {
    @Null(groups = {OnCreate.class, OnUpdate.class}, message = "{cert.id.null}")
    private Long id;

    @NotNull(groups = OnCreate.class, message = "{cert.name.not.null}")
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "{cert.name.not.blank}")
    @Size(groups = {OnCreate.class, OnUpdate.class}, min = 1, max = 30, message = "{cert.name.size}")
    private String name;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "{cert.description.not.blank}")
    @Size(groups = {OnCreate.class, OnUpdate.class}, min = 1, max = 2000, message = "{cert.description.size}")
    private String description;

    @NotNull(groups = OnCreate.class, message = "{cert.price.not.null}")
    @DecimalMin(groups = {OnCreate.class, OnUpdate.class}, value = "0.0", message = "{cert.price.decimal.min}")
    @Digits(groups = {OnCreate.class, OnUpdate.class}, integer = 9, fraction = 2, message = "{cert.price.digits}")
    private BigDecimal price;

    @NotNull(groups = OnCreate.class, message = "{cert.duration.not.null}")
    @DecimalMin(groups = {OnCreate.class, OnUpdate.class}, value = "1", message = "{cert.duration.decimal.min}")
    @DecimalMax(groups = {OnCreate.class, OnUpdate.class}, value = "365", message = "{cert.duration.decimal.max}")
    private Integer duration;

    private LocalDateTime createDate;

    private LocalDateTime lastUpdateDate;

    @Valid
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
                tags.equals(that.getTags());
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

    /**
     * Builder instance.
     *
     * @return the builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * The Builder for GiftCertificate.
     */
    public static class Builder {
        private final GiftCertificate giftCertificate;

        private Builder() {
            giftCertificate = new GiftCertificate();
        }

        /**
         * Sets id.
         *
         * @param id the id
         * @return the id
         */
        public Builder setId(Long id) {
            giftCertificate.setId(id);
            return this;
        }

        /**
         * Sets name.
         *
         * @param name the name
         * @return the name
         */
        public Builder setName(String name) {
            giftCertificate.setName(name);
            return this;
        }

        /**
         * Sets description.
         *
         * @param description the description
         * @return the description
         */
        public Builder setDescription(String description) {
            giftCertificate.setDescription(description);
            return this;
        }

        /**
         * Sets price.
         *
         * @param price the price
         * @return the price
         */
        public Builder setPrice(BigDecimal price) {
            giftCertificate.setPrice(price);
            return this;
        }

        /**
         * Sets duration.
         *
         * @param duration the duration
         * @return the duration
         */
        public Builder setDuration(Integer duration) {
            giftCertificate.setDuration(duration);
            return this;
        }

        /**
         * Sets create date.
         *
         * @param createDate the create date
         * @return the create date
         */
        public Builder setCreateDate(LocalDateTime createDate) {
            giftCertificate.setCreateDate(createDate);
            return this;
        }

        /**
         * Sets last update date.
         *
         * @param lastUpdateDate the last update date
         * @return the last update date
         */
        public Builder setLastUpdateDate(LocalDateTime lastUpdateDate) {
            giftCertificate.setLastUpdateDate(lastUpdateDate);
            return this;
        }

        /**
         * Sets tags.
         *
         * @param tags the tags
         * @return the tags
         */
        public Builder setTags(List<Tag> tags) {
            giftCertificate.setTags(tags);
            return this;
        }

        /**
         * Build gift certificate.
         *
         * @return the gift certificate
         */
        public GiftCertificate build() {
            return giftCertificate;
        }
    }
}
