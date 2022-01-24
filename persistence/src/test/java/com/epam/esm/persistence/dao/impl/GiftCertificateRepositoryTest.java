package com.epam.esm.persistence.dao.impl;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.persistence.config.TestPersistenceConfig;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = {"test"})
@EntityScan("com.epam.esm")
@EnableJpaRepositories("com.epam.esm.persistence.repository")
@SpringBootTest(classes = TestPersistenceConfig.class)
@Transactional
class GiftCertificateRepositoryTest {
    @Autowired
    private GiftCertificateRepository giftCertificateRepository;

    @Test
    void findByNameContainingAndDescriptionContaining_ExistedNameAndDescription_ReturnGiftCertificate() {
        GiftCertificate expectedGiftCertificate = TestUtil.getGiftCertificateList(1).get(0);
        expectedGiftCertificate = giftCertificateRepository.save(expectedGiftCertificate);
        Pageable pageable = TestUtil.getPageable();
        Page<GiftCertificate> actualGiftCertificatePage =
                giftCertificateRepository.findByNameContainingAndDescriptionContaining(
                        expectedGiftCertificate.getName(), expectedGiftCertificate.getDescription(), pageable);
        assertEquals(expectedGiftCertificate, actualGiftCertificatePage.getContent().get(0));
        giftCertificateRepository.deleteById(expectedGiftCertificate.getId());
    }

    @Test
    void findByNameContainingAndDescriptionContaining_NonExistedNameAndDescription_ReturnNull() {
        Pageable pageable = TestUtil.getPageable();
        Page<GiftCertificate> GiftCertificatePage = giftCertificateRepository.findByNameContainingAndDescriptionContaining("name", "description", pageable);
        assertFalse(GiftCertificatePage.hasContent());
    }
}
