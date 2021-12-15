package com.epam.esm.web.controller;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.validation.marker.OnCreate;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The Tag controller.
 */
@RestController
@RequestMapping("/api/tags")
public class TagController {
    private final TagService tagService;

    /**
     * Instantiates a new Tag controller.
     *
     * @param tagService the tag service
     */
    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Create tag response entity.
     *
     * @param tag the tag
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<Tag> createTag(@Validated(OnCreate.class) @RequestBody Tag tag) {
        tag = tagService.save(tag);
        return new ResponseEntity<>(tag, HttpStatus.CREATED);
    }

    /**
     * Gets tag.
     *
     * @param id the id
     * @return the tag
     * @throws EntityNotFoundException the entity not found exception
     */
    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTag(@PathVariable Long id) throws EntityNotFoundException {
        Tag tag = tagService.get(id);
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    /**
     * Gets tags.
     *
     * @param name the name
     * @return the tags
     */
    @GetMapping
    public ResponseEntity<List<Tag>> getTags(@RequestParam(name = "name", required = false) String name) {
        List<Tag> tags;
        if (name == null) {
            tags = tagService.getAll();
        } else {
            tags = tagService.getByName(name);
        }
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    /**
     * Delete tag response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
