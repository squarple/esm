package com.epam.esm.service.dto;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.service.dto.validation.marker.OnCreate;
import com.epam.esm.service.dto.validation.marker.OnUpdate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {
    @Null(groups = {OnCreate.class, OnUpdate.class}, message = "{cert.id.null}")
    private Long id;

    @NotBlank(groups = {OnCreate.class}, message = "{cert.name.not.blank}")
    @Size(groups = {OnCreate.class, OnUpdate.class}, min = 1, max = 30, message = "{cert.username.size}")
    private String name;

    @NotBlank(groups = {OnCreate.class}, message = "{cert.description.not.blank}")
    @Size(groups = {OnCreate.class, OnUpdate.class}, min = 1, max = 2000, message = "{cert.description.size}")
    private String description;

    @NotNull(groups = OnCreate.class, message = "{cert.price.not.null}")
    @DecimalMin(groups = {OnCreate.class, OnUpdate.class}, value = "0.0", message = "{cert.price.decimal.min}")
    @Digits(groups = {OnCreate.class, OnUpdate.class}, integer = 9, fraction = 2, message = "{cert.price.digits}")
    private BigDecimal price;

    @NotNull(groups = OnCreate.class, message = "{cert.duration.not.null}")
    @DecimalMin(groups = {OnCreate.class, OnUpdate.class}, value = "1", message = "{cert.duration.decimal.min}")
    @DecimalMax(groups = {OnCreate.class, OnUpdate.class}, value = "365", message = "{cert.duration.decimal.max}")
    private Integer duration;

    private LocalDateTime createDate;

    private LocalDateTime lastUpdateDate;

    @Valid
    private List<TagDto> tags;

    public GiftCertificate toGiftCertificate() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(id);
        giftCertificate.setName(name);
        giftCertificate.setDescription(description);
        giftCertificate.setPrice(price);
        giftCertificate.setDuration(duration);
        giftCertificate.setCreateDate(createDate);
        giftCertificate.setLastUpdateDate(lastUpdateDate);
        tags = tags == null ? new ArrayList<>() : tags;
        giftCertificate.setTags(tags
                .stream()
                .map(TagDto::toTag)
                .collect(Collectors.toList()));
        return giftCertificate;
    }

    public static GiftCertificateDto fromGiftCertificate(GiftCertificate giftCertificate) {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setId(giftCertificate.getId());
        giftCertificateDto.setName(giftCertificate.getName());
        giftCertificateDto.setDescription(giftCertificate.getDescription());
        giftCertificateDto.setPrice(giftCertificate.getPrice());
        giftCertificateDto.setDuration(giftCertificate.getDuration());
        giftCertificateDto.setCreateDate(giftCertificate.getCreateDate());
        giftCertificateDto.setLastUpdateDate(giftCertificate.getLastUpdateDate());
        giftCertificateDto.setTags(giftCertificate.getTags()
                .stream()
                .map(TagDto::fromTag)
                .collect(Collectors.toList()));
        return giftCertificateDto;
    }
}
