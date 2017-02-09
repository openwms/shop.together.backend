package io.interface21.shop2gether;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * A UserService.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public interface UserService {

    Optional<OwnerVO> getUserByUsername(String username);

    OwnerVO getUserById(Long id);

    List<OwnerVO> findUsersWithin(LinkedList<Coordinate> area);
}
