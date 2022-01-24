package com.epam.esm.service.impl;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.User;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.dto.UserDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class TestUtil {
    public static List<TagDto> getTagDtoList(String... names) {
        List<TagDto> tags = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            TagDto tag = new TagDto();
            tag.setId(i+1L);
            tag.setName(names[i]);
            tags.add(tag);
        }
        return tags;
    }

    public static List<GiftCertificateDto> getGiftCertificateDtoList(int count) {
        List<GiftCertificateDto> certList = new ArrayList<>();
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
            certList.add(GiftCertificateDto.fromGiftCertificate(giftCertificate));
        }
        return certList;
    }

    public static Pageable getPageable() {
        return PageRequest.of(0, 10);
    }

    public static List<UserDto> getUserDtoList(int count) {
        List<UserDto> users = new ArrayList<>();
        for (int j = 0; j < count; j++) {
            User user = new User();
            user.setId(j+1L);
            user.setUsername(Integer.toString(j));
            user.setEmail(j + "@mail.com");
            users.add(UserDto.fromUser(user));
        }
        return users;
    }
}
