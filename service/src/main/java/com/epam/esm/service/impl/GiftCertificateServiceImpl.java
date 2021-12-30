package com.epam.esm.service.impl;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.pagination.Page;
import com.epam.esm.model.pagination.Pageable;
import com.epam.esm.persistence.dao.GiftCertificateDao;
import com.epam.esm.persistence.dao.criteria.cert.Criteria;
import com.epam.esm.persistence.exception.EntityNotFoundDaoException;
import com.epam.esm.persistence.exception.ForbiddenActionException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The GiftCertificate service.
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDao giftCertificateDao;

    /**
     * Instantiates a new GiftCertificateService.
     *
     * @param giftCertificateDao the gift certificate dao
     */
    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    @Transactional
    public GiftCertificate save(GiftCertificate giftCertificate) {
        giftCertificate.setId(null);
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        giftCertificate = giftCertificateDao.create(giftCertificate);
        return giftCertificate;
    }

    @Override
    public GiftCertificate get(Long id) throws EntityNotFoundException {
        try {
            return giftCertificateDao.find(id);
        } catch (EntityNotFoundDaoException e) {
            throw new EntityNotFoundException(e, id);
        }
    }

    @Override
    public Page<GiftCertificate> get(String name, String description, List<String> tagNames, String sortField, String sort, Pageable pageable) throws ResourceNotFoundException {
        Page<GiftCertificate> page;
        if (name == null &&
                description == null &&
                tagNames == null &&
                sort == null &&
                sortField == null) {
            page = giftCertificateDao.findAll(pageable);
        } else {
            if (tagNames == null) {
                tagNames = new ArrayList<>();
            } else {
                tagNames.removeIf(String::isEmpty);
            }
            Criteria criteria = new Criteria(
                    name != null ? (name.isEmpty() ? null : name) : null,
                    description != null ? (description.isEmpty() ? null : description) : null,
                    tagNames,
                    (sortField == null ? Criteria.SortField.NONE : Criteria.SortField.valueOf(sortField.toUpperCase())),
                    (sort == null ? Criteria.Sort.NONE : Criteria.Sort.valueOf(sort.toUpperCase())));
            page = giftCertificateDao.find(criteria, pageable);
        }
        if (pageable.getPageNumber() > page.getTotalPages()) {
            throw new ResourceNotFoundException();
        }
        return page;
    }

    @Transactional
    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) throws EntityNotFoundException {
        try {
            giftCertificate.setLastUpdateDate(LocalDateTime.now());
            return giftCertificateDao.update(giftCertificate);
        } catch (EntityNotFoundDaoException e) {
            throw new EntityNotFoundException(e, giftCertificate.getId());
        }
    }

    @Transactional
    @Override
    public void delete(Long id) throws ForbiddenActionException, EntityNotFoundException {
        try {
            giftCertificateDao.delete(id);
        } catch (EntityNotFoundDaoException e) {
            throw new EntityNotFoundException(e.getEntityId());
        }
    }

    @Override
    public Page<GiftCertificate> getAll(Pageable pageable) throws ResourceNotFoundException {
        Page<GiftCertificate> page = giftCertificateDao.findAll(pageable);
        if (pageable.getPageNumber() > page.getTotalPages()) {
            throw new ResourceNotFoundException();
        }
        return page;
    }

    @Override
    public boolean isPossibleToDelete(Long id) {
        return giftCertificateDao.isPossibleToDelete(id);
    }
}
