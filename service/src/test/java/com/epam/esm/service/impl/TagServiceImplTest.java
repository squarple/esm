package com.epam.esm.service.impl;

import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.service.config.TestServiceConfig;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.EntityAlreadyExistsException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
@Profile("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = TestServiceConfig.class)
class TagServiceImplTest {
    @Mock
    private TagRepository tagRepository;

    private TagServiceImpl tagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tagService = new TagServiceImpl(tagRepository);
    }

    @Test
    void save_ReturnCreatedTag() throws EntityAlreadyExistsException {
        TagDto expectedTagDto = TestUtil.getTagDtoList("username").get(0);
        when(tagRepository.save(expectedTagDto.toTag())).thenReturn(expectedTagDto.toTag());
        TagDto actualTagDto = tagService.save(expectedTagDto);
        assertEquals(expectedTagDto, actualTagDto);
    }

    @Test
    void find_ExistedTagId_ReturnTag() throws EntityNotFoundException {
        TagDto expectedTagDto = TestUtil.getTagDtoList("username").get(0);
        when(tagRepository.findById(expectedTagDto.getId())).thenReturn(Optional.of(expectedTagDto.toTag()));
        TagDto actualTagDto = tagService.find(expectedTagDto.getId());
        assertEquals(expectedTagDto, actualTagDto);
    }

    @Test
    void find_NonExistedTagId_ThrowEntityNotFoundException() {
        when(tagRepository.findById(0L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> tagService.find(0L));
    }

    @Test
    void findAll_ReturnListOfTags() {
        Pageable pageable = TestUtil.getPageable();
        List<TagDto> expectedTagsDto = TestUtil.getTagDtoList("name1", "name2");
        Page<TagDto> page = new PageImpl<>(expectedTagsDto, pageable, 2);
        when(tagRepository.findAll(pageable)).thenReturn(page.map(TagDto::toTag));
        Page<TagDto> actualTags = tagService.findAll(pageable);
        assertEquals(page, actualTags);
    }

    @Test
    void findByName_ReturnListOfTags() {
        Pageable pageable = TestUtil.getPageable();
        List<TagDto> expectedTagsDto = TestUtil.getTagDtoList("name1", "name2");
        Page<TagDto> page = new PageImpl<>(expectedTagsDto, pageable, 2);
        when(tagRepository.findByNameContaining("n", pageable)).thenReturn(page.map(TagDto::toTag));
        Page<TagDto> actualTags = tagService.findByName("n", pageable);
        assertEquals(page, actualTags);
    }
}
