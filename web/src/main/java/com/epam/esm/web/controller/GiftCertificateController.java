package com.epam.esm.web.controller;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.validation.marker.OnCreate;
import com.epam.esm.model.validation.marker.OnUpdate;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certs")
public class GiftCertificateController {
    private final GiftCertificateService certService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.certService = giftCertificateService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificate> getCert(@PathVariable Long id) throws EntityNotFoundException {
        GiftCertificate giftCertificate = certService.get(id);
        return new ResponseEntity<>(giftCertificate, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<GiftCertificate>> getCerts(@RequestParam(name = "name", required = false) String name,
                                                          @RequestParam(name = "desc", required = false) String desc,
                                                          @RequestParam(name = "tagName", required = false) String tagName,
                                                          @RequestParam(name = "sortField", required = false) String sortField,
                                                          @RequestParam(name = "sort", required = false) String sort) {
        List<GiftCertificate> giftCertificates = certService.get(name, desc, tagName, sortField, sort);
        return new ResponseEntity<>(giftCertificates, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GiftCertificate> createGiftCertificate(@RequestBody @Validated(OnCreate.class) GiftCertificate giftCertificate) {
        giftCertificate = certService.save(giftCertificate);
        return new ResponseEntity<>(giftCertificate, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GiftCertificate> patchGiftCertificate(@PathVariable Long id, @RequestBody @Validated(OnUpdate.class) GiftCertificate giftCertificate) throws EntityNotFoundException {
        giftCertificate.setId(id);
        giftCertificate = certService.update(giftCertificate);
        return new ResponseEntity<>(giftCertificate, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteGiftCertificate(@PathVariable Long id) {
        certService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
