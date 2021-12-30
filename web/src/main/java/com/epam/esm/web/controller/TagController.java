package com.epam.esm.web.controller;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.pagination.Page;
import com.epam.esm.model.pagination.PageRequest;
import com.epam.esm.model.pagination.Pageable;
import com.epam.esm.model.validation.marker.OnCreate;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.EntityAlreadyExistsException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ResourceNotFoundException;
import com.epam.esm.web.hateoas.LinkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The Tag controller.
 */
@RestController
@RequestMapping("/api/tags")
public class TagController {
    private final TagService tagService;

    /**
     * Instantiates a new TagController.
     *
     * @param tagService the tag service
     */
    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Create tag.
     *
     * @param tag the tag
     * @return the tag
     * @throws EntityAlreadyExistsException if entity already exists
     * @throws EntityNotFoundException      if entity not found
     */
    @PostMapping
    public ResponseEntity<EntityModel<Tag>> createTag(@Validated(OnCreate.class) @RequestBody Tag tag)
            throws EntityAlreadyExistsException, EntityNotFoundException {
        tag = tagService.save(tag);
        LinkUtil.setTagLinks(tag);
        return new ResponseEntity<>(EntityModel.of(tag), HttpStatus.CREATED);
    }

    /**
     * Gets tag.
     *
     * @param id the id
     * @return the tag
     * @throws EntityNotFoundException      if entity not found
     * @throws EntityAlreadyExistsException if entity already exists
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Tag>> getTag(@PathVariable Long id)
            throws EntityNotFoundException, EntityAlreadyExistsException {
        Tag tag = tagService.get(id);
        LinkUtil.setTagLinks(tag);
        return new ResponseEntity<>(EntityModel.of(tag), HttpStatus.OK);
    }

    /**
     * Gets tags.
     *
     * @param name the name
     * @param page the page
     * @param size the size
     * @return the tags
     * @throws ResourceNotFoundException    if resource not found
     * @throws EntityAlreadyExistsException if entity already exists
     * @throws EntityNotFoundException      if entity not found
     */
    @GetMapping
    public ResponseEntity<EntityModel<Page<Tag>>> getTags(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size
    ) throws ResourceNotFoundException, EntityAlreadyExistsException, EntityNotFoundException {
        Pageable pageable = PageRequest.of(page, size);
        Page<Tag> tagsPage;
        if (name == null || name.isEmpty()) {
            tagsPage = tagService.getAll(pageable);
        } else {
            tagsPage = tagService.getByName(name, pageable);
        }
        for (Tag tag : tagsPage.getContent()) {
            LinkUtil.setTagLinks(tag);
        }
        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(TagController.class).getTags(name, page, size)).withSelfRel());
        links.add(linkTo(methodOn(TagController.class).getTags("", 0, 10)).withRel("tags"));
        links.add(linkTo(methodOn(TagController.class).getTags(name, 0, size)).withRel("first"));
        if (tagsPage.getTotalPages() > 0) {
            links.add(linkTo(methodOn(TagController.class).getTags(name, tagsPage.getTotalPages() - 1, size)).withRel("last"));
        }
        if (tagsPage.getNumber() + 1 < tagsPage.getTotalPages()) {
            links.add(linkTo(methodOn(TagController.class).getTags(name, tagsPage.getNumber() + 1, size)).withRel("next"));
        }
        if (tagsPage.getNumber() > 0) {
            links.add(linkTo(methodOn(TagController.class).getTags(name, tagsPage.getNumber() - 1, size)).withRel("previous"));
        }
        return new ResponseEntity<>(EntityModel.of(tagsPage, links), HttpStatus.OK);
    }

    /**
     * Gets most used tag of user with the highest cost of all orders.
     *
     * @return the most used tag of user with the highest cost of all orders
     * @throws EntityAlreadyExistsException if entity already exists
     * @throws EntityNotFoundException      if entity not found
     */
    @GetMapping("/mostUsed")
    public ResponseEntity<Tag> getMostUsedTagOfUserWithHighestCostOfAllOrders()
            throws EntityAlreadyExistsException, EntityNotFoundException {
        Tag tag = tagService.getMostUsedTagOfUserWithHighestCostOfAllOrders();
        LinkUtil.setTagLinks(tag);
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    /**
     * Delete tag.
     *
     * @param id the id
     * @return the void
     * @throws EntityNotFoundException if entity not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) throws EntityNotFoundException {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
