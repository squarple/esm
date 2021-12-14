package com.epam.esm.service.impl;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.persistence.dao.impl.TagDaoImpl;
import com.epam.esm.persistence.exception.EntityNotFoundDaoException;
import com.epam.esm.service.config.TestServiceConfig;
import com.epam.esm.service.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestServiceConfig.class})
class TagServiceImplTest {
    @Mock
    private TagDaoImpl tagDao;
    @InjectMocks
    private TagServiceImpl tagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_ReturnCreatedTag() {
        Tag expectedTag = Tag.builder().setId(1L).setName("name").build();
        when(tagDao.create(expectedTag)).thenReturn(expectedTag);
        Tag actualTag = tagService.save(expectedTag);
        assertEquals(expectedTag, actualTag);
    }

    @Test
    void get_ExistedTagId_ReturnTag() throws EntityNotFoundDaoException, EntityNotFoundException {
        Tag expectedTag = Tag.builder().setId(1L).setName("name").build();
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
    void getAll_ReturnListOfTags() {
        List<Tag> expectedTags = TestUtil.getTagList("name1", "name2");
        when(tagDao.findAll()).thenReturn(expectedTags);
        List<Tag> actualTags = tagService.getAll();
        assertEquals(expectedTags, actualTags);
    }

    @Test
    void getByName_ReturnListOfTags() {
        List<Tag> expectedTags = TestUtil.getTagList("name1", "name2");
        when(tagDao.findByName("n")).thenReturn(expectedTags);
        List<Tag> actualTags = tagService.getByName("n");
        assertEquals(expectedTags, actualTags);
    }

    @Test
    void getByCertId_ReturnListOfTags() {
        List<Tag> expectedTags = TestUtil.getTagList("name1", "name2");
        when(tagDao.findByCertId(1L)).thenReturn(expectedTags);
        List<Tag> actualTags = tagService.getByCertId(1L);
        assertEquals(expectedTags, actualTags);
    }
}
