package io.interface21.shop2gether;

import org.ameba.exception.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * A UserController exposes RESTful User resources.
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
    OwnerVO getUserFor(@RequestParam("username") @NotNull String username) {
        Optional<OwnerVO> userOpt = userService.getUserByUsername(username);
        if (userOpt.isPresent()) {

            // enrich
            OwnerVO user = userOpt.get();
            // todo: until this bug is not fixed we're stuck on the line above instead... https://github.com/spring-projects/spring-hateoas/issues/169
            //user.add(linkTo(methodOn(UserController.class).getUserFor(username)).withRel("items"));
            return user;
        }
        throw new NotFoundException("No User with username found", "NOTFOUND", username);
    }

    /* Hack: We use a POST here to pass complex objects. */
    @PostMapping(value = "/users")
    List<OwnerVO> getByCoordinateWindow(@RequestBody @NotNull List<Coordinate> area) {
        return userService.findUsersWithin(new LinkedList<>(area));
    }
}
