package com.epam.esm.persistence.dao.impl;

import com.epam.esm.model.entity.User;
import com.epam.esm.model.entity.User_;
import com.epam.esm.model.pagination.Page;
import com.epam.esm.model.pagination.PageImpl;
import com.epam.esm.model.pagination.Pageable;
import com.epam.esm.persistence.dao.UserDao;
import com.epam.esm.persistence.exception.EntityAlreadyExistsDaoException;
import com.epam.esm.persistence.exception.EntityNotFoundDaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * The UserDao repository.
 */
@Repository
@Transactional
public class UserDaoImpl extends AbstractEntityDao<User> implements UserDao {

    /**
     * Instantiates a new UserDaoImpl.
     *
     * @param entityManager the entity manager
     */
    @Autowired
    public UserDaoImpl(EntityManager entityManager) {
        super(User.class, entityManager);
    }

    @Override
    public User create(User user) throws EntityAlreadyExistsDaoException {
        if (ifUserExists(user.getName())) {
            throw new EntityAlreadyExistsDaoException();
        }
        entityManager.persist(user);
        return user;
    }

    @Override
    public User find(Long id) throws EntityNotFoundDaoException {
        User user = entityManager.find(User.class, id);
        if (user == null) {
            throw new EntityNotFoundDaoException(id);
        }
        return user;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.select(root);
        TypedQuery<User> tq = createTypedQuery(cq, pageable);
        long total = getTotalCount();
        List<User> content = tq.getResultList();
        return new PageImpl<>(content, pageable, total);
    }

    private boolean ifUserExists(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<User> fromUser = cq.from(User.class);
        cq.select(cb.count(fromUser))
                .where(cb.equal(fromUser.get(User_.name), name));
        TypedQuery<Long> tq = entityManager.createQuery(cq);
        return tq.getSingleResult() > 0;
    }
}
