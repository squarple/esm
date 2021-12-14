package com.epam.esm.web.controller;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.web.config.TestWebAppContextConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestWebAppContextConfig.class})
class TagControllerTests {
    private TagService tagService;
    private TagController tagController;

    @BeforeEach
    void setUp() {
        tagService = mock(TagService.class);
        tagController = new TagController(tagService);
    }

    @Test
    void createTag_ReturnCreatedTag() {
        Tag tag = Tag.builder().setId(1L).setName("name").build();
        when(tagService.save(tag)).thenReturn(tag);
        ResponseEntity<Tag> expected = new ResponseEntity<>(tag, HttpStatus.CREATED);
        ResponseEntity<Tag> actual = tagController.createTag(tag);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getTag_ReturnTag() throws EntityNotFoundException {
        Tag tag = Tag.builder().setId(1L).setName("name").build();
        when(tagService.get(1L)).thenReturn(tag);
        ResponseEntity<Tag> expected = new ResponseEntity<>(tag, HttpStatus.OK);
        ResponseEntity<Tag> actual = tagController.getTag(1L);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getTags_ReturnListOfTags() throws EntityNotFoundException {
        List<Tag> tags = TestUtil.getTagList("a", "aa", "aaa");
        when(tagService.getAll()).thenReturn(tags);
        ResponseEntity<List<Tag>> expected = new ResponseEntity<>(tags, HttpStatus.OK);
        ResponseEntity<List<Tag>> actual = tagController.getTags(null);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getTagsByName_ReturnListOfTags() throws EntityNotFoundException {
        List<Tag> tags = TestUtil.getTagList("a", "aa", "aaa");
        when(tagService.getByName("a")).thenReturn(tags);
        ResponseEntity<List<Tag>> expected = new ResponseEntity<>(tags, HttpStatus.OK);
        ResponseEntity<List<Tag>> actual = tagController.getTags("a");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void deleteTag() throws EntityNotFoundException {
        Tag tag = Tag.builder().setId(1L).setName("name").build();
        ResponseEntity<Void> expected = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        ResponseEntity<Void> actual = tagController.deleteTag(1L);
        Assertions.assertEquals(expected, actual);
    }
}
