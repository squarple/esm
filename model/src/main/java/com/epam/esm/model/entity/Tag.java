package com.epam.esm.model.entity;

import com.epam.esm.model.validation.marker.OnCreate;
import com.epam.esm.model.validation.marker.OnUpdate;

import javax.validation.constraints.*;

public class Tag {
    @Null(groups = {OnCreate.class, OnUpdate.class}, message = "{tag.id.null}")
    private Long id;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "{tag.name.not.blank}")
    @Size(groups = {OnCreate.class, OnUpdate.class}, min = 1, max = 30, message = "{tag.name.size}")
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
