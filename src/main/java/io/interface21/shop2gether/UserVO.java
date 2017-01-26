package io.interface21.shop2gether;

import org.ameba.http.AbstractBase;

/**
 * A UserVO is the View Object that represents an User.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public class UserVO extends AbstractBase {

    public String username;
    public String phonenumber;
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
