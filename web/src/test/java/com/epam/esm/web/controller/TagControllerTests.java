package com.epam.esm.web.controller;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.pagination.Page;
import com.epam.esm.model.pagination.PageImpl;
import com.epam.esm.model.pagination.Pageable;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.EntityAlreadyExistsException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ResourceNotFoundException;
import com.epam.esm.web.config.TestWebAppContextConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//@ActiveProfiles("test")
@Profile("test")
@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
//@Import(TestWebAppContextConfig.class)
@SpringBootTest(classes = TestWebAppContextConfig.class)
class TagControllerTests {
    @Mock
    private TagService tagService;
    private TagController tagController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tagController = new TagController(tagService);
    }

    @Test
    void createTag_ReturnCreatedTag() throws EntityAlreadyExistsException, EntityNotFoundException {
        Tag tag = TestUtil.getTagList("name").get(0);
        when(tagService.save(tag)).thenReturn(tag);
        ResponseEntity<EntityModel<Tag>> expected = new ResponseEntity<>(EntityModel.of(tag), HttpStatus.CREATED);
        ResponseEntity<EntityModel<Tag>> actual = tagController.createTag(tag);
        assertEquals(expected.getBody().getContent(), actual.getBody().getContent());
    }

    @Test
    void getTag_ReturnTag() throws EntityNotFoundException, EntityAlreadyExistsException {
        Tag tag = TestUtil.getTagList("name").get(0);
        when(tagService.get(1L)).thenReturn(tag);
        ResponseEntity<EntityModel<Tag>> expected = new ResponseEntity<>(EntityModel.of(tag), HttpStatus.OK);
        ResponseEntity<EntityModel<Tag>> actual = tagController.getTag(1L);
        assertEquals(expected.getBody().getContent(), actual.getBody().getContent());
    }

    @Test
    void getTags_ReturnListOfTags() throws EntityNotFoundException, ResourceNotFoundException, EntityAlreadyExistsException {
        Pageable pageable = TestUtil.getPageable();
        List<Tag> tags = TestUtil.getTagList("a", "aa", "aaa");
        Page<Tag> page = new PageImpl<>(tags, pageable, 3);
        when(tagService.getAll(pageable)).thenReturn(page);
        ResponseEntity<EntityModel<Page<Tag>>> expected = new ResponseEntity<>(EntityModel.of(page), HttpStatus.OK);
        ResponseEntity<EntityModel<Page<Tag>>> actual = tagController.getTags(null, 0, 10);
        assertEquals(expected.getBody().getContent(), actual.getBody().getContent());
    }

    @Test
    void getTagsByName_ReturnListOfTags() throws ResourceNotFoundException, EntityNotFoundException, EntityAlreadyExistsException {
        Pageable pageable = TestUtil.getPageable();
        List<Tag> tags = TestUtil.getTagList("a", "aa", "aaa").stream().sorted(Comparator.comparing(Tag::getName)).collect(Collectors.toList());
        Page<Tag> page = new PageImpl<>(tags, pageable, 3);
        when(tagService.getByName("a", pageable)).thenReturn(page);
        ResponseEntity<EntityModel<Page<Tag>>> expected = new ResponseEntity<>(EntityModel.of(page), HttpStatus.OK);
        ResponseEntity<EntityModel<Page<Tag>>> actual = tagController.getTags("a", 0, 10);
        assertEquals(expected.getBody().getContent(), actual.getBody().getContent());
    }

    @Test
    void deleteTag() throws EntityNotFoundException {
        ResponseEntity<Void> expected = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        ResponseEntity<Void> actual = tagController.deleteTag(1L);
        assertEquals(expected, actual);
    }
}
