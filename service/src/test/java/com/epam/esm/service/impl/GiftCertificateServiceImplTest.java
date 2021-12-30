package com.epam.esm.service.impl;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.pagination.Page;
import com.epam.esm.model.pagination.PageImpl;
import com.epam.esm.model.pagination.Pageable;
import com.epam.esm.persistence.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.persistence.exception.EntityNotFoundDaoException;
import com.epam.esm.service.config.TestServiceConfig;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
@Profile("test")
//@Import(TestServiceConfig.class)
//@EntityScan("com.epam.esm")
@SpringBootTest(classes = TestServiceConfig.class)
class GiftCertificateServiceImplTest {
    @Mock
    private GiftCertificateDaoImpl giftCertificateDao;
    private GiftCertificateServiceImpl giftCertificateService;

    @BeforeEach
    void setUpBeforeEach() {
        MockitoAnnotations.openMocks(this);
        giftCertificateService = new GiftCertificateServiceImpl(giftCertificateDao);
    }

    @Test
    void save_Successful() {
        GiftCertificate cert = TestUtil.getGiftCertificateList(1).get(0);
        GiftCertificate expectedCert = TestUtil.getGiftCertificateList(1).get(0);
        when(giftCertificateDao.create(cert)).thenReturn(expectedCert);
        GiftCertificate actualCertificate = giftCertificateService.save(cert);
        assertEquals(expectedCert, actualCertificate);
    }

    @Test
    void get_ExistedGiftCertificateId_ReturnCert() throws EntityNotFoundDaoException, EntityNotFoundException {
        GiftCertificate expectedCert = TestUtil.getGiftCertificateList(1).get(0);
        when(giftCertificateDao.find(expectedCert.getId()))
                .thenReturn(expectedCert);
        GiftCertificate actualCert = giftCertificateService.get(expectedCert.getId());
        assertEquals(expectedCert, actualCert);
    }

    @Test
    void get_NotExistedGiftCertificateId_ThrowEntityNotFoundException() throws EntityNotFoundDaoException {
        when(giftCertificateDao.find(0L))
                .thenThrow(EntityNotFoundDaoException.class);
        assertThrows(EntityNotFoundException.class, () -> giftCertificateService.get(0L));
    }

    @Test
    void update_ReturnUpdatedGiftCertificate() throws EntityNotFoundDaoException, EntityNotFoundException {
        GiftCertificate expectedCert = TestUtil.getGiftCertificateList(1).get(0);
        when(giftCertificateDao.update(expectedCert)).thenReturn(expectedCert);
        GiftCertificate actualCert = giftCertificateService.update(expectedCert);
        assertEquals(expectedCert, actualCert);
    }

    @Test
    void update_NotExistedGiftCertificateId_ThrowEntityNotFoundException() throws EntityNotFoundDaoException {
        GiftCertificate expectedCert = TestUtil.getGiftCertificateList(1).get(0);
        when(giftCertificateDao.update(expectedCert))
                .thenThrow(EntityNotFoundDaoException.class);
        assertThrows(EntityNotFoundException.class, () -> giftCertificateService.update(expectedCert));
    }

    @Test
    void getAll_ReturnListOfGiftCertificate() throws ResourceNotFoundException {
        Pageable pageable = TestUtil.getPageable();
        List<GiftCertificate> expectedCertificates = TestUtil.getGiftCertificateList(3);
        Page<GiftCertificate> page = new PageImpl<>(expectedCertificates, pageable, 3);
        when(giftCertificateDao.findAll(pageable))
                .thenReturn(page);
        Page<GiftCertificate> actualCertificates = giftCertificateService.getAll(pageable);
        assertEquals(page, actualCertificates);
    }
}
