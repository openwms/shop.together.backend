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
package io.interface21.shop2gether;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.ameba.exception.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * A UserController.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@RestController
class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users", params = "username")
    UserVO getUserFor(@RequestParam("username") String username) {
        Optional<UserVO> userOpt = userService.getUserByUsername(username);
        if (userOpt.isPresent()) {

            // enrich
            UserVO user = userOpt.get();
            //user.add(linkTo(methodOn(UserController.class).getUserFor(username)).withRel("items"));
            return user;
        }
        throw new NotFoundException("No User with username found", "NOTFOUND", username);
    }

    @GetMapping(value = "/users", params = "username")
    List<UserVO> getByCoordinateWindow(LinkedList<Coordinate> polygon) {
        return userService.findUsersWithin(polygon);
    }
}
