package com.epam.esm.web.controller;

import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.EntityAlreadyExistsException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.web.config.TestWebAppContextConfig;
import com.epam.esm.web.hateoas.assembler.TagModelAssembler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@Profile("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = TestWebAppContextConfig.class)
class TagControllerTests {
    @Mock
    private TagService tagService;

    @Mock
    private TagModelAssembler tagModelAssembler;

    private TagController tagController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tagController = new TagController(tagService, tagModelAssembler);
    }

    @Test
    void createTag_ReturnCreatedTag() throws EntityAlreadyExistsException, EntityNotFoundException {
        TagDto tag = TestUtil.getTagList("username").get(0);
        when(tagService.save(tag)).thenReturn(tag);
        when(tagModelAssembler.assembleModel(tag)).thenReturn(EntityModel.of(tag));
        ResponseEntity<EntityModel<TagDto>> expected = new ResponseEntity<>(EntityModel.of(tag), HttpStatus.CREATED);
        ResponseEntity<EntityModel<TagDto>> actual = tagController.createTag(tag);
        assertEquals(expected.getBody().getContent(), actual.getBody().getContent());
    }

    @Test
    void getTag_ReturnTag() throws EntityNotFoundException, EntityAlreadyExistsException {
        TagDto tag = TestUtil.getTagList("username").get(0);
        when(tagService.find(1L)).thenReturn(tag);
        when(tagModelAssembler.assembleModel(tag)).thenReturn(EntityModel.of(tag));
        ResponseEntity<EntityModel<TagDto>> expected = new ResponseEntity<>(EntityModel.of(tag), HttpStatus.OK);
        ResponseEntity<EntityModel<TagDto>> actual = tagController.getTag(1L);
        assertEquals(expected.getBody().getContent(), actual.getBody().getContent());
    }

    @Test
    void getTags_ReturnListOfTags() throws EntityNotFoundException, EntityAlreadyExistsException {
        Pageable pageable = TestUtil.getPageable();
        List<TagDto> tags = TestUtil.getTagList("a", "aa", "aaa");
        Page<TagDto> page = new PageImpl<>(tags, pageable, 3);
        when(tagService.findAll(pageable)).thenReturn(page);
        when(tagModelAssembler.assemblePagedModel(page, null)).thenReturn(EntityModel.of(page));
        ResponseEntity<EntityModel<Page<TagDto>>> expected = new ResponseEntity<>(EntityModel.of(page), HttpStatus.OK);
        ResponseEntity<EntityModel<Page<TagDto>>> actual = tagController.getTags(null, 0, 10);
        assertEquals(expected.getBody().getContent(), actual.getBody().getContent());
    }

    @Test
    void getTagsByName_ReturnListOfTags() throws EntityNotFoundException, EntityAlreadyExistsException {
        Pageable pageable = TestUtil.getPageable();
        List<TagDto> tags = TestUtil.getTagList("a", "aa", "aaa").stream().sorted(Comparator.comparing(TagDto::getName)).collect(Collectors.toList());
        Page<TagDto> page = new PageImpl<>(tags, pageable, 3);
        when(tagModelAssembler.assemblePagedModel(page, "a")).thenReturn(EntityModel.of(page));
        when(tagService.findByName("a", pageable)).thenReturn(page);
        ResponseEntity<EntityModel<Page<TagDto>>> expected = new ResponseEntity<>(EntityModel.of(page), HttpStatus.OK);
        ResponseEntity<EntityModel<Page<TagDto>>> actual = tagController.getTags("a", 0, 10);
        assertEquals(expected.getBody().getContent(), actual.getBody().getContent());
    }

    @Test
    void deleteTag() throws EntityNotFoundException {
        ResponseEntity<Void> expected = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        ResponseEntity<Void> actual = tagController.deleteTag(1L);
        assertEquals(expected, actual);
    }
}
