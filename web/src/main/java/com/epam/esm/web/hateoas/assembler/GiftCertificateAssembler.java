package com.epam.esm.web.hateoas.assembler;

import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.exception.EntityAlreadyExistsException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ForbiddenActionException;
import com.epam.esm.web.controller.GiftCertificateController;
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
public class GiftCertificateAssembler {
    public EntityModel<GiftCertificateDto> assembleModel(GiftCertificateDto giftCertificateDto, boolean isPossibleToDelete)
            throws EntityNotFoundException, EntityAlreadyExistsException, ForbiddenActionException {
        LinkUtil.setGiftCertificateLinks(giftCertificateDto, isPossibleToDelete);
        return EntityModel.of(giftCertificateDto);
    }

    public EntityModel<Page<GiftCertificateDto>> assemblePagedModel(Page<GiftCertificateDto> page, GiftCertificateService giftCertificateService)
            throws EntityNotFoundException, EntityAlreadyExistsException, ForbiddenActionException {
        for (GiftCertificateDto giftCertificateDto : page.getContent()) {
            LinkUtil.setGiftCertificateLinks(giftCertificateDto, giftCertificateService.isPossibleToDelete(giftCertificateDto.getId()));
        }
        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(GiftCertificateController.class).getGiftCertificates(null, null, null, null, null, page.getNumber(), page.getSize())).withSelfRel());
        links.add(linkTo(methodOn(GiftCertificateController.class).getGiftCertificates(null, null, null, null, null, 0, 10)).withRel("giftCertificates"));
        links.add(linkTo(methodOn(GiftCertificateController.class).getGiftCertificates(null, null, null, null, null, 0, page.getSize())).withRel("first"));
        if (page.getTotalPages() > 0) {
            links.add(linkTo(methodOn(GiftCertificateController.class).getGiftCertificates(null, null, null, null, null, page.getTotalPages() - 1, page.getSize())).withRel("last"));
        }
        if (page.getNumber() + 1 < page.getTotalPages()) {
            links.add(linkTo(methodOn(GiftCertificateController.class).getGiftCertificates(null, null, null, null, null, page.getNumber() + 1, page.getSize())).withRel("next"));
        }
        if (page.getNumber() > 0) {
            links.add(linkTo(methodOn(GiftCertificateController.class).getGiftCertificates(null, null, null, null, null, page.getNumber() - 1, page.getSize())).withRel("previous"));
        }
        return EntityModel.of(page, links);
    }
}
