package com.epam.esm.service.impl;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.pagination.PageRequest;
import com.epam.esm.model.pagination.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class TestUtil {
    public static List<Tag> getTagList(String... names) {
        List<Tag> tags = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            Tag tag = new Tag();
            tag.setId(i+1L);
            tag.setName(names[i]);
            tags.add(tag);
        }
        return tags;
    }

    public static List<GiftCertificate> getGiftCertificateList(int count) {
        List<GiftCertificate> certList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            GiftCertificate giftCertificate = new GiftCertificate();
            giftCertificate.setId(i+1L);
            giftCertificate.setName(Integer.toString(i));
            giftCertificate.setDescription("");
            giftCertificate.setPrice(BigDecimal.valueOf(100L,2));
            giftCertificate.setDuration(10);
            giftCertificate.setCreateDate(LocalDateTime.of(2000,12,1,1,1,1));
            giftCertificate.setLastUpdateDate(LocalDateTime.of(2000,12,1,1,1,1));
            giftCertificate.setTags(new ArrayList<>());
            certList.add(giftCertificate);
        }
        return certList;
    }

    public static Pageable getPageable() {
        return new PageRequest(0, 10);
    }

    public static List<User> getUserList(int count) {
        List<User> users = new ArrayList<>();
        for (int j = 0; j < count; j++) {
            User user = new User();
            user.setId(j+1L);
            user.setName(Integer.toString(j));
            users.add(user);
        }
        return users;
    }
}
