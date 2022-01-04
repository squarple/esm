package com.epam.esm.web.controller;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.pagination.Page;
import com.epam.esm.model.pagination.PageImpl;
import com.epam.esm.model.pagination.Pageable;
import com.epam.esm.persistence.exception.ForbiddenActionException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.EntityAlreadyExistsException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ResourceNotFoundException;
import com.epam.esm.web.config.TestWebAppContextConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

//@ActiveProfiles("test")
@Profile("test")
@ExtendWith(MockitoExtension.class)
//@ContextConfiguration(classes = {TestWebAppContextConfig.class})
//@WebAppConfiguration
//@Import(TestWebAppContextConfig.class)
@SpringBootTest(classes = TestWebAppContextConfig.class)
class GiftCertificateControllerTests {
    @Mock
    private GiftCertificateService giftCertificateService;
    private GiftCertificateController giftCertificateController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        giftCertificateController = new GiftCertificateController(giftCertificateService);
    }

    @Test
    void getCert_ReturnCert() throws EntityNotFoundException, EntityAlreadyExistsException, ForbiddenActionException {
        GiftCertificate certificate = TestUtil.getGiftCertificateList(1).get(0);
        when(giftCertificateService.get(1L)).thenReturn(certificate);
        ResponseEntity<EntityModel<GiftCertificate>> expected = new ResponseEntity<>(EntityModel.of(certificate), HttpStatus.OK);
        ResponseEntity<EntityModel<GiftCertificate>> actual = giftCertificateController.getGiftCertificate(1L);
        assertEquals(expected.getBody().getContent(), actual.getBody().getContent());
    }

    @Test
    void getAllCerts_ReturnAllCerts() throws ResourceNotFoundException, EntityAlreadyExistsException, ForbiddenActionException, EntityNotFoundException {
        Pageable pageable = TestUtil.getPageable();
        List<GiftCertificate> certificates = TestUtil.getGiftCertificateList(3);
        Page<GiftCertificate> page = new PageImpl<>(certificates, pageable, 3);
        when(giftCertificateService.get(null, null, null, null, null, pageable)).thenReturn(page);
        ResponseEntity<EntityModel<Page<GiftCertificate>>> expected = new ResponseEntity<>(EntityModel.of(page), HttpStatus.OK);
        ResponseEntity<EntityModel<Page<GiftCertificate>>> actual = giftCertificateController.getGiftCertificates(null, null, null, null, null, 0, 10);
        assertEquals(expected.getBody().getContent(), actual.getBody().getContent());
    }

    @Test
    void getCertsWithCriteria_ReturnAllCerts() throws ResourceNotFoundException, EntityAlreadyExistsException, ForbiddenActionException, EntityNotFoundException {
        Pageable pageable = TestUtil.getPageable();
        List<GiftCertificate> certificates = TestUtil.getGiftCertificateList(3);
        Page<GiftCertificate> page = new PageImpl<>(certificates, pageable, 3);
        when(giftCertificateService.get("name", "descr", Stream.of("tagName").collect(Collectors.toList()), "name", "asc", pageable)).thenReturn(page);
        ResponseEntity<EntityModel<Page<GiftCertificate>>> expected = new ResponseEntity<>(EntityModel.of(page), HttpStatus.OK);
        ResponseEntity<EntityModel<Page<GiftCertificate>>> actual = giftCertificateController.getGiftCertificates("name", "descr", Stream.of("tagName").collect(Collectors.toList()), "name", "asc", 0, 10);
        assertEquals(expected.getBody().getContent(), actual.getBody().getContent());
    }

    @Test
    void createCert_ReturnCreatedCert() throws EntityAlreadyExistsException, ForbiddenActionException, EntityNotFoundException {
        GiftCertificate certificate = TestUtil.getGiftCertificateList(1).get(0);
        when(giftCertificateService.save(certificate)).thenReturn(certificate);
        ResponseEntity<EntityModel<GiftCertificate>> expected = new ResponseEntity<>(EntityModel.of(certificate), HttpStatus.CREATED);
        ResponseEntity<EntityModel<GiftCertificate>> actual = giftCertificateController.createGiftCertificate(certificate);
        assertEquals(expected.getBody().getContent(), actual.getBody().getContent());
    }

    @Test
    void patchCert() throws EntityNotFoundException, EntityAlreadyExistsException, ForbiddenActionException {
        GiftCertificate certificate = TestUtil.getGiftCertificateList(1).get(0);
        when(giftCertificateService.update(certificate)).thenReturn(certificate);
        ResponseEntity<EntityModel<GiftCertificate>> expected = new ResponseEntity<>(EntityModel.of(certificate), HttpStatus.OK);
        ResponseEntity<EntityModel<GiftCertificate>> actual = giftCertificateController.patchGiftCertificate(certificate.getId(), certificate);
        assertEquals(expected.getBody().getContent(), actual.getBody().getContent());
    }

    @Test
    void deleteCert() throws ForbiddenActionException, EntityNotFoundException {
        ResponseEntity<Void> expected = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        ResponseEntity<Void> actual = giftCertificateController.deleteGiftCertificate(1L);
        assertEquals(expected, actual);
    }
}
