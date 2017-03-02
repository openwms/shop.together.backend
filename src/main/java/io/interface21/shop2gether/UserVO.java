package io.interface21.shop2gether;

import lombok.Getter;
import org.ameba.http.AbstractBase;

/**
 * A UserVO is the View Object that represents an User.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Getter
public class UserVO extends AbstractBase {

    public String username, phonenumber, persistentKey;
    public Coordinate home;

    @Override
    public String toString() {
        return "UserVO{" +
                "username='" + username + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", home=" + home +
                "} " + super.toString();
    }
}
