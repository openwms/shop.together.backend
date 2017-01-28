package io.interface21.shop2gether.service;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A TextNote.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "T_TEXT_NOTE")
@DiscriminatorValue("TNOTE")
class TextNote extends Item {

    @Column(name = "C_TITLE")
    private String title;
    @Column(name = "C_TEXT")
    private String text;
    @Column(name = "C_COLOR")
    private String color;
    @Column(name = "C_PINNED")
    private boolean pinned = false;

    public TextNote(String title, String text, String color, boolean pinned) {
        super();
        this.title = title;
        this.text = text;
        this.color = color;
        this.pinned = pinned;
    }

    @Override
    public void copyFrom(Item toSave) {
        super.copyFrom(toSave);
        if (toSave instanceof TextNote) {
            this.title = ((TextNote)toSave).title;
            this.text = ((TextNote)toSave).text;
            this.color = ((TextNote)toSave).color;
            this.pinned = ((TextNote)toSave).pinned;
        }
    }

    @Override
    public String toString() {
        return "TextNote{" +
                "title='" + title + '\'' +
                ", color='" + color + '\'' +
                ", pinned=" + pinned +
                "} " + super.toString();
    }
}
