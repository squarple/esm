package com.epam.esm.web.hateoas.util;

import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.exception.EntityAlreadyExistsException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ForbiddenActionException;
import com.epam.esm.web.controller.GiftCertificateController;
import com.epam.esm.web.controller.OrderController;
import com.epam.esm.web.controller.TagController;
import com.epam.esm.web.controller.UserController;
import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The LinkUtil class.
 */
public final class LinkUtil {
    private LinkUtil() {

    }

    /**
     * Sets gift certificate links.
     *
     * @param giftCertificateDto    the gift certificate
     * @param isPossibleToDelete the is possible to delete
     * @throws EntityAlreadyExistsException if entity already exists
     * @throws ForbiddenActionException     if forbidden action
     * @throws EntityNotFoundException      if entity not found
     */
    public static void setGiftCertificateLinks(GiftCertificateDto giftCertificateDto, boolean isPossibleToDelete) throws EntityAlreadyExistsException, ForbiddenActionException, EntityNotFoundException, ForbiddenActionException {
        if (giftCertificateDto.hasLinks()) {
            return;
        }
        setGiftCertificateLinks(giftCertificateDto);
        if (isPossibleToDelete) {
            giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class).deleteGiftCertificate(giftCertificateDto.getId())).withRel("delete"));
        }
    }

    private static void setGiftCertificateLinks(GiftCertificateDto giftCertificateDto) throws EntityAlreadyExistsException, ForbiddenActionException, EntityNotFoundException {
        if (giftCertificateDto.hasLinks()) {
            return;
        }
        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(GiftCertificateController.class).getGiftCertificate(giftCertificateDto.getId())).withSelfRel());
        links.add(linkTo(methodOn(GiftCertificateController.class).patchGiftCertificate(giftCertificateDto.getId(), giftCertificateDto)).withRel("edit"));
        giftCertificateDto.add(links);
        for (TagDto tag : giftCertificateDto.getTags()) {
            setTagLinks(tag);
        }
    }

    /**
     * Sets order links.
     *
     * @param order the order
     * @throws EntityAlreadyExistsException if entity already exists
     * @throws ForbiddenActionException     if forbidden action
     * @throws EntityNotFoundException      if entity not found
     */
    public static void setOrderLinks(OrderDto order) throws EntityAlreadyExistsException, ForbiddenActionException, EntityNotFoundException {
        if (order.hasLinks()) {
            return;
        }
        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(OrderController.class).getOrder(order.getId())).withSelfRel());
        links.add(linkTo(methodOn(UserController.class).getUser(order.getUserDto().getId())).withRel("user"));
        links.add(linkTo(methodOn(GiftCertificateController.class).getGiftCertificate(order.getGiftCertificateDto().getId())).withRel("giftCertificate"));
        order.add(links);
        setUserLinks(order.getUserDto());
        setGiftCertificateLinks(order.getGiftCertificateDto());
    }

    /**
     * Sets tag links.
     *
     * @param tagDto the tag
     * @throws EntityAlreadyExistsException if entity already exists
     * @throws EntityNotFoundException      if entity not found
     */
    public static void setTagLinks(TagDto tagDto) throws EntityAlreadyExistsException, EntityNotFoundException {
        if (tagDto.hasLinks()) {
            return;
        }
        tagDto.add(linkTo(methodOn(TagController.class).getTag(tagDto.getId())).withSelfRel());
        tagDto.add(linkTo(methodOn(TagController.class).deleteTag(tagDto.getId())).withRel("delete"));
    }

    /**
     * Sets user links.
     *
     * @param userDto the user
     * @throws EntityNotFoundException if entity not found
     */
    public static void setUserLinks(UserDto userDto) throws EntityNotFoundException {
        if (userDto.hasLinks()) {
            return;
        }
        userDto.add(linkTo(methodOn(UserController.class).getUser(userDto.getId())).withSelfRel());
    }
}
