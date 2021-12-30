package com.epam.esm.model.entity;

import com.epam.esm.model.validation.marker.OnCreate;
import com.epam.esm.model.validation.marker.OnUpdate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * The Tag entity.
 */
@Entity
@Table(name = "tags")
public class Tag extends RepresentationModel<Tag> {
    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(Tag.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Null(groups = {OnCreate.class, OnUpdate.class}, message = "{tag.id.null}")
    private Long id;

    @Column(name = "name")
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "{tag.name.not.blank}")
    @Size(groups = {OnCreate.class, OnUpdate.class}, min = 1, max = 30, message = "{tag.name.size}")
    private String name;

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
     * On pre persist action.
     */
    @PrePersist
    public void onPrePersist() {
        logger.info("{}: insert new tag", LocalDateTime.now());
    }

    /**
     * On pre update action.
     */
    @PreUpdate
    public void onPreUpdate() {
        logger.info("{}: update tag with id={}", LocalDateTime.now(), this.id);
    }

    /**
     * On pre remove action.
     */
    @PreRemove
    public void onPreRemove() {
        logger.info("{}: delete tag with id={}", LocalDateTime.now(), this.id);
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
}
