/*
 * Shop2gether Backend.
 *
 * This software module including the design and software principals used is and remains
 * the property of Heiko Scherrer (the initial author of the project)
 * and is submitted with the understanding that it is not to be reproduced nor copied in
 * whole or in part, nor licensed or otherwise provided or communicated to any third party
 * without their prior written consent. It must not be used in any way detrimental to the
 * interests of both authors. Acceptance of this module will be construed as an agreement
 * to the above.
 *
 * All rights of Heiko Scherrer remain reserved. Shop2gether Backend
 * is a registered trademark of Heiko Scherrer. Other products and
 * company names mentioned herein may be trademarks or trade names of their respective owners.
 * Specifications are subject to change without notice.
 */
package io.interface21.shop2gether.service;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import io.interface21.shop2gether.Coordinate;
import io.interface21.shop2gether.UserService;
import io.interface21.shop2gether.UserVO;
import org.ameba.annotation.TxService;
import org.ameba.exception.NotFoundException;
import org.ameba.mapping.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;

/**
 * A UserServiceImpl is a transactional Spring managed service that deals with {@link UserVO UserVO} instances.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@TxService
class UserServiceImpl implements UserService {

    private final BeanMapper mapper;
    private final UserRepository userRepository;

    UserServiceImpl(BeanMapper mapper, UserRepository userRepository) {
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<UserVO> getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent() ? Optional.of(mapper.map(user.get(), UserVO.class)) : Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserVO getUserById(Long id) {
        User user = userRepository.findOne(id);
        NotFoundException.throwIfNull(user, String.format("User with id %s not found", id));
        return mapper.map(user, UserVO.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserVO> findUsersWithin(LinkedList<Coordinate> area) {
        List<Point> points = area.stream().map(coord->new Point(coord.getLongitude(), coord.getLatitude())).collect(Collectors.toList());
        Polygon polygon = new Polygon(points);
        List<User> users = userRepository.findUsersWithin(polygon);
        return users == null ? Collections.emptyList() : mapper.map(users, UserVO.class);
    }
}
