package neon.units.in_memory;

import neon.core.Network;
import neon.core.model.Category;
import neon.core.model.Link;
import neon.core.model.Note;

import java.util.Collection;

public interface Store extends Network<Note> {
    @Override
    Iterable<Link> getLinks(Note note);

    void add(Note note);

    void add(Note note, Collection<String> tags);

    void add(Category category);

    Collection<Note> getAll();

    Collection<Category> getAllCategories();

    void delete(Note note);
}
