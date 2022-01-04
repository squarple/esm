package com.epam.esm.model.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * The metamodel for Order entity.
 */
@StaticMetamodel(Order.class)
public class Order_ {
    public static volatile SingularAttribute<Order, Long> id;
    public static volatile SingularAttribute<Order, User> user;
    public static volatile SingularAttribute<Order, GiftCertificate> giftCertificate;
    public static volatile SingularAttribute<Order, BigDecimal> cost;
    public static volatile SingularAttribute<Order, LocalDateTime> purchaseDate;
}
