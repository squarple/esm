package com.epam.esm.persistence.dao.impl;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.pagination.Pageable;
import com.epam.esm.persistence.config.TestPersistenceConfig;
import com.epam.esm.persistence.dao.GiftCertificateDao;
import com.epam.esm.persistence.dao.criteria.cert.Criteria;
import com.epam.esm.persistence.exception.EntityNotFoundDaoException;
import com.epam.esm.persistence.exception.ForbiddenActionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.esm.persistence.dao.impl.TestUtil.getGiftCertificateList;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = {"test"})
//@Import(TestPersistenceConfig.class)
@EntityScan("com.epam.esm")
@SpringBootTest(classes = TestPersistenceConfig.class)
class GiftCertificateDaoImplTest {
    @Autowired
    private GiftCertificateDao certDao;

    @Test
    void create_Successful() throws ForbiddenActionException, EntityNotFoundDaoException {
        GiftCertificate giftCertificate = TestUtil.getGiftCertificateList(1).get(0);
        giftCertificate = certDao.create(giftCertificate);
        assertNotNull(giftCertificate);
        assertNotNull(giftCertificate.getId());
        certDao.delete(giftCertificate.getId());
    }

    @Test
    void find_ExistedCert_ReturnCert() throws EntityNotFoundDaoException, ForbiddenActionException {
        GiftCertificate expectedCert = TestUtil.getGiftCertificateList(1).get(0);
        certDao.create(expectedCert);
        GiftCertificate actualCert = certDao.find(expectedCert.getId());
        assertEquals(expectedCert.getId(), actualCert.getId());
        certDao.delete(actualCert.getId());
    }

    @Test
    void find_NonExistedGiftCertificate_ReturnOptionalEmpty() {
        assertThrows(EntityNotFoundDaoException.class, () -> certDao.find(0L));
    }

    @Test
    void findAll_ReturnListOfGiftCertificates() throws ForbiddenActionException, EntityNotFoundDaoException {
        Pageable pageable = TestUtil.getPageable();
        List<GiftCertificate> expectedCertList = getGiftCertificateList(3);
        expectedCertList.forEach(certDao::create);
        expectedCertList = expectedCertList.stream()
                .sorted(Comparator.comparing(GiftCertificate::getId))
                .collect(Collectors.toList());
        List<GiftCertificate> actualCertList = certDao.findAll(pageable).getContent().stream()
                .sorted(Comparator.comparing(GiftCertificate::getId))
                .collect(Collectors.toList());
        assertEquals(expectedCertList.stream().map(GiftCertificate::getId).collect(Collectors.toSet()),
                actualCertList.stream().map(GiftCertificate::getId).collect(Collectors.toSet()));
        for (GiftCertificate giftCertificate : expectedCertList) {
            certDao.delete(giftCertificate.getId());
        }
    }

    @Test
    void update_Successful() throws EntityNotFoundDaoException, ForbiddenActionException {
        GiftCertificate initialCert = TestUtil.getGiftCertificateList(1).get(0);
        certDao.create(initialCert);
        GiftCertificate expectedCert = TestUtil.getGiftCertificateList(1).get(0);
        expectedCert.setId(initialCert.getId());
        expectedCert.setName("modName");
        certDao.update(expectedCert);
        GiftCertificate actualCert = certDao.find(expectedCert.getId());
        assertEquals(expectedCert.getName(), actualCert.getName());
        certDao.delete(expectedCert.getId());
    }

    @Test
    void  delete_Successful() throws EntityNotFoundDaoException, ForbiddenActionException {
        GiftCertificate cert = TestUtil.getGiftCertificateList(1).get(0);
        certDao.create(cert);
        GiftCertificate createdCert = certDao.find(cert.getId());
        certDao.delete(createdCert.getId());
        assertThrows(EntityNotFoundDaoException.class, () -> certDao.find(createdCert.getId()));
    }

    @Test
    void findWithCriteria_Successful() throws ForbiddenActionException, EntityNotFoundDaoException {
        Pageable pageable = TestUtil.getPageable();
        GiftCertificate cert = TestUtil.getGiftCertificateList(1).get(0);
        certDao.create(cert);
        Criteria criteria = new Criteria("0", null, null, null, null);
        List<GiftCertificate> actualCerts = certDao.find(criteria, pageable).getContent();
        assertEquals(Stream.of(cert).collect(Collectors.toList()).stream().map(GiftCertificate::getId).collect(Collectors.toSet()),
                actualCerts.stream().map(GiftCertificate::getId).collect(Collectors.toSet()));
        certDao.delete(cert.getId());
    }
}
