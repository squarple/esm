package com.epam.esm.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The Order entity.
 */
@Entity
@Table(name = "orders")
public class Order extends RepresentationModel<Order> {
    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(Order.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    private GiftCertificate giftCertificate;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets gift certificate.
     *
     * @return the gift certificate
     */
    public GiftCertificate getGiftCertificate() {
        return giftCertificate;
    }

    /**
     * Sets gift certificate.
     *
     * @param giftCertificate the gift certificate
     */
    public void setGiftCertificate(GiftCertificate giftCertificate) {
        this.giftCertificate = giftCertificate;
    }

    /**
     * Gets cost.
     *
     * @return the cost
     */
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * Sets cost.
     *
     * @param cost the cost
     */
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    /**
     * Gets purchase date.
     *
     * @return the purchase date
     */
    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * Sets purchase date.
     *
     * @param purchaseDate the purchase date
     */
    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    /**
     * On pre persist action.
     */
    @PrePersist
    public void onPrePersist() {
        logger.info("{}: insert new order", LocalDateTime.now());
    }

    /**
     * On pre update action.
     */
    @PreUpdate
    public void onPreUpdate() {
        logger.info("{}: update order with id={}", LocalDateTime.now(), this.id);
    }

    /**
     * On pre remove action.
     */
    @PreRemove
    public void onPreRemove() {
        logger.info("{}: delete order with id={}", LocalDateTime.now(), this.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Order order = (Order) o;
        return getId().equals(order.getId()) &&
                getUser().equals(order.getUser()) &&
                getGiftCertificate().equals(order.getGiftCertificate()) &&
                getCost().equals(order.getCost()) &&
                getPurchaseDate().equals(order.getPurchaseDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getGiftCertificate(), getCost(), getPurchaseDate());
    }
}
