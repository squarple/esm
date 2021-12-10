package com.epam.esm.service.impl;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.persistence.builder.cert.criteria.Criteria;
import com.epam.esm.persistence.dao.GiftCertificateDao;
import com.epam.esm.persistence.exception.EntityNotFoundDaoException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDao giftCertificateDao;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    public GiftCertificate save(GiftCertificate giftCertificate) {
        giftCertificate.setId(null);
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
    public List<GiftCertificate> get(String name, String description, String tagName, String sortField, String sort) {
        List<GiftCertificate> giftCertificates;
        if (name == null &&
                description == null &&
                tagName == null &&
                sort == null &&
                sortField == null) {
            giftCertificates = giftCertificateDao.findAll();
        } else {
            giftCertificates = giftCertificateDao.find(new Criteria(
                    name,
                    description,
                    tagName,
                    (sortField == null ? Criteria.SortField.NONE : Criteria.SortField.valueOf(sortField.toUpperCase())),
                    (sort == null ? Criteria.Sort.NONE : Criteria.Sort.valueOf(sort.toUpperCase()))));
        }
        return giftCertificates;
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) throws EntityNotFoundException {
        try {
            return giftCertificateDao.update(giftCertificate);
        } catch (EntityNotFoundDaoException e) {
            throw new EntityNotFoundException(e, giftCertificate.getId());
        }
    }

    @Override
    public void delete(Long id) {
        giftCertificateDao.delete(id);
    }

    @Override
    public List<GiftCertificate> getAll() {
        return giftCertificateDao.findAll();
    }
}
