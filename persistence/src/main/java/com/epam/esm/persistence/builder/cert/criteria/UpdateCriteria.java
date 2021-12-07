package com.epam.esm.persistence.builder.cert.criteria;

public class UpdateCriteria {
    private final boolean name;
    private final boolean description;
    private final boolean price;
    private final boolean duration;
    private final boolean createDate;
    private final boolean lastUpdateDate;

    public UpdateCriteria(boolean name, boolean description, boolean price, boolean duration, boolean createDate, boolean lastUpdateDate) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
    }

    public boolean isName() {
        return name;
    }

    public boolean isDescription() {
        return description;
    }

    public boolean isPrice() {
        return price;
    }

    public boolean isDuration() {
        return duration;
    }

    public boolean isCreateDate() {
        return createDate;
    }

    public boolean isLastUpdateDate() {
        return lastUpdateDate;
    }
}
