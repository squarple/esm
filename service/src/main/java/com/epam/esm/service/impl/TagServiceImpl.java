package com.epam.esm.service.impl;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.EntityAlreadyExistsException;
import com.epam.esm.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Transactional
    @Override
    public TagDto save(TagDto tagDto) throws EntityAlreadyExistsException {
        if (tagRepository.existsByName(tagDto.getName())) {
            throw new EntityAlreadyExistsException(tagDto.getName());
        }
        Tag tag = tagRepository.save(tagDto.toTag());
        return TagDto.fromTag(tag);
    }

    @Override
    public TagDto find(Long id) throws EntityNotFoundException {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
        return TagDto.fromTag(tag);
    }

    @Override
    public Page<TagDto> findAll(Pageable pageable) {
        Page<Tag> tagPage = tagRepository.findAll(pageable);
        return tagPage.map(TagDto::fromTag);
    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
        try {
            tagRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new EntityNotFoundException(id);
        }
    }

    @Override
    public Page<TagDto> findByName(String name, Pageable pageable) {
        Page<Tag> tagPage = tagRepository.findByNameContaining(name, pageable);
        return tagPage.map(TagDto::fromTag);
    }

    @Override
    public TagDto findMostUsedTagOfUserWithHighestCostOfAllOrders() throws EntityNotFoundException {
        Tag tag = tagRepository.findMostUsedTagOfUserWithHighestCostOfAllOrders();
        if (tag == null) {
            throw new EntityNotFoundException(null);
        }
        return TagDto.fromTag(tag);
    }
}
