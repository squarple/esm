package com.epam.esm.service.impl;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.persistence.builder.cert.criteria.Criteria;
import com.epam.esm.persistence.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.persistence.exception.EntityNotFoundDaoException;
import com.epam.esm.service.config.TestServiceConfig;
import com.epam.esm.service.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Profile("test")
@ContextConfiguration(classes = {TestServiceConfig.class})
class GiftCertificateServiceImplTest {
    @Mock
    private GiftCertificateDaoImpl giftCertificateDao;
    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    @BeforeEach
    void setUpBeforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_Successful() {
        GiftCertificate cert = GiftCertificate.builder()
                .setName("name")
                .setDescription("descr")
                .setPrice(BigDecimal.valueOf(100L,2))
                .setDuration(10)
                .setCreateDate(LocalDateTime.of(2000,12,1,1,1,1))
                .setLastUpdateDate(LocalDateTime.of(2000,12,1,1,1,1))
                .setTags(new ArrayList<>())
                .build();
        GiftCertificate expectedCert = GiftCertificate.builder()
                .setId(1L)
                .setName("name")
                .setDescription("descr")
                .setPrice(BigDecimal.valueOf(100L,2))
                .setDuration(10)
                .setCreateDate(LocalDateTime.of(2000,12,1,1,1,1))
                .setLastUpdateDate(LocalDateTime.of(2000,12,1,1,1,1))
                .setTags(new ArrayList<>())
                .build();
        when(giftCertificateDao.create(cert)).thenReturn(expectedCert);
        GiftCertificate actualCertificate = giftCertificateService.save(cert);
        assertEquals(expectedCert, actualCertificate);
    }

    @Test
    void get_ExistedGiftCertificateId_ReturnCert() throws EntityNotFoundDaoException, EntityNotFoundException {
        GiftCertificate expectedCert = GiftCertificate.builder()
                .setId(1L)
                .setName("name")
                .setDescription("descr")
                .setPrice(BigDecimal.valueOf(100L,2))
                .setDuration(10)
                .setCreateDate(LocalDateTime.of(2000,12,1,1,1,1))
                .setLastUpdateDate(LocalDateTime.of(2000,12,1,1,1,1))
                .setTags(new ArrayList<>())
                .build();
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
    void  getWithCriteria_Successful() {
        Criteria criteria = new Criteria("n", "n", null, Criteria.SortField.DESCRIPTION, Criteria.Sort.ASC);
        when(giftCertificateDao.find(criteria)).thenReturn(new ArrayList<>());
        assertEquals(new ArrayList<GiftCertificate>(), giftCertificateService.get("n", "n", null, Criteria.SortField.DESCRIPTION.toString(), Criteria.Sort.ASC.toString()));
    }

    @Test
    void update_ReturnUpdatedGiftCertificate() throws EntityNotFoundDaoException, EntityNotFoundException {
        GiftCertificate expectedCert = GiftCertificate.builder()
                .setId(1L)
                .setName("name")
                .setDescription("descr")
                .setPrice(BigDecimal.valueOf(100L,2))
                .setDuration(10)
                .setCreateDate(LocalDateTime.of(2000,12,1,1,1,1))
                .setLastUpdateDate(LocalDateTime.of(2000,12,1,1,1,1))
                .setTags(new ArrayList<>())
                .build();
        when(giftCertificateDao.update(expectedCert)).thenReturn(expectedCert);
        GiftCertificate actualCert = giftCertificateService.update(expectedCert);
        assertEquals(expectedCert, actualCert);
    }

    @Test
    void update_NotExistedGiftCertificateId_ThrowEntityNotFoundException() throws EntityNotFoundDaoException {
        GiftCertificate expectedCert = GiftCertificate.builder()
                .setId(1L)
                .setName("name")
                .setDescription("descr")
                .setPrice(BigDecimal.valueOf(100L,2))
                .setDuration(10)
                .setCreateDate(LocalDateTime.of(2000,12,1,1,1,1))
                .setLastUpdateDate(LocalDateTime.of(2000,12,1,1,1,1))
                .setTags(new ArrayList<>())
                .build();
        when(giftCertificateDao.update(expectedCert))
                .thenThrow(EntityNotFoundDaoException.class);
        assertThrows(EntityNotFoundException.class, () -> giftCertificateService.update(expectedCert));
    }

    @Test
    void getAll_ReturnListOfGiftCertificate() {
        List<GiftCertificate> expectedCertificates = com.epam.esm.service.impl.TestUtil.getGiftCertificateList(3);
        when(giftCertificateDao.findAll())
                .thenReturn(expectedCertificates);
        List<GiftCertificate> actualCertificates = giftCertificateService.getAll();
        assertEquals(expectedCertificates, actualCertificates);
    }
}
