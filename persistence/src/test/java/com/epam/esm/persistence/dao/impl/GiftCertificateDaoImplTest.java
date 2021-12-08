package com.epam.esm.persistence.dao.impl;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.persistence.builder.cert.criteria.SelectCriteria;
import com.epam.esm.persistence.config.TestPersistenceConfig;
import com.epam.esm.persistence.dao.GiftCertificateDao;
import com.epam.esm.persistence.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static com.epam.esm.persistence.dao.impl.TestUtil.getGiftCertificateList;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = {"test"})
@ContextConfiguration(classes = {TestPersistenceConfig.class})
class GiftCertificateDaoImplTest {
    @Autowired
    private GiftCertificateDao certDao;

    @Test
    void create_Successful() throws EntityNotFoundException {
        GiftCertificate expectedCert = GiftCertificate.builder()
                .setName("name")
                .setDescription("descr")
                .setPrice(BigDecimal.valueOf(100L,2))
                .setDuration(10)
                .setCreateDate(LocalDateTime.of(2000,12,1,1,1,1))
                .setLastUpdateDate(LocalDateTime.of(2000,12,1,1,1,1))
                .setTags(new ArrayList<>())
                .build();
        certDao.create(expectedCert);
        GiftCertificate actualCert = certDao.find(expectedCert.getId());
        assertEquals(expectedCert, actualCert);
        certDao.delete(actualCert.getId());
    }

    @Test
    void find_ExistedCert_ReturnCert() throws EntityNotFoundException {
        GiftCertificate expectedCert = GiftCertificate.builder()
                .setName("name")
                .setDescription("descr")
                .setPrice(BigDecimal.valueOf(100L,2))
                .setDuration(10)
                .setCreateDate(LocalDateTime.of(2000,12,1,1,1,1))
                .setLastUpdateDate(LocalDateTime.of(2000,12,1,1,1,1))
                .setTags(new ArrayList<>())
                .build();
        certDao.create(expectedCert);
        GiftCertificate actualCert = certDao.find(expectedCert.getId());
        assertEquals(expectedCert, actualCert);
        certDao.delete(actualCert.getId());
    }

    @Test
    void find_NonExistedGiftCertificate_ReturnOptionalEmpty() {
        assertThrows(EntityNotFoundException.class, () -> certDao.find(0L));
    }

    @Test
    void findAll_ReturnListOfGiftCertificates() {
        List<GiftCertificate> expectedCertList = getGiftCertificateList(3);
        expectedCertList.forEach(certDao::create);
        expectedCertList = expectedCertList.stream()
                        .sorted(Comparator.comparing(GiftCertificate::getId))
                        .collect(Collectors.toList());
        List<GiftCertificate> actualCertList =
                certDao.findAll().stream()
                        .sorted(Comparator.comparing(GiftCertificate::getId))
                        .collect(Collectors.toList());
        assertEquals(expectedCertList, actualCertList);
        expectedCertList.stream()
                .map(GiftCertificate::getId)
                .forEach(certDao::delete);
    }

    @Test
    void update_Successful() throws EntityNotFoundException {
        GiftCertificate initialCert = GiftCertificate.builder()
                .setName("name")
                .setDescription("descr")
                .setPrice(BigDecimal.valueOf(100L,2))
                .setDuration(10)
                .setCreateDate(LocalDateTime.of(2000,12,1,1,1,1))
                .setLastUpdateDate(LocalDateTime.of(2000,12,1,1,1,1))
                .setTags(new ArrayList<>())
                .build();
        certDao.create(initialCert);
        GiftCertificate expectedCert = GiftCertificate.builder()
                .setId(initialCert.getId())
                .setName("mod name")
                .setDescription("descr")
                .setPrice(BigDecimal.valueOf(100L,2))
                .setDuration(10)
                .setCreateDate(LocalDateTime.of(2000,12,1,1,1,1))
                .setLastUpdateDate(LocalDateTime.of(2000,12,1,1,1,1))
                .setTags(new ArrayList<>())
                .build();
        certDao.update(expectedCert);
        GiftCertificate actualCert = certDao.find(expectedCert.getId());
        assertEquals(expectedCert, actualCert);
        certDao.delete(expectedCert.getId());
    }

    @Test
    void  delete_Successful() throws EntityNotFoundException {
        GiftCertificate cert = GiftCertificate.builder()
                .setName("name")
                .setDescription("descr")
                .setPrice(BigDecimal.valueOf(100L,2))
                .setDuration(10)
                .setCreateDate(LocalDateTime.of(2000,12,1,1,1,1))
                .setLastUpdateDate(LocalDateTime.of(2000,12,1,1,1,1))
                .setTags(new ArrayList<>())
                .build();
        certDao.create(cert);
        GiftCertificate createdCert = certDao.find(cert.getId());
        certDao.delete(createdCert.getId());
        assertThrows(EntityNotFoundException.class, () -> certDao.find(createdCert.getId()));
    }

    @Test
    void findWithCriteria_Successful() {
        GiftCertificate cert = GiftCertificate.builder()
                .setName("name")
                .setDescription("descr")
                .setPrice(BigDecimal.valueOf(100L,2))
                .setDuration(10)
                .setCreateDate(LocalDateTime.of(2000,12,1,1,1,1))
                .setLastUpdateDate(LocalDateTime.of(2000,12,1,1,1,1))
                .setTags(new ArrayList<>())
                .build();
        certDao.create(cert);
        SelectCriteria criteria = new SelectCriteria("n", "d", null, null, null);
        List<GiftCertificate> actualCerts = certDao.find(criteria);
        assertEquals(Stream.of(cert).collect(Collectors.toList()), actualCerts);
        certDao.delete(cert.getId());
    }
}
