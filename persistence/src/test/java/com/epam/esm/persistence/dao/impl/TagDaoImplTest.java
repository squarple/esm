package com.epam.esm.persistence.dao.impl;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.pagination.Pageable;
import com.epam.esm.persistence.config.TestPersistenceConfig;
import com.epam.esm.persistence.dao.TagDao;
import com.epam.esm.persistence.exception.EntityAlreadyExistsDaoException;
import com.epam.esm.persistence.exception.EntityNotFoundDaoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.persistence.dao.impl.TestUtil.getTagList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = {"test"})
//@Import(TestPersistenceConfig.class)
@EntityScan("com.epam.esm")
@SpringBootTest(classes = TestPersistenceConfig.class)
class TagDaoImplTest {
    @Autowired
    private TagDao tagDao;

    @Test
    void create_Successful() throws EntityAlreadyExistsDaoException, EntityNotFoundDaoException {
        Tag expectedTag = TestUtil.getTagList("name5").get(0);
        expectedTag = tagDao.create(expectedTag);
        Tag actualTag = tagDao.find(expectedTag.getId());
        assertEquals(expectedTag, actualTag);
        tagDao.delete(actualTag.getId());
    }

    @Test
    void find_ExistedTag_ReturnTag() throws EntityNotFoundDaoException, EntityAlreadyExistsDaoException {
        Tag expectedTag = TestUtil.getTagList("name1").get(0);
        expectedTag = tagDao.create(expectedTag);
        Tag actualTag = tagDao.find(expectedTag.getId());
        assertEquals(expectedTag, actualTag);
        tagDao.delete(actualTag.getId());
    }

    @Test
    void find_NonExistedTag_ReturnOptionalEmpty() {
        assertThrows(EntityNotFoundDaoException.class, () -> tagDao.find(0L));
    }

    @Test
    void findAll_ReturnListOfTags() throws EntityAlreadyExistsDaoException, EntityNotFoundDaoException {
        Pageable pageable = TestUtil.getPageable();
        List<Tag> expectedTags = getTagList("11", "21", "31");
        for (Tag expectedTag : expectedTags) {
            tagDao.create(expectedTag);
        }
        expectedTags = expectedTags.stream().sorted(Comparator.comparing(Tag::getId)).collect(Collectors.toList());
        List<Tag> actualTags = tagDao.findAll(pageable).getContent().stream().sorted(Comparator.comparing(Tag::getId)).collect(Collectors.toList());
        assertEquals(expectedTags, actualTags);
        TagDao tagDao1 = tagDao;
        for (Tag expectedTag : expectedTags) {
            Long id = expectedTag.getId();
            tagDao1.delete(id);
        }
    }

    @Test
    void delete_Successful() throws EntityNotFoundDaoException, EntityAlreadyExistsDaoException {
        Tag tag = TestUtil.getTagList("name2").get(0);
        tagDao.create(tag);
        Tag createdTag = tagDao.find(tag.getId());
        tagDao.delete(createdTag.getId());
        assertThrows(EntityNotFoundDaoException.class, () -> tagDao.find(createdTag.getId()));
    }

    @Test
    void findByName_Successful() throws EntityAlreadyExistsDaoException, EntityNotFoundDaoException {
        Pageable pageable = TestUtil.getPageable();
        List<Tag> tags = getTagList("a1", "aa1", "ab1", "b1");
        for (Tag tag : tags) {
            tagDao.create(tag);
        }
        List<Tag> expectedTags = tags.stream()
                .limit(3)
                .sorted(Comparator.comparing(Tag::getId))
                .collect(Collectors.toList());
        List<Tag> actualTags = tagDao.findByName("a", pageable).getContent();
        actualTags = actualTags.stream()
                .sorted(Comparator.comparing(Tag::getId))
                .collect(Collectors.toList());
        assertEquals(expectedTags, actualTags);
        TagDao tagDao1 = tagDao;
        for (Tag tag : tags) {
            Long id = tag.getId();
            tagDao1.delete(id);
        }
    }
}
