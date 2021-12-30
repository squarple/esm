package com.epam.esm.service.impl;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.pagination.Page;
import com.epam.esm.model.pagination.PageImpl;
import com.epam.esm.model.pagination.Pageable;
import com.epam.esm.persistence.dao.impl.TagDaoImpl;
import com.epam.esm.persistence.exception.EntityAlreadyExistsDaoException;
import com.epam.esm.persistence.exception.EntityNotFoundDaoException;
import com.epam.esm.service.config.TestServiceConfig;
import com.epam.esm.service.exception.EntityAlreadyExistsException;
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

@Profile("test")
@ExtendWith(MockitoExtension.class)
//@Import(TestServiceConfig.class)
@SpringBootTest(classes = TestServiceConfig.class)
class TagServiceImplTest {
    @Mock
    private TagDaoImpl tagDao;

    private TagServiceImpl tagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tagService = new TagServiceImpl(tagDao);
    }

    @Test
    void save_ReturnCreatedTag() throws EntityAlreadyExistsDaoException, EntityAlreadyExistsException {
        Tag expectedTag = TestUtil.getTagList("name").get(0);
        when(tagDao.create(expectedTag)).thenReturn(expectedTag);
        Tag actualTag = tagService.save(expectedTag);
        assertEquals(expectedTag, actualTag);
    }

    @Test
    void get_ExistedTagId_ReturnTag() throws EntityNotFoundDaoException, EntityNotFoundException {
        Tag expectedTag = TestUtil.getTagList("name").get(0);
        when(tagDao.find(expectedTag.getId())).thenReturn(expectedTag);
        Tag actualTag = tagService.get(expectedTag.getId());
        assertEquals(expectedTag, actualTag);
    }

    @Test
    void get_NonExistedTagId_ThrowRuntimeException() throws EntityNotFoundDaoException {
        when(tagDao.find(0L)).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> tagService.get(0L));
    }

    @Test
    void getAll_ReturnListOfTags() throws ResourceNotFoundException {
        Pageable pageable = TestUtil.getPageable();
        List<Tag> expectedTags = TestUtil.getTagList("name1", "name2");
        Page<Tag> page = new PageImpl<>(expectedTags, pageable, 2);
        when(tagDao.findAll(pageable)).thenReturn(page);
        Page<Tag> actualTags = tagService.getAll(pageable);
        assertEquals(page, actualTags);
    }

    @Test
    void getByName_ReturnListOfTags() throws ResourceNotFoundException {
        Pageable pageable = TestUtil.getPageable();
        List<Tag> expectedTags = TestUtil.getTagList("name1", "name2");
        Page<Tag> page = new PageImpl<>(expectedTags, pageable, 2);
        when(tagDao.findByName("n", pageable)).thenReturn(page);
        Page<Tag> actualTags = tagService.getByName("n", pageable);
        assertEquals(page, actualTags);
    }
}
