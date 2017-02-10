package io.interface21.shop2gether;

import org.ameba.http.AbstractBase;

import java.io.Serializable;

/**
 * A UserGroupVO is the View Object that represents an UserGroup.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public class UserGroupVO extends AbstractBase implements Serializable {

    private Long pk;

    public Long getPk() {
        return pk;
    }
}
