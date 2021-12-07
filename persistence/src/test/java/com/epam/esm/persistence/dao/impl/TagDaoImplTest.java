package com.epam.esm.persistence.dao.impl;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.persistence.config.TestPersistenceConfig;
import com.epam.esm.persistence.dao.GiftCertificateDao;
import com.epam.esm.persistence.dao.TagDao;
import com.epam.esm.persistence.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.esm.persistence.dao.impl.TestUtil.getTagList;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = {"test"})
@ContextConfiguration(classes = {TestPersistenceConfig.class})
class TagDaoImplTest {
    @Autowired
    private TagDao tagDao;

    @Autowired
    private GiftCertificateDao certDao;

    @Test
    void create_Successful() {
        Tag expectedTag = Tag.builder().setName("name").build();
        Tag actualTag = tagDao.create(expectedTag);
        assertEquals(expectedTag, actualTag);
        tagDao.delete(actualTag.getId());
    }

    @Test
    void find_ExistedTag_ReturnTag() throws EntityNotFoundException {
        Tag expectedTag = Tag.builder().setName("name").build();
        expectedTag = tagDao.create(expectedTag);
        Tag actualTag = tagDao.find(expectedTag.getId());
        assertEquals(expectedTag, actualTag);
        tagDao.delete(actualTag.getId());
    }

    @Test
    void find_NonExistedTag_ReturnOptionalEmpty() throws EntityNotFoundException {
        assertThrows(EntityNotFoundException.class, () -> tagDao.find(0L));
    }

    @Test
    void findAll_ReturnListOfTags() {
        List<Tag> expectedTags = getTagList("1", "2", "3");
        expectedTags.forEach(e -> tagDao.create(e));
        expectedTags = expectedTags.stream().sorted(Comparator.comparing(Tag::getId)).collect(Collectors.toList());
        List<Tag> actualTags = tagDao.findAll().stream().sorted(Comparator.comparing(Tag::getId)).collect(Collectors.toList());
        assertEquals(expectedTags, actualTags);
        expectedTags.stream()
                .map(Tag::getId)
                .forEach(tagDao::delete);
    }

    @Test
    void delete_Successful() throws EntityNotFoundException {
        Tag tag = Tag.builder().setName("tag to delete").build();
        tagDao.create(tag);
        Tag createdTag = tagDao.find(tag.getId());
        tagDao.delete(createdTag.getId());
        assertThrows(EntityNotFoundException.class, () -> tagDao.find(createdTag.getId()));
    }

    @Test
    void findByName_Successful() {
        List<Tag> tags = getTagList("a", "aa", "ab", "b");
        tags.forEach(tagDao::create);
        List<Tag> expectedTags = tags.stream()
                .limit(3)
                .sorted(Comparator.comparing(Tag::getId))
                .collect(Collectors.toList());
        List<Tag> actualTags = tagDao.findByName("a");
        actualTags = actualTags.stream()
                .sorted(Comparator.comparing(Tag::getId))
                .collect(Collectors.toList());
        assertEquals(expectedTags, actualTags);
        tags.stream()
                .map(Tag::getId)
                .forEach(tagDao::delete);
    }

    @Test
    void findByCertId() {
        List<Tag> expectedTags = TestUtil.getTagList("tag1", "tag2", "tag3");
        expectedTags.forEach(e -> tagDao.create(e));
        GiftCertificate cert = GiftCertificate.builder()
                .setName("name")
                .setDescription("descr")
                .setPrice(BigDecimal.valueOf(1L))
                .setDuration(10)
                .setCreateDate(LocalDateTime.now())
                .setLastUpdateDate(LocalDateTime.now())
                .setTags(expectedTags)
                .build();
        certDao.create(cert);
        List<Tag> actualTags = tagDao.findByCertId(cert.getId()).stream().sorted(Comparator.comparing(Tag::getId)).collect(Collectors.toList());
        expectedTags = expectedTags.stream().sorted(Comparator.comparing(Tag::getId)).collect(Collectors.toList());
        assertEquals(expectedTags, actualTags);
        certDao.delete(cert.getId());
        expectedTags.stream().map(Tag::getId).forEach(e -> tagDao.delete(e));
    }
}
