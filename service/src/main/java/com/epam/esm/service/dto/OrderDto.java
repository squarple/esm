package com.epam.esm.service.dto;

import com.epam.esm.model.entity.Order;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDto extends RepresentationModel<OrderDto>  {
    private Long id;
    private UserDto userDto;
    private GiftCertificateDto giftCertificateDto;
    private BigDecimal cost;
    private LocalDateTime purchaseDate;

    public Order toOrder() {
        Order order = new Order();
        order.setId(id);
        order.setUser(userDto.toUser());
        order.setGiftCertificate(giftCertificateDto.toGiftCertificate());
        order.setCost(cost);
        order.setPurchaseDate(purchaseDate);
        return order;
    }

    public static OrderDto fromOrder(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setUserDto(UserDto.fromUser(order.getUser()));
        orderDto.setGiftCertificateDto(GiftCertificateDto.fromGiftCertificate(order.getGiftCertificate()));
        orderDto.setCost(order.getCost());
        orderDto.setPurchaseDate(order.getPurchaseDate());
        return orderDto;
    }
}
