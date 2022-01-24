package com.epam.esm.web.controller;

import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.validation.marker.OnCreate;
import com.epam.esm.service.dto.validation.marker.OnUpdate;
import com.epam.esm.service.exception.EntityAlreadyExistsException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ForbiddenActionException;
import com.epam.esm.web.hateoas.assembler.GiftCertificateAssembler;
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

import java.util.List;

/**
 * The GiftCertificateController.
 */
@RestController
@RequestMapping("/api/certs")
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateAssembler assembler;

    /**
     * Instantiates a new GiftCertificateController.
     *
     * @param giftCertificateService the gift certificate service
     * @param giftCertificateAssembler gift certificate assembler
     */
    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService, GiftCertificateAssembler giftCertificateAssembler) {
        this.giftCertificateService = giftCertificateService;
        this.assembler = giftCertificateAssembler;
    }

    /**
     * Gets cert.
     *
     * @param id the id
     * @return the cert
     * @throws EntityNotFoundException      if entity not found
     * @throws EntityAlreadyExistsException if entity already exists
     */
    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<GiftCertificateDto>> getGiftCertificate(@PathVariable Long id)
            throws EntityNotFoundException, EntityAlreadyExistsException, ForbiddenActionException {
        GiftCertificateDto giftCertificateDto = giftCertificateService.find(id);
        EntityModel<GiftCertificateDto> model = assembler.assembleModel(giftCertificateDto, giftCertificateService.isPossibleToDelete(id));
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    /**
     * Gets certs.
     *
     * @param name      the username
     * @param desc      the desc
     * @param tagNames  the tag names
     * @param sortField the sort field
     * @param sort      the sort
     * @param page      the page
     * @param size      the size
     * @return the certs
     * @throws EntityAlreadyExistsException if entity already exists
     * @throws EntityNotFoundException      if entity not found
     */
    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<EntityModel<Page<GiftCertificateDto>>> getGiftCertificates(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "desc", required = false) String desc,
            @RequestParam(name = "tagName", required = false) List<String> tagNames,
            @RequestParam(name = "sortField", required = false) String sortField,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size
    ) throws EntityAlreadyExistsException, EntityNotFoundException, ForbiddenActionException {
        Pageable pageable = PageRequest.of(page, size);
        Page<GiftCertificateDto> giftCertificatesDto = giftCertificateService.find(name, desc, tagNames, sortField, sort, pageable);
        EntityModel<Page<GiftCertificateDto>> model = assembler.assemblePagedModel(giftCertificatesDto, giftCertificateService);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    /**
     * Create gift certificate.
     *
     * @param giftCertificateDto the gift certificate
     * @return the gift certificate
     * @throws EntityAlreadyExistsException if entity already exists
     * @throws EntityNotFoundException      if entity not found
     */
    @PreAuthorize("hasAuthority('SCOPE_certificate:write')")
    @PostMapping
    public ResponseEntity<EntityModel<GiftCertificateDto>> createGiftCertificate(
            @RequestBody @Validated(OnCreate.class) GiftCertificateDto giftCertificateDto
    ) throws EntityAlreadyExistsException, EntityNotFoundException, ForbiddenActionException {
        giftCertificateDto = giftCertificateService.save(giftCertificateDto);
        EntityModel<GiftCertificateDto> model = assembler.assembleModel(giftCertificateDto,
                giftCertificateService.isPossibleToDelete(giftCertificateDto.getId()));
        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    /**
     * Update gift certificate.
     *
     * @param id              the id
     * @param giftCertificateDto the gift certificate
     * @return the updated gift certificate
     * @throws EntityNotFoundException      if entity not found
     * @throws EntityAlreadyExistsException if entity already exists
     */
    @PreAuthorize("hasAuthority('SCOPE_certificate:write')")
    @PatchMapping("/{id}")
    public ResponseEntity<EntityModel<GiftCertificateDto>> patchGiftCertificate(
            @PathVariable Long id,
            @RequestBody @Validated(OnUpdate.class) GiftCertificateDto giftCertificateDto
    ) throws EntityNotFoundException, EntityAlreadyExistsException, ForbiddenActionException {
        giftCertificateDto.setId(id);
        giftCertificateDto = giftCertificateService.update(giftCertificateDto);
        EntityModel<GiftCertificateDto> model = assembler.assembleModel(giftCertificateDto,
                giftCertificateService.isPossibleToDelete(id));
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    /**
     * Delete gift certificate.
     *
     * @param id the id
     * @return the void response entity
     */
    @PreAuthorize("hasAuthority('SCOPE_certificate:write')")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteGiftCertificate(@PathVariable Long id)
            throws ForbiddenActionException, EntityNotFoundException {
        giftCertificateService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
