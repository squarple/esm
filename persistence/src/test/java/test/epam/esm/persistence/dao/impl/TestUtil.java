package test.epam.esm.persistence.dao.impl;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestUtil {
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
                    .setPrice(BigDecimal.valueOf(1L))
                    .setDuration(10)
                    .setCreateDate(LocalDateTime.now())
                    .setLastUpdateDate(LocalDateTime.now())
                    .build());
        }
        return certList;
    }
}
