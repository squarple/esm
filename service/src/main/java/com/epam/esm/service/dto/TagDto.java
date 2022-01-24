package com.epam.esm.service.dto;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.dto.validation.marker.OnCreate;
import com.epam.esm.service.dto.validation.marker.OnUpdate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TagDto extends RepresentationModel<TagDto> {
    @Null(groups = {OnCreate.class, OnUpdate.class}, message = "{tag.id.null}")
    private Long id;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "{tag.username.not.blank}")
    @Size(groups = {OnCreate.class, OnUpdate.class}, message = "{tag.username.size}")
    private String name;

    public Tag toTag() {
        Tag tag = new Tag();
        tag.setId(id);
        tag.setName(name);
        return tag;
    }

    public static TagDto fromTag(Tag tag) {
        TagDto tagDto = new TagDto();
        tagDto.setId(tag.getId());
        tagDto.setName(tag.getName());
        return tagDto;
    }
}
