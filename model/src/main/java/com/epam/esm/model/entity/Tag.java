package com.epam.esm.model.entity;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Tag {
    @DecimalMin(value = "1", message = "Id cannot be less than 1")
    private Long id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 1, max = 30, message = "Name should be between 1 and 30")
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Tag tag = (Tag) o;
        return getId().equals(tag.getId()) &&
                getName().equals(tag.getName());
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = result * 31 + name.hashCode();
        return result;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Tag tag;

        public Builder() {
            tag = new Tag();
        }

        public Builder setId(Long id) {
            tag.setId(id);
            return this;
        }

        public Builder setName(String name) {
            tag.setName(name);
            return this;
        }

        public Tag build() {
            return tag;
        }
    }
}
