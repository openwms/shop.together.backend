package io.interface21.shop2gether;

import java.io.Serializable;

/**
 * A TextNoteVO is the View Object that represents a TextNote.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public class TextNoteVO extends ItemVO implements Serializable {

    public String title;
    public String text;
    public String color;
    public boolean pinned;

    @Override
    public String toString() {
        return "TextNoteVO{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", color='" + color + '\'' +
                ", pinned=" + pinned +
                "} " + super.toString();
    }
}
