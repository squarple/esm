package com.epam.esm.web.controller;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.dto.UserDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class TestUtil {
    public static List<TagDto> getTagList(String... names) {
        List<Tag> tags = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            Tag tag = new Tag();
            tag.setId(i+1L);
            tag.setName(names[i]);
            tags.add(tag);
        }
        return tags.stream()
                .map(TagDto::fromTag)
                .collect(Collectors.toList());
    }

    public static List<GiftCertificateDto> getGiftCertificateList(int count) {
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
        return certList.stream()
                .map(GiftCertificateDto::fromGiftCertificate)
                .collect(Collectors.toList());
    }

    public static Pageable getPageable() {
        return PageRequest.of(0, 10);
    }

    public static List<UserDto> getUserList(int count) {
        List<User> users = new ArrayList<>();
        for (int j = 0; j < count; j++) {
            User user = new User();
            user.setId(j+1L);
            user.setUsername(Integer.toString(j));
            user.setEmail(j + "@mail.com");
            users.add(user);
        }
        return users.stream()
                .map(UserDto::fromUser)
                .collect(Collectors.toList());
    }

    public static OrderDto createOrder(UserDto user, GiftCertificateDto giftCertificate) {
        OrderDto order = new OrderDto();
        order.setId(1L);
        order.setUserDto(user);
        order.setGiftCertificateDto(giftCertificate);
        order.setCost(giftCertificate.getPrice());
        order.setPurchaseDate(LocalDateTime.of(2000,12,1,1,1,1));
        return order;
    }
}
