package com.epam.esm.persistence.dao.impl;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.persistence.config.TestPersistenceConfig;
import com.epam.esm.persistence.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = {"test"})
@EntityScan("com.epam.esm")
@EnableJpaRepositories("com.epam.esm.persistence.repository")
@SpringBootTest(classes = TestPersistenceConfig.class)
@Transactional
class TagRepositoryTest {
    @Autowired
    TagRepository tagRepository;

    @Test
    void findByNameLike_ReturnFoundedTags() {
        List<Tag> tags = TestUtil.getTagList("tag", "nonTag");
        tagRepository.saveAll(tags);
        Page<Tag> tagPage = tagRepository.findByNameContaining("non", TestUtil.getPageable());
        assertEquals(1, tagPage.getContent().size());
        assertEquals(tags.get(1), tagPage.getContent().get(0));
        tagRepository.deleteAll(tags);
    }

    @Test
    void findByNameLike_ReturnEmptyPage() {
        Page<Tag> tagPage = tagRepository.findByNameContaining("non", TestUtil.getPageable());
        assertEquals(0, tagPage.getContent().size());
    }

    @Test
    void existsByName_ExistedTag_ReturnTrue() {
        Tag tag = TestUtil.getTagList("tag").get(0);
        tagRepository.save(tag);
        assertTrue(tagRepository.existsByName("tag"));
        tagRepository.deleteById(tag.getId());
    }

    @Test
    void existsByName_NonExistedTag_ReturnFalse() {
        assertFalse(tagRepository.existsByName("tag"));
    }
}
