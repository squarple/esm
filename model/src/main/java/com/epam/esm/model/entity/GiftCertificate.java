package com.epam.esm.model.entity;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class GiftCertificate {
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
}
