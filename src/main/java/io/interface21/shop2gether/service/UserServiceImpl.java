package io.interface21.shop2gether.service;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;
import io.interface21.shop2gether.Coordinate;
import io.interface21.shop2gether.OwnerVO;
import io.interface21.shop2gether.UserService;
import io.interface21.shop2gether.UserVO;
import org.ameba.annotation.TxService;
import org.ameba.exception.NotFoundException;
import org.ameba.mapping.BeanMapper;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * A UserServiceImpl is a transactional Spring managed service that deals with {@link UserVO UserVO} instances.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@TxService
class UserServiceImpl implements UserService {

    private final BeanMapper mapper;
    private final Repositories.OwnerRepository ownerRepository;

    UserServiceImpl(BeanMapper mapper, Repositories.OwnerRepository ownerRepository) {
        this.mapper = mapper;
        this.ownerRepository = ownerRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<OwnerVO> getUserByUsername(String username) {
        Optional<Owner> user = ownerRepository.findByUsername(username);
        return user.isPresent() ? Optional.of(mapper.map(user.get(), OwnerVO.class)) : Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OwnerVO getUserById(Long id) {
        Owner user = ownerRepository.findOne(id);
        NotFoundException.throwIfNull(user, format("User with id %s not found", id));
        return mapper.map(user, OwnerVO.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OwnerVO> findUsersWithin(LinkedList<Coordinate> area) {
        List<com.vividsolutions.jts.geom.Coordinate> points =
                area.stream()
                        .map(coord->new com.vividsolutions.jts.geom.Coordinate(coord.getLongitude(), coord.getLatitude()))
                        .collect(Collectors.toList());
        GeometryFactory fact = new GeometryFactory();
        LinearRing linear = fact.createLinearRing(points.toArray(new com.vividsolutions.jts.geom.Coordinate[]{}));
        List<Owner> users = ownerRepository.findUsersWithin(new Polygon(linear, null, fact));
        return users == null ? Collections.emptyList() : mapper.map(users, OwnerVO.class);
    }
}
