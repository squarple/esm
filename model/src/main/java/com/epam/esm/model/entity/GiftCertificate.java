package com.epam.esm.model.entity;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class GiftCertificate {
    @DecimalMin(value = "1", message = "Id cannot be less than 1")
    private Long id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 1, max = 30, message = "Name must be between 1 and 30 characters")
    private String name;

    @NotNull(message = "Description cannot be null")
    @Size(min = 1, max = 2000, message = "Description must be between 1 and 2000 characters")
    private String description;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0", message = "Duration must be equals or greater than zero")
    @Digits(integer = 9, fraction = 2, message = "Number of integral digits should be equals or less than two and number of fractional digits should be equals or less than two")
    private BigDecimal price;

    @NotNull(message = "Duration cannot be null")
    @DecimalMin(value = "1", message = "Duration must be greater than zero")
    private Integer duration;

    @NotNull(message = "Create date cannot be null")
    private LocalDateTime createDate;

    @NotNull(message = "Last update date cannot be null")
    private LocalDateTime lastUpdateDate;

    public GiftCertificate() {

    }

    public GiftCertificate(Long id, String name, String description, BigDecimal price, Integer duration, LocalDateTime createDate, LocalDateTime lastUpdateDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
    }

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
                getLastUpdateDate().equals(that.getLastUpdateDate());
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
        return result;
    }
}
