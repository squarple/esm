package com.epam.esm.web.hateoas;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import com.epam.esm.persistence.exception.ForbiddenActionException;
import com.epam.esm.service.exception.EntityAlreadyExistsException;
import com.epam.esm.service.exception.EntityNotFoundException;
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
     * @param giftCertificate    the gift certificate
     * @param isPossibleToDelete the is possible to delete
     * @throws EntityAlreadyExistsException if entity already exists
     * @throws ForbiddenActionException     if forbidden action
     * @throws EntityNotFoundException      if entity not found
     */
    public static void setGiftCertificateLinks(GiftCertificate giftCertificate, boolean isPossibleToDelete) throws EntityAlreadyExistsException, ForbiddenActionException, EntityNotFoundException {
        if (giftCertificate.hasLinks()) {
            return;
        }
        setGiftCertificateLinks(giftCertificate);
        if (isPossibleToDelete) {
            giftCertificate.add(linkTo(methodOn(GiftCertificateController.class).deleteGiftCertificate(giftCertificate.getId())).withRel("delete"));
        }
    }

    private static void setGiftCertificateLinks(GiftCertificate giftCertificate) throws EntityAlreadyExistsException, ForbiddenActionException, EntityNotFoundException {
        if (giftCertificate.hasLinks()) {
            return;
        }
        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(GiftCertificateController.class).getGiftCertificate(giftCertificate.getId())).withSelfRel());
        links.add(linkTo(methodOn(GiftCertificateController.class).patchGiftCertificate(giftCertificate.getId(), giftCertificate)).withRel("edit"));
        giftCertificate.add(links);
        for (Tag tag : giftCertificate.getTags()) {
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
    public static void setOrderLinks(Order order) throws EntityAlreadyExistsException, ForbiddenActionException, EntityNotFoundException {
        if (order.hasLinks()) {
            return;
        }
        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(OrderController.class).getOrder(order.getId())).withSelfRel());
        links.add(linkTo(methodOn(UserController.class).getUser(order.getUser().getId())).withRel("user"));
        links.add(linkTo(methodOn(GiftCertificateController.class).getGiftCertificate(order.getGiftCertificate().getId())).withRel("giftCertificate"));
        order.add(links);
        setUserLinks(order.getUser());
        setGiftCertificateLinks(order.getGiftCertificate());
    }

    /**
     * Sets tag links.
     *
     * @param tag the tag
     * @throws EntityAlreadyExistsException if entity already exists
     * @throws EntityNotFoundException      if entity not found
     */
    public static void setTagLinks(Tag tag) throws EntityAlreadyExistsException, EntityNotFoundException {
        if (tag.hasLinks()) {
            return;
        }
        tag.add(linkTo(methodOn(TagController.class).getTag(tag.getId())).withSelfRel());
        tag.add(linkTo(methodOn(TagController.class).deleteTag(tag.getId())).withRel("delete"));
    }

    /**
     * Sets user links.
     *
     * @param user the user
     * @throws EntityNotFoundException if entity not found
     */
    public static void setUserLinks(User user) throws EntityNotFoundException {
        if (user.hasLinks()) {
            return;
        }
        user.add(linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel());
    }
}
