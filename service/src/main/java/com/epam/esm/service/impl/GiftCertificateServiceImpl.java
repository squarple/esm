package com.epam.esm.service.impl;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.repository.OrderRepository;
import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ForbiddenActionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository giftCertificateRepository;
    private final OrderRepository orderRepository;
    private final TagRepository tagRepository;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository, OrderRepository orderRepository, TagRepository tagRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.orderRepository = orderRepository;
        this.tagRepository = tagRepository;
    }

    @Transactional
    @Override
    public GiftCertificateDto save(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = giftCertificateDto.toGiftCertificate();
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        tagRepository.saveAll(giftCertificate.getTags());
        giftCertificate = giftCertificateRepository.save(giftCertificate);
        return GiftCertificateDto.fromGiftCertificate(giftCertificate);
    }

    @Override
    public GiftCertificateDto find(Long id) throws EntityNotFoundException {
        GiftCertificate giftCertificate = giftCertificateRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
        return GiftCertificateDto.fromGiftCertificate(giftCertificate);
    }

    @Override
    public Page<GiftCertificateDto> find(String name, String description, List<String> tagNames, String sortField, String sort, Pageable pageable) {
        if (sortField != null && isPropertyExists(sortField)) {
            pageable = PageRequest.of(pageable.getPageNumber(),
                    pageable.getPageSize(),
                    (sort == null ? Sort.Direction.ASC : (Sort.Direction.valueOf(sort.toUpperCase()))),
                    sortField);
        }
        Page<GiftCertificate> giftCertificatePage =
                giftCertificateRepository.findByNameContainingAndDescriptionContaining((name == null ? "" : name),
                        (description == null ? "" : description),
                        pageable);
        if (tagNames == null) {
            return giftCertificatePage.map(GiftCertificateDto::fromGiftCertificate);
        }
        List<GiftCertificateDto> giftCertificateDtoList = giftCertificatePage
                .filter(giftCertificate -> giftCertificate.getTags().stream()
                        .map(Tag::getName)
                        .collect(Collectors.toList())
                        .containsAll(tagNames))
                .map(GiftCertificateDto::fromGiftCertificate)
                .stream().collect(Collectors.toList());
        return new PageImpl<>(giftCertificateDtoList, giftCertificatePage.getPageable(), giftCertificatePage.getTotalElements());
    }

    private boolean isPropertyExists(String sortField) {
        List<String> properties = Stream.of("id", "name", "description", "price", "duration", "create_date", "last_update_date").collect(Collectors.toList());
        return properties.contains(sortField.toLowerCase());
    }

    @Transactional
    @Override
    public GiftCertificateDto update(GiftCertificateDto giftCertificateDto) throws EntityNotFoundException {
        GiftCertificate giftCertificate = giftCertificateRepository.findById(giftCertificateDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(giftCertificateDto.getId()));
        update(giftCertificate, giftCertificateDto);
        return GiftCertificateDto.fromGiftCertificate(giftCertificate);
    }

    @Override
    public void delete(Long id) throws ForbiddenActionException, EntityNotFoundException {
        if (isPossibleToDelete(id)) {
            try {
                giftCertificateRepository.deleteById(id);
            } catch (EmptyResultDataAccessException exception) {
                throw new EntityNotFoundException(id);
            }
        } else {
            throw new ForbiddenActionException(id);
        }
    }

    @Override
    public Page<GiftCertificateDto> getAll(Pageable pageable) {
        Page<GiftCertificate> giftCertificatePage = giftCertificateRepository.findAll(pageable);
        return giftCertificatePage.map(GiftCertificateDto::fromGiftCertificate);
    }

    @Override
    public boolean isPossibleToDelete(Long id) {
        return orderRepository.countByGiftCertificateId(id) == 0;
    }

    private void update(GiftCertificate giftCertificate, GiftCertificateDto giftCertificateDto) {
        if (giftCertificateDto.getName() != null && !giftCertificateDto.getName().isEmpty()) {
            giftCertificate.setName(giftCertificateDto.getName());
        }
        if (giftCertificateDto.getDescription() != null && !giftCertificateDto.getDescription().isEmpty()) {
            giftCertificate.setDescription(giftCertificateDto.getDescription());
        }
        if (giftCertificateDto.getPrice() != null && giftCertificateDto.getPrice().compareTo(BigDecimal.valueOf(0)) > 0) {
            giftCertificate.setPrice(giftCertificateDto.getPrice());
        }
        if (giftCertificateDto.getDuration() != null && giftCertificateDto.getDuration() > 0 && giftCertificateDto.getDuration() < 365) {
            giftCertificate.setDuration(giftCertificate.getDuration());
        }
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        if (giftCertificateDto.getTags() != null) {
            List<Tag> newTags = giftCertificateDto.toGiftCertificate().getTags();
            tagRepository.saveAll(newTags);
            giftCertificate.getTags()
                    .removeIf(tag -> newTags.stream()
                            .noneMatch(newTag -> newTag.equals(tag)));
            newTags.forEach(newTag -> {
                if (!giftCertificate.getTags().contains(newTag)) {
                    giftCertificate.getTags().add(newTag);
                }
            });
        }
    }
}
