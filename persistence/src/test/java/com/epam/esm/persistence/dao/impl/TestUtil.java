package com.epam.esm.persistence.dao.impl;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class TestUtil {
    public static List<Tag> getTagList(String... names) {
        List<Tag> tags = new ArrayList<>();
        Arrays.asList(names).forEach(e -> tags.add(Tag.builder().setName(e).build()));
        return tags;
    }

    public static List<GiftCertificate> getGiftCertificateList(int count) {
        List<GiftCertificate> certList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            certList.add(GiftCertificate.builder()
                    .setName(Integer.toString(i))
                    .setDescription("")
                    .setPrice(BigDecimal.valueOf(100L,2))
                    .setDuration(10)
                    .setCreateDate(LocalDateTime.of(2000,12,1,1,1,1))
                    .setLastUpdateDate(LocalDateTime.of(2000,12,1,1,1,1))
                    .setTags(new ArrayList<>())
                    .build());
        }
        return certList;
    }
}
