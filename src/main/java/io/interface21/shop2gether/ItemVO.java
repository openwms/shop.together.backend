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

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.ameba.http.AbstractBase;

/**
 * A ItemVO.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "_type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TextNoteVO.class, name = "tnote")
})
public abstract class ItemVO extends AbstractBase implements Serializable {

    private Long persistentKey;
    public boolean shareable;
    public long version;
    @JsonIgnore
    public List<UserGroupVO> sharedWith;

    public Long getPersistentKey() {
        return persistentKey;
    }

    public List<UserGroupVO> getSharedWith() {
        return sharedWith;
    }

    @Override
    public String toString() {
        return "ItemVO{" +
                "persistentKey=" + persistentKey +
                ", shareable=" + shareable +
                ", sharedWith=" + sharedWith +
                "} " + super.toString();
    }
}
