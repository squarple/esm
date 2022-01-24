package com.epam.esm.web.controller;

import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.EntityAlreadyExistsException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.web.hateoas.assembler.TagModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * The Tag controller.
 */
@RestController
@RequestMapping("/api/tags")
public class TagController {
    private final TagService tagService;
    private final TagModelAssembler assembler;

    /**
     * Instantiates a new TagController.
     *
     * @param tagService the tag service
     * @param tagModelAssembler tag model assembler
     */
    @Autowired
    public TagController(TagService tagService, TagModelAssembler tagModelAssembler) {
        this.tagService = tagService;
        this.assembler = tagModelAssembler;
    }

    /**
     * Create tag.
     *
     * @param tagDto the tag
     * @return the tag
     * @throws EntityAlreadyExistsException if entity already exists
     * @throws EntityNotFoundException      if entity not found
     */
    @PreAuthorize("hasAuthority('SCOPE_tag:write')")
    @PostMapping
    public ResponseEntity<EntityModel<TagDto>> createTag(@Validated @RequestBody TagDto tagDto)
            throws EntityAlreadyExistsException, EntityNotFoundException {
        tagDto = tagService.save(tagDto);
        EntityModel<TagDto> model = assembler.assembleModel(tagDto);
        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    /**
     * Gets tag.
     *
     * @param id the id
     * @return the tag
     * @throws EntityNotFoundException      if entity not found
     * @throws EntityAlreadyExistsException if entity already exists
     */
    @PreAuthorize("hasAuthority('SCOPE_tag:read')")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<TagDto>> getTag(@PathVariable Long id)
            throws EntityNotFoundException, EntityAlreadyExistsException {
        TagDto tagDto = tagService.find(id);
        EntityModel<TagDto> model = assembler.assembleModel(tagDto);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    /**
     * Gets tags.
     *
     * @param name the username
     * @param page the page
     * @param size the size
     * @return the tags
     * @throws EntityAlreadyExistsException if entity already exists
     * @throws EntityNotFoundException      if entity not found
     */
    @PreAuthorize("hasAuthority('SCOPE_tag:read')")
    @GetMapping
    public ResponseEntity<EntityModel<Page<TagDto>>> getTags(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size
    ) throws EntityAlreadyExistsException, EntityNotFoundException {
        Pageable pageable = PageRequest.of(page, size);
        Page<TagDto> tagsDtoPage;
        if (name == null || name.isEmpty()) {
            tagsDtoPage = tagService.findAll(pageable);
        } else {
            tagsDtoPage = tagService.findByName(name, pageable);
        }
        EntityModel<Page<TagDto>> model = assembler.assemblePagedModel(tagsDtoPage, name);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    /**
     * Gets most used tag of user with the highest cost of all orders.
     *
     * @return the most used tag of user with the highest cost of all orders
     * @throws EntityAlreadyExistsException if entity already exists
     * @throws EntityNotFoundException      if entity not found
     */
    @PreAuthorize("hasAuthority('SCOPE_tag:read')")
    @GetMapping("/mostUsed")
    public ResponseEntity<EntityModel<TagDto>> getMostUsedTagOfUserWithHighestCostOfAllOrders()
            throws EntityAlreadyExistsException, EntityNotFoundException {
        TagDto tagDto = tagService.findMostUsedTagOfUserWithHighestCostOfAllOrders();
        EntityModel<TagDto> model = assembler.assembleModel(tagDto);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    /**
     * Delete tag.
     *
     * @param id the id
     * @return the void
     * @throws EntityNotFoundException if entity not found
     */
    @PreAuthorize("hasAuthority('SCOPE_tag:write')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) throws EntityNotFoundException {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
