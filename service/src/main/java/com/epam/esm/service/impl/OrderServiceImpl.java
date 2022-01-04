package com.epam.esm.service.impl;

import com.epam.esm.model.entity.Order;
import com.epam.esm.model.pagination.Page;
import com.epam.esm.model.pagination.Pageable;
import com.epam.esm.persistence.dao.OrderDao;
import com.epam.esm.persistence.exception.EntityNotFoundDaoException;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Order service.
 */
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;

    /**
     * Instantiates a new OrderService.
     *
     * @param orderDao the order dao
     */
    @Autowired
    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    @Transactional
    public Order save(Long userId, Long giftCertificateId) throws EntityNotFoundException {
        try {
            return orderDao.create(userId, giftCertificateId);
        } catch (EntityNotFoundDaoException e) {
            throw new EntityNotFoundException(e, e.getEntityId());
        }
    }

    @Override
    public Order get(Long id) throws EntityNotFoundException {
        try {
            return orderDao.find(id);
        } catch (EntityNotFoundDaoException e) {
            throw new EntityNotFoundException(e, e.getEntityId());
        }
    }

    @Override
    public Page<Order> getAll(Pageable pageable) throws ResourceNotFoundException {
        Page<Order> page = orderDao.findAll(pageable);
        if (pageable.getPageNumber() > page.getTotalPages()) {
            throw new ResourceNotFoundException();
        }
        return page;
    }

    @Override
    public Page<Order> getByUserId(Long id, Pageable pageable) throws ResourceNotFoundException {
        Page<Order> page = orderDao.findByUserId(id, pageable);
        if (pageable.getPageNumber() > page.getTotalPages()) {
            throw new ResourceNotFoundException();
        }
        return page;
    }
}
