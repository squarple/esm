package com.epam.esm.persistence.dao.impl;

import com.epam.esm.model.entity.User;
import com.epam.esm.model.entity.User_;
import com.epam.esm.model.pagination.Pageable;
import com.epam.esm.persistence.config.TestPersistenceConfig;
import com.epam.esm.persistence.dao.UserDao;
import com.epam.esm.persistence.exception.EntityAlreadyExistsDaoException;
import com.epam.esm.persistence.exception.EntityNotFoundDaoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "test")
@EntityScan("com.epam.esm")
@SpringBootTest(classes = TestPersistenceConfig.class)
@Transactional
class UserDaoImplTest {
    @Autowired
    private UserDao userDao;

    @Autowired
    private EntityManager entityManager;

    @Test
    void create_Successful() throws EntityAlreadyExistsDaoException {
        User user = new User();
        user.setName("user");
        userDao.create(user);
        assertNotNull(user);
        assertNotNull(user.getId());
        deleteUser(user.getId());
    }

    @Test
    void findUserById_ReturnFoundedUser() throws EntityNotFoundDaoException, EntityAlreadyExistsDaoException {
        User expectedUser = new User();
        expectedUser.setName("name");
        userDao.create(expectedUser);
        User actualUser = userDao.find(expectedUser.getId());
        assertEquals(expectedUser, actualUser);
        deleteUser(actualUser.getId());
    }

    @Test
    void findAll_ReturnListOfUsers() throws EntityAlreadyExistsDaoException {
        Pageable pageable = TestUtil.getPageable();
        List<User> users = TestUtil.getUserList(2);
        for (User user : users) {
            userDao.create(user);
        }
        List<User> actualUsers = userDao.findAll(pageable).getContent();
        assertEquals(users.stream().collect(Collectors.toSet()),
                actualUsers.stream().collect(Collectors.toSet()));
    }

    void deleteUser(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<User> cd = cb.createCriteriaDelete(User.class);
        Root<User> fromUser = cd.from(User.class);
        cd.where(cb.equal(fromUser.get(User_.id), id));
        entityManager.createQuery(cd).executeUpdate();
    }
}
