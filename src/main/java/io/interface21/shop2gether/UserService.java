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

    Optional<UserVO> getUserByUsername(String username);

    UserVO getUserById(Long id);

    List<UserVO> findUsersWithin(LinkedList<Coordinate> area);
}
