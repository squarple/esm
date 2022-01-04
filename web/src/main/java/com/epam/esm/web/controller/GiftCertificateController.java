package com.epam.esm.web.controller;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.pagination.Page;
import com.epam.esm.model.pagination.PageRequest;
import com.epam.esm.model.pagination.Pageable;
import com.epam.esm.model.validation.marker.OnCreate;
import com.epam.esm.model.validation.marker.OnUpdate;
import com.epam.esm.persistence.exception.ForbiddenActionException;
import com.epam.esm.service.GiftCertificateService;
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
 * The GiftCertificateController.
 */
@RestController
@RequestMapping("/api/certs")
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    /**
     * Instantiates a new GiftCertificateController.
     *
     * @param giftCertificateService the gift certificate service
     */
    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    /**
     * Gets cert.
     *
     * @param id the id
     * @return the cert
     * @throws EntityNotFoundException      if entity not found
     * @throws EntityAlreadyExistsException if entity already exists
     * @throws ForbiddenActionException     if forbidden action
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<GiftCertificate>> getGiftCertificate(@PathVariable Long id)
            throws EntityNotFoundException, EntityAlreadyExistsException, ForbiddenActionException {
        GiftCertificate giftCertificate = giftCertificateService.get(id);
        LinkUtil.setGiftCertificateLinks(giftCertificate, giftCertificateService.isPossibleToDelete(id));
        return new ResponseEntity<>(EntityModel.of(giftCertificate), HttpStatus.OK);
    }

    /**
     * Gets certs.
     *
     * @param name      the name
     * @param desc      the desc
     * @param tagNames  the tag names
     * @param sortField the sort field
     * @param sort      the sort
     * @param page      the page
     * @param size      the size
     * @return the certs
     * @throws ResourceNotFoundException    if resource not found
     * @throws EntityAlreadyExistsException if entity already exists
     * @throws EntityNotFoundException      if entity not found
     * @throws ForbiddenActionException     if forbidden action
     */
    @GetMapping
    public ResponseEntity<EntityModel<Page<GiftCertificate>>> getGiftCertificates(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "desc", required = false) String desc,
            @RequestParam(name = "tagName", required = false) List<String> tagNames,
            @RequestParam(name = "sortField", required = false) String sortField,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size
    ) throws ResourceNotFoundException, EntityAlreadyExistsException, EntityNotFoundException, ForbiddenActionException {
        Pageable pageable = PageRequest.of(page, size);
        Page<GiftCertificate> giftCertificates = giftCertificateService.get(name, desc, tagNames, sortField, sort, pageable);
        for (GiftCertificate giftCertificate : giftCertificates.getContent()) {
            LinkUtil.setGiftCertificateLinks(giftCertificate, giftCertificateService.isPossibleToDelete(giftCertificate.getId()));
        }

        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(GiftCertificateController.class).getGiftCertificates(null, null, null, null, null, giftCertificates.getNumber(), giftCertificates.getSize())).withSelfRel());
        links.add(linkTo(methodOn(GiftCertificateController.class).getGiftCertificates(null, null, null, null, null, 0, 10)).withRel("giftCertificates"));
        links.add(linkTo(methodOn(GiftCertificateController.class).getGiftCertificates(null, null, null, null, null, 0, giftCertificates.getSize())).withRel("first"));
        if (giftCertificates.getTotalPages() > 0) {
            links.add(linkTo(methodOn(GiftCertificateController.class).getGiftCertificates(null, null, null, null, null, giftCertificates.getTotalPages() - 1, giftCertificates.getSize())).withRel("last"));
        }
        if (giftCertificates.getNumber() + 1 < giftCertificates.getTotalPages()) {
            links.add(linkTo(methodOn(GiftCertificateController.class).getGiftCertificates(null, null, null, null, null, giftCertificates.getNumber() + 1, giftCertificates.getSize())).withRel("next"));
        }
        if (giftCertificates.getNumber() > 0) {
            links.add(linkTo(methodOn(GiftCertificateController.class).getGiftCertificates(null, null, null, null, null, giftCertificates.getNumber() - 1, giftCertificates.getSize())).withRel("previous"));
        }
        return new ResponseEntity<>(EntityModel.of(giftCertificates, links), HttpStatus.OK);
    }

    /**
     * Create gift certificate.
     *
     * @param giftCertificate the gift certificate
     * @return the gift certificate
     * @throws EntityAlreadyExistsException if entity already exists
     * @throws ForbiddenActionException     if forbidden action
     * @throws EntityNotFoundException      if entity not found
     */
    @PostMapping
    public ResponseEntity<EntityModel<GiftCertificate>> createGiftCertificate(
            @RequestBody @Validated(OnCreate.class) GiftCertificate giftCertificate
    ) throws EntityAlreadyExistsException, ForbiddenActionException, EntityNotFoundException {
        giftCertificate = giftCertificateService.save(giftCertificate);
        LinkUtil.setGiftCertificateLinks(giftCertificate, giftCertificateService.isPossibleToDelete(giftCertificate.getId()));
        return new ResponseEntity<>(EntityModel.of(giftCertificate), HttpStatus.CREATED);
    }

    /**
     * Update gift certificate.
     *
     * @param id              the id
     * @param giftCertificate the gift certificate
     * @return the updated gift certificate
     * @throws EntityNotFoundException      if entity not found
     * @throws EntityAlreadyExistsException if entity already exists
     * @throws ForbiddenActionException     if forbidden action
     */
    @PatchMapping("/{id}")
    public ResponseEntity<EntityModel<GiftCertificate>> patchGiftCertificate(
            @PathVariable Long id,
            @RequestBody @Validated(OnUpdate.class) GiftCertificate giftCertificate
    ) throws EntityNotFoundException, EntityAlreadyExistsException, ForbiddenActionException {
        giftCertificate.setId(id);
        giftCertificate = giftCertificateService.update(giftCertificate);
        LinkUtil.setGiftCertificateLinks(giftCertificate, giftCertificateService.isPossibleToDelete(id));
        return new ResponseEntity<>(EntityModel.of(giftCertificate), HttpStatus.OK);
    }

    /**
     * Delete gift certificate.
     *
     * @param id the id
     * @return the void response entity
     * @throws ForbiddenActionException if forbidden action
     * @throws EntityNotFoundException  if entity not found
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteGiftCertificate(@PathVariable Long id) throws ForbiddenActionException, EntityNotFoundException {
        giftCertificateService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
