package com.epam.esm.web.controller;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.exception.EntityAlreadyExistsException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ForbiddenActionException;
import com.epam.esm.web.config.TestWebAppContextConfig;
import com.epam.esm.web.hateoas.assembler.GiftCertificateAssembler;
import org.hibernate.engine.spi.EntityEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@Profile("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = TestWebAppContextConfig.class)
class GiftCertificateControllerTests {
    @Mock
    private GiftCertificateService giftCertificateService;

    @Mock
    private GiftCertificateAssembler giftCertificateAssembler;

    private GiftCertificateController giftCertificateController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        giftCertificateController = new GiftCertificateController(giftCertificateService, giftCertificateAssembler);
    }

    @Test
    void getCert_ReturnCert() throws EntityNotFoundException, EntityAlreadyExistsException, ForbiddenActionException {
        GiftCertificateDto certificateDto = TestUtil.getGiftCertificateList(1).get(0);
        when(giftCertificateService.find(1L)).thenReturn(certificateDto);
        when(giftCertificateService.isPossibleToDelete(1L)).thenReturn(true);
        when(giftCertificateAssembler.assembleModel(certificateDto, true)).thenReturn(EntityModel.of(certificateDto));
        ResponseEntity<EntityModel<GiftCertificateDto>> expected = new ResponseEntity<>(EntityModel.of(certificateDto), HttpStatus.OK);
        ResponseEntity<EntityModel<GiftCertificateDto>> actual = giftCertificateController.getGiftCertificate(1L);
        assertEquals(expected.getBody().getContent(), actual.getBody().getContent());
    }

    @Test
    void getAllCerts_ReturnAllCerts() throws EntityAlreadyExistsException, ForbiddenActionException, EntityNotFoundException {
        Pageable pageable = TestUtil.getPageable();
        List<GiftCertificateDto> certificates = TestUtil.getGiftCertificateList(3);
        Page<GiftCertificateDto> page = new PageImpl<>(certificates, pageable, 3);
        when(giftCertificateService.find(null, null, null, null, null, pageable)).thenReturn(page);
        when(giftCertificateAssembler.assemblePagedModel(page, giftCertificateService)).thenReturn(EntityModel.of(page));
        ResponseEntity<EntityModel<Page<GiftCertificateDto>>> expected = new ResponseEntity<>(EntityModel.of(page), HttpStatus.OK);
        ResponseEntity<EntityModel<Page<GiftCertificateDto>>> actual = giftCertificateController.getGiftCertificates(null, null, null, null, null, 0, 10);
        assertEquals(expected.getBody().getContent(), actual.getBody().getContent());
    }

    @Test
    void getCertsWithCriteria_ReturnAllCerts() throws EntityAlreadyExistsException, ForbiddenActionException, EntityNotFoundException {
        Pageable pageable = TestUtil.getPageable();
        List<GiftCertificateDto> certificates = TestUtil.getGiftCertificateList(3);
        Page<GiftCertificateDto> page = new PageImpl<>(certificates, pageable, 3);
        when(giftCertificateService.find("username", "descr", Stream.of("tagName").collect(Collectors.toList()), "username", "asc", pageable)).thenReturn(page);
        when(giftCertificateAssembler.assemblePagedModel(page, giftCertificateService)).thenReturn(EntityModel.of(page));
        ResponseEntity<EntityModel<Page<GiftCertificateDto>>> expected = new ResponseEntity<>(EntityModel.of(page), HttpStatus.OK);
        ResponseEntity<EntityModel<Page<GiftCertificateDto>>> actual = giftCertificateController.getGiftCertificates("username", "descr", Stream.of("tagName").collect(Collectors.toList()), "username", "asc", 0, 10);
        assertEquals(expected.getBody().getContent(), actual.getBody().getContent());
    }

    @Test
    void createCert_ReturnCreatedCert() throws EntityAlreadyExistsException, ForbiddenActionException, EntityNotFoundException {
        GiftCertificateDto certificate = TestUtil.getGiftCertificateList(1).get(0);
        when(giftCertificateService.save(certificate)).thenReturn(certificate);
        when(giftCertificateService.isPossibleToDelete(certificate.getId())).thenReturn(true);
        when(giftCertificateAssembler.assembleModel(certificate, true)).thenReturn(EntityModel.of(certificate));
        ResponseEntity<EntityModel<GiftCertificateDto>> expected = new ResponseEntity<>(EntityModel.of(certificate), HttpStatus.CREATED);
        ResponseEntity<EntityModel<GiftCertificateDto>> actual = giftCertificateController.createGiftCertificate(certificate);
        assertEquals(expected.getBody().getContent(), actual.getBody().getContent());
    }

    @Test
    void patchCert() throws EntityNotFoundException, EntityAlreadyExistsException, ForbiddenActionException {
        GiftCertificateDto certificate = TestUtil.getGiftCertificateList(1).get(0);
        when(giftCertificateService.update(certificate)).thenReturn(certificate);
        when(giftCertificateService.isPossibleToDelete(certificate.getId())).thenReturn(true);
        when(giftCertificateAssembler.assembleModel(certificate, true)).thenReturn(EntityModel.of(certificate));
        ResponseEntity<EntityModel<GiftCertificateDto>> expected = new ResponseEntity<>(EntityModel.of(certificate), HttpStatus.OK);
        ResponseEntity<EntityModel<GiftCertificateDto>> actual = giftCertificateController.patchGiftCertificate(certificate.getId(), certificate);
        assertEquals(expected.getBody().getContent(), actual.getBody().getContent());
    }

    @Test
    void deleteCert() throws ForbiddenActionException, EntityNotFoundException {
        ResponseEntity<Void> expected = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        ResponseEntity<Void> actual = giftCertificateController.deleteGiftCertificate(1L);
        assertEquals(expected, actual);
    }
}
