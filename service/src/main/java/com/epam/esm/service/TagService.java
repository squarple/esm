package com.epam.esm.service;

import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.EntityAlreadyExistsException;
import com.epam.esm.service.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The interface TagService.
 */
public interface TagService {

    /**
     * Save tag.
     *
     * @param tagDto the tag
     * @return the tag
     * @throws EntityAlreadyExistsException if tag already exists
     */
    TagDto save(TagDto tagDto) throws EntityAlreadyExistsException;

    /**
     * Find tag.
     *
     * @param id the id
     * @return the tag
     * @throws EntityNotFoundException if entity not found
     */
    TagDto find(Long id) throws EntityNotFoundException;

    /**
     * Find all.
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<TagDto> findAll(Pageable pageable);

    /**
     * Delete.
     *
     * @param id the id
     */
    void delete(Long id) throws EntityNotFoundException;

    /**
     * Find by name page.
     *
     * @param name     the name
     * @param pageable the pageable
     * @return the page
     */
    Page<TagDto> findByName(String name, Pageable pageable);

    /**
     * Find most used tag of user with the highest cost of all orders tag dto.
     *
     * @return the tag
     */
    TagDto findMostUsedTagOfUserWithHighestCostOfAllOrders() throws EntityNotFoundException;
}
