package com.epam.esm.service.impl;

import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.repository.OrderRepository;
import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.service.config.TestServiceConfig;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@Profile("test")
@SpringBootTest(classes = TestServiceConfig.class)
class GiftCertificateServiceImplTest {
    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private TagRepository tagRepository;

    private GiftCertificateServiceImpl giftCertificateService;

    @BeforeEach
    void setUpBeforeEach() {
        MockitoAnnotations.openMocks(this);
        giftCertificateService = new GiftCertificateServiceImpl(giftCertificateRepository, orderRepository, tagRepository);
    }

    @Test
    void find_ExistedGiftCertificateId_ReturnCert() throws EntityNotFoundException {
        GiftCertificateDto expectedCert = TestUtil.getGiftCertificateDtoList(1).get(0);
        when(giftCertificateRepository.findById(expectedCert.getId()))
                .thenReturn(Optional.ofNullable(expectedCert.toGiftCertificate()));
        GiftCertificateDto actualCert = giftCertificateService.find(expectedCert.getId());
        assertEquals(expectedCert, actualCert);
    }

    @Test
    void update_ReturnUpdatedGiftCertificate() throws EntityNotFoundException {
        GiftCertificateDto expectedCert = TestUtil.getGiftCertificateDtoList(1).get(0);
        when(giftCertificateRepository.findById(expectedCert.getId())).thenReturn(Optional.ofNullable(expectedCert.toGiftCertificate()));
        GiftCertificateDto actualCert = giftCertificateService.update(expectedCert);
        assertEquals(expectedCert.getName(), actualCert.getName());
    }

    @Test
    void getAll_ReturnListOfGiftCertificate() {
        Pageable pageable = TestUtil.getPageable();
        List<GiftCertificateDto> expectedCertificates = TestUtil.getGiftCertificateDtoList(3);
        Page<GiftCertificateDto> page = new PageImpl<>(expectedCertificates, pageable, 3);
        when(giftCertificateRepository.findAll(pageable))
                .thenReturn(page.map(GiftCertificateDto::toGiftCertificate));
        Page<GiftCertificateDto> actualCertificates = giftCertificateService.getAll(pageable);
        assertEquals(page, actualCertificates);
    }
}
