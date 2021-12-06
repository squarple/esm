package test.epam.esm.persistence.dao.impl;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.persistence.dao.GiftCertificateDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static test.epam.esm.persistence.dao.impl.TestUtil.getGiftCertificateList;

class GiftCertificateDaoImplTest {
    @Autowired
    private GiftCertificateDao certDao;

    @Test
    void create_Successful() {
        GiftCertificate expectedCert = GiftCertificate.builder()
                .setName("name")
                .setDescription("descr")
                .setPrice(BigDecimal.valueOf(1L))
                .setDuration(10)
                .setCreateDate(LocalDateTime.now())
                .setLastUpdateDate(LocalDateTime.now())
                .build();
        certDao.create(expectedCert);
        Optional<GiftCertificate> actualCertOptional = certDao.find(expectedCert.getId());
        if (!actualCertOptional.isPresent()) {
            fail();
        }
        assertEquals(expectedCert, actualCertOptional.get());
        certDao.delete(actualCertOptional.get().getId());
    }

    @Test
    void find_ExistedCert_ReturnCert() {
        GiftCertificate expectedCert = GiftCertificate.builder()
                .setName("name")
                .setDescription("descr")
                .setPrice(BigDecimal.valueOf(1L))
                .setDuration(10)
                .setCreateDate(LocalDateTime.now())
                .setLastUpdateDate(LocalDateTime.now())
                .build();
        certDao.create(expectedCert);
        Optional<GiftCertificate> actualCertOptional = certDao.find(expectedCert.getId());
        if (!actualCertOptional.isPresent()) {
            fail();
        }
        assertEquals(expectedCert, actualCertOptional.get());
        certDao.delete(actualCertOptional.get().getId());
    }

    @Test
    void find_NonExistedGiftCertificate_ReturnOptionalEmpty() {
        Optional<GiftCertificate> actualCertOptional = certDao.find(0L);
        assertFalse(actualCertOptional.isPresent());
    }

    @Test
    void findAll_ReturnListOfGiftCertificates() {
        List<GiftCertificate> expectedCertList =
                getGiftCertificateList(3).stream()
                        .sorted(Comparator.comparing(GiftCertificate::getId))
                        .collect(Collectors.toList());
        expectedCertList.forEach(certDao::create);
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
    void update_Successful() {
        GiftCertificate initialCert = GiftCertificate.builder()
                .setName("name")
                .setDescription("descr")
                .setPrice(BigDecimal.valueOf(1L))
                .setDuration(10)
                .setCreateDate(LocalDateTime.now())
                .setLastUpdateDate(LocalDateTime.now())
                .build();
        GiftCertificate expectedCert = GiftCertificate.builder()
                .setId(initialCert.getId())
                .setName("mod name")
                .setDescription("descr")
                .setPrice(BigDecimal.valueOf(1L))
                .setDuration(10)
                .setCreateDate(LocalDateTime.now())
                .setLastUpdateDate(LocalDateTime.now())
                .build();
        certDao.update(expectedCert);
        Optional<GiftCertificate> actualCertOptional = certDao.find(expectedCert.getId());
        if (!actualCertOptional.isPresent()) {
            fail();
        }
        assertEquals(expectedCert, actualCertOptional.get());
        certDao.delete(expectedCert.getId());
    }

    @Test
    void  delete_Successful() {
        GiftCertificate cert = GiftCertificate.builder()
                .setName("name")
                .setDescription("descr")
                .setPrice(BigDecimal.valueOf(1L))
                .setDuration(10)
                .setCreateDate(LocalDateTime.now())
                .setLastUpdateDate(LocalDateTime.now())
                .build();
        certDao.create(cert);
        Optional<GiftCertificate> createdCert = certDao.find(cert.getId());
        if (!createdCert.isPresent()) {
            fail();
        }
        certDao.delete(createdCert.get().getId());
        Optional<GiftCertificate> actualCert = certDao.find(createdCert.get().getId());
        assertFalse(actualCert.isPresent());
    }
}
