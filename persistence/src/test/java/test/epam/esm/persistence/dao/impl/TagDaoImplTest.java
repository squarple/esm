package test.epam.esm.persistence.dao.impl;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.persistence.dao.GiftCertificateDao;
import com.epam.esm.persistence.dao.TagDao;
import jakarta.validation.constraints.AssertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static test.epam.esm.persistence.dao.impl.TestUtil.getTagList;

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
    void find_ExistedTag_ReturnTag() {
        Tag expectedTag = Tag.builder().setName("name").build();
        expectedTag = tagDao.create(expectedTag);
        Optional<Tag> actualTagOptional = tagDao.find(expectedTag.getId());
        if (!actualTagOptional.isPresent()) {
            fail();
        }
        assertEquals(expectedTag, actualTagOptional.get());
        tagDao.delete(actualTagOptional.get().getId());
    }

    @Test
    void find_NonExistedTag_ReturnOptionalEmpty() {
        Optional<Tag> actualTagOptional = tagDao.find(0L);
        assertFalse(actualTagOptional.isPresent());
    }

    @Test
    void findAll_ReturnListOfTags() { //fixme comparator???
        List<Tag> expectedTags = getTagList("1", "2", "3");
        expectedTags.forEach(tagDao::create);
        List<Tag> actualTags = tagDao.findAll();
        assertEquals(expectedTags, actualTags);
        expectedTags.stream()
                .map(Tag::getId)
                .forEach(tagDao::delete);
    }

    @Test
    void update_Successful() {
        Tag initialTag = Tag.builder().setName("initial tag").build();
        tagDao.create(initialTag);
        Tag expectedTag = Tag.builder().setId(initialTag.getId()).setName("mod tag").build();
        tagDao.update(expectedTag);
        Optional<Tag> actualTagOptional = tagDao.find(expectedTag.getId());
        if (!actualTagOptional.isPresent()) {
            fail();
        }
        assertEquals(expectedTag, actualTagOptional.get());
        tagDao.delete(expectedTag.getId());
    }

    @Test
    void delete_Successful() {
        Tag tag = Tag.builder().setName("tag to delete").build();
        tagDao.create(tag);
        Optional<Tag> createdTag = tagDao.find(tag.getId());
        if (!createdTag.isPresent()) {
            fail();
        }
        tagDao.delete(createdTag.get().getId());
        Optional<Tag> actualTag = tagDao.find(createdTag.get().getId());
        assertFalse(actualTag.isPresent());
    }

    @Test
    void findByName_Successful() {
        List<Tag> tags = getTagList("a", "aa", "ab", "b");
        List<Tag> expectedTags = tags.stream()
                .limit(3)
                .sorted(Comparator.comparing(Tag::getId))
                .collect(Collectors.toList());
        tags.forEach(tagDao::create);
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
        GiftCertificate cert = GiftCertificate.builder()
                .setName("name")
                .setDescription("descr")
                .setPrice(BigDecimal.valueOf(1L))
                .setDuration(10)
                .setCreateDate(LocalDateTime.now())
                .setLastUpdateDate(LocalDateTime.now())
                .build();
        List<Tag> expectedTags = TestUtil.getTagList("tag1", "tag2", "tag3").stream().sorted().collect(Collectors.toList());
        certDao.create(cert);
        expectedTags.forEach(e -> tagDao.create(e));
        tagDao.addConnections(cert.getId(), expectedTags.stream().map(Tag::getId).collect(Collectors.toList()));
        List<Tag> actualTags = tagDao.findByCertId(cert.getId()).stream().sorted().collect(Collectors.toList());
        assertEquals(expectedTags, actualTags);
        certDao.delete(cert.getId());
        expectedTags.stream().map(Tag::getId).forEach(e -> tagDao.delete(e));
    }

    @Test
    void addConnections_Successful() {
        GiftCertificate cert = GiftCertificate.builder()
                .setName("name")
                .setDescription("descr")
                .setPrice(BigDecimal.valueOf(1L))
                .setDuration(10)
                .setCreateDate(LocalDateTime.now())
                .setLastUpdateDate(LocalDateTime.now())
                .build();
        List<Tag> expectedTags = TestUtil.getTagList("tag1", "tag2", "tag3").stream().sorted().collect(Collectors.toList());
        certDao.create(cert);
        expectedTags.forEach(e -> tagDao.create(e));
        tagDao.addConnections(cert.getId(), expectedTags.stream().map(Tag::getId).collect(Collectors.toList()));
        List<Tag> actualTags = tagDao.findByCertId(cert.getId()).stream().sorted().collect(Collectors.toList());
        assertEquals(expectedTags, actualTags);
        certDao.delete(cert.getId());
        expectedTags.stream().map(Tag::getId).forEach(e -> tagDao.delete(e));
    }

    @Test
    void removeConnections_Successful() {
        GiftCertificate cert = GiftCertificate.builder()
                .setName("name")
                .setDescription("descr")
                .setPrice(BigDecimal.valueOf(1L))
                .setDuration(10)
                .setCreateDate(LocalDateTime.now())
                .setLastUpdateDate(LocalDateTime.now())
                .build();
        List<Tag> expectedTags = TestUtil.getTagList("tag1", "tag2", "tag3").stream().sorted().collect(Collectors.toList());
        certDao.create(cert);
        expectedTags.forEach(e -> tagDao.create(e));
        tagDao.addConnections(cert.getId(), expectedTags.stream().map(Tag::getId).collect(Collectors.toList()));
        List<Tag> actualTags = tagDao.findByCertId(cert.getId()).stream().sorted().collect(Collectors.toList());
        if (!expectedTags.equals(actualTags)) {
            fail();
        }
        tagDao.removeConnections(cert.getId(), expectedTags.stream().map(Tag::getId).collect(Collectors.toList()));
        List<Tag> emptyTagsList = tagDao.findByCertId(cert.getId());
        assertTrue(emptyTagsList.isEmpty());
        certDao.delete(cert.getId());
        expectedTags.stream().map(Tag::getId).forEach(e -> tagDao.delete(e));
    }
}
