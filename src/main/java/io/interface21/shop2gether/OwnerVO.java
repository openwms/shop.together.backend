package io.interface21.shop2gether;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

/**
 * A OwnerVO is the View Object that represents an Owner.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Getter
public class OwnerVO<T extends ItemVO> extends UserVO implements Serializable {

    @JsonIgnore
    private List<T> items;

    List<T> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "OwnerVO with data from UserVO " + super.toString();
    }
}
