package com.epam.esm.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The Order entity.
 */
@Slf4j
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private GiftCertificate giftCertificate;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    /**
     * On pre persist action.
     */
    @PrePersist
    public void onPrePersist() {
        log.info("{}: insert new order", LocalDateTime.now());
    }

    /**
     * On pre update action.
     */
    @PreUpdate
    public void onPreUpdate() {
        log.info("{}: update order with id={}", LocalDateTime.now(), this.id);
    }

    /**
     * On pre remove action.
     */
    @PreRemove
    public void onPreRemove() {
        log.info("{}: delete order with id={}", LocalDateTime.now(), this.id);
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
