package com.epam.esm.web.hateoas.assembler;

import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.EntityAlreadyExistsException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.web.controller.TagController;
import com.epam.esm.web.hateoas.util.LinkUtil;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagModelAssembler {
    public EntityModel<TagDto> assembleModel(TagDto tagDto) throws EntityNotFoundException, EntityAlreadyExistsException {
        LinkUtil.setTagLinks(tagDto);
        return EntityModel.of(tagDto);
    }

    public EntityModel<Page<TagDto>> assemblePagedModel(Page<TagDto> page, String name)
            throws EntityNotFoundException, EntityAlreadyExistsException {
        for (TagDto tagDto : page.getContent()) {
            LinkUtil.setTagLinks(tagDto);
        }
        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(TagController.class).getTags(name, page.getNumber(), page.getSize())).withSelfRel());
        links.add(linkTo(methodOn(TagController.class).getTags("", 0, 10)).withRel("tags"));
        links.add(linkTo(methodOn(TagController.class).getTags(name, 0, page.getSize())).withRel("first"));
        if (page.getTotalPages() > 0) {
            links.add(linkTo(methodOn(TagController.class).getTags(name, page.getTotalPages() - 1, page.getSize())).withRel("last"));
        }
        if (page.getNumber() + 1 < page.getTotalPages()) {
            links.add(linkTo(methodOn(TagController.class).getTags(name, page.getNumber() + 1, page.getSize())).withRel("next"));
        }
        if (page.getNumber() > 0) {
            links.add(linkTo(methodOn(TagController.class).getTags(name, page.getNumber() - 1, page.getSize())).withRel("previous"));
        }
        return EntityModel.of(page, links);
    }
}
