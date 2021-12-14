package com.epam.esm.web.controller;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.web.config.TestWebAppContextConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestWebAppContextConfig.class})
@WebAppConfiguration
class GiftCertificateControllerTests {
    private GiftCertificateService giftCertificateService;
    private GiftCertificateController giftCertificateController;

    @BeforeEach
    void setUp() {
        giftCertificateService = mock(GiftCertificateService.class);
        giftCertificateController = new GiftCertificateController(giftCertificateService);
    }

    @Test
    void getCert_ReturnCert() throws EntityNotFoundException {
        GiftCertificate certificate = TestUtil.getGiftCertificateList(1).get(0);
        when(giftCertificateService.get(1L)).thenReturn(certificate);
        ResponseEntity<GiftCertificate> expected = new ResponseEntity<>(certificate, HttpStatus.OK);
        ResponseEntity<GiftCertificate> actual = giftCertificateController.getCert(1L);
        assertEquals(expected, actual);
    }

    @Test
    void getAllCerts_ReturnAllCerts() {
        List<GiftCertificate> certificates = TestUtil.getGiftCertificateList(3);
        when(giftCertificateService.get(null, null, null, null, null)).thenReturn(certificates);
        ResponseEntity<List<GiftCertificate>> expected = new ResponseEntity<>(certificates, HttpStatus.OK);
        ResponseEntity<List<GiftCertificate>> actual = giftCertificateController.getCerts(null, null, null, null, null);
        assertEquals(expected, actual);
    }

    @Test
    void getCertsWithCriteria_ReturnAllCerts() {
        List<GiftCertificate> certificates = TestUtil.getGiftCertificateList(3);
        when(giftCertificateService.get("name", "descr", "tagName", "name", "asc")).thenReturn(certificates);
        ResponseEntity<List<GiftCertificate>> expected = new ResponseEntity<>(certificates, HttpStatus.OK);
        ResponseEntity<List<GiftCertificate>> actual = giftCertificateController.getCerts("name", "descr", "tagName", "name", "asc");
        assertEquals(expected, actual);
    }

    @Test
    void createCert_ReturnCreatedCert() {
        GiftCertificate certificate = TestUtil.getGiftCertificateList(1).get(0);
        when(giftCertificateService.save(certificate)).thenReturn(certificate);
        ResponseEntity<GiftCertificate> expected = new ResponseEntity<>(certificate, HttpStatus.CREATED);
        ResponseEntity<GiftCertificate> actual = giftCertificateController.createGiftCertificate(certificate);
        assertEquals(expected, actual);
    }

    @Test
    void patchCert() throws EntityNotFoundException {
        GiftCertificate certificate = TestUtil.getGiftCertificateList(1).get(0);
        when(giftCertificateService.update(certificate)).thenReturn(certificate);
        ResponseEntity<GiftCertificate> expected = new ResponseEntity<>(certificate, HttpStatus.OK);
        ResponseEntity<GiftCertificate> actual = giftCertificateController.patchGiftCertificate(certificate.getId(), certificate);
        assertEquals(expected, actual);
    }

    @Test
    void deleteCert() {
        ResponseEntity<Void> expected = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        ResponseEntity<Void> actual = giftCertificateController.deleteGiftCertificate(1L);
        assertEquals(expected, actual);
    }
}
