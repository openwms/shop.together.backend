package io.interface21.shop2gether;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.LinkedList;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A Coordinate.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class Coordinate implements Serializable {

    public double longitude;
    public double latitude;
    public double longitudeDelta;
    public double latitudeDelta;

    public static String toPolygonString(LinkedList<Coordinate> coords) {
        StringBuilder sb = new StringBuilder("POLYGON ((");
        coords.stream().forEachOrdered(c -> sb.append(c.getLongitude()).append(" ").append(c.getLatitude()).append(", "));
        sb.append("))");
        return sb.toString();
    }
}
