package neon.plugins.tags.model;

import neon.core.model.Category;
import neon.core.model.Note;

import java.util.Collection;

public class Title extends TextNote implements Category {
    private final Collection<Note> included;

    public Title(String text, Collection<Note> included) {
        super(text);
        this.included = included;
    }

    @Override
    public Iterable<? extends Note> getNotes() {
        return included;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Title title = (Title) o;
        return included.equals(title.included);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
