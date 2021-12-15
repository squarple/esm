package com.epam.esm.web.controller;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.validation.marker.OnCreate;
import com.epam.esm.model.validation.marker.OnUpdate;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The Gift certificate controller.
 */
@RestController
@RequestMapping("/api/certs")
public class GiftCertificateController {
    private final GiftCertificateService certService;

    /**
     * Instantiates a new Gift certificate controller.
     *
     * @param giftCertificateService the gift certificate service
     */
    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.certService = giftCertificateService;
    }

    /**
     * Gets cert.
     *
     * @param id the id
     * @return the cert
     * @throws EntityNotFoundException the entity not found exception
     */
    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificate> getCert(@PathVariable Long id) throws EntityNotFoundException {
        GiftCertificate giftCertificate = certService.get(id);
        return new ResponseEntity<>(giftCertificate, HttpStatus.OK);
    }

    /**
     * Gets certs.
     *
     * @param name      the name
     * @param desc      the desc
     * @param tagName   the tag name
     * @param sortField the sort field
     * @param sort      the sort
     * @return the certs
     */
    @GetMapping
    public ResponseEntity<List<GiftCertificate>> getCerts(@RequestParam(name = "name", required = false) String name,
                                                          @RequestParam(name = "desc", required = false) String desc,
                                                          @RequestParam(name = "tagName", required = false) String tagName,
                                                          @RequestParam(name = "sortField", required = false) String sortField,
                                                          @RequestParam(name = "sort", required = false) String sort) {
        List<GiftCertificate> giftCertificates = certService.get(name, desc, tagName, sortField, sort);
        return new ResponseEntity<>(giftCertificates, HttpStatus.OK);
    }

    /**
     * Create gift certificate response entity.
     *
     * @param giftCertificate the gift certificate
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<GiftCertificate> createGiftCertificate(@RequestBody @Validated(OnCreate.class) GiftCertificate giftCertificate) {
        giftCertificate = certService.save(giftCertificate);
        return new ResponseEntity<>(giftCertificate, HttpStatus.CREATED);
    }

    /**
     * Patch gift certificate response entity.
     *
     * @param id              the id
     * @param giftCertificate the gift certificate
     * @return the response entity
     * @throws EntityNotFoundException the entity not found exception
     */
    @PatchMapping("/{id}")
    public ResponseEntity<GiftCertificate> patchGiftCertificate(@PathVariable Long id, @RequestBody @Validated(OnUpdate.class) GiftCertificate giftCertificate) throws EntityNotFoundException {
        giftCertificate.setId(id);
        giftCertificate = certService.update(giftCertificate);
        return new ResponseEntity<>(giftCertificate, HttpStatus.OK);
    }

    /**
     * Delete gift certificate response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteGiftCertificate(@PathVariable Long id) {
        certService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
