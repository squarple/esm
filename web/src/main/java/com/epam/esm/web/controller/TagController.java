package com.epam.esm.web.controller;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tag createTag(@Valid @RequestBody Tag tag) {
        return tagService.save(tag);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTag(@PathVariable Long id) throws EntityNotFoundException {
        Tag tag = tagService.get(id);
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
