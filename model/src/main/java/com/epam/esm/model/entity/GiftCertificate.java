package com.epam.esm.model.entity;

import com.epam.esm.model.validation.marker.OnCreate;
import com.epam.esm.model.validation.marker.OnUpdate;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public List<Tag> getTags() {
        return tags;
    }

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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final GiftCertificate giftCertificate;

        public Builder() {
            giftCertificate = new GiftCertificate();
        }

        public Builder setId(Long id) {
            giftCertificate.setId(id);
            return this;
        }

        public Builder setName(String name) {
            giftCertificate.setName(name);
            return this;
        }

        public Builder setDescription(String description) {
            giftCertificate.setDescription(description);
            return this;
        }

        public Builder setPrice(BigDecimal price) {
            giftCertificate.setPrice(price);
            return this;
        }

        public Builder setDuration(Integer duration) {
            giftCertificate.setDuration(duration);
            return this;
        }

        public Builder setCreateDate(LocalDateTime createDate) {
            giftCertificate.setCreateDate(createDate);
            return this;
        }

        public Builder setLastUpdateDate(LocalDateTime lastUpdateDate) {
            giftCertificate.setLastUpdateDate(lastUpdateDate);
            return this;
        }

        public Builder setTags(List<Tag> tags) {
            giftCertificate.setTags(tags);
            return this;
        }

        public GiftCertificate build() {
            return giftCertificate;
        }
    }
}
