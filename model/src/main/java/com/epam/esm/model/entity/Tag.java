package com.epam.esm.model.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Tag {
    private Long id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 1, max = 30, message = "Name should be between 1 and 30")
    private String name;

    public Tag(Long id, String name) {
        this.id = id;
        this.name = name;
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
}
