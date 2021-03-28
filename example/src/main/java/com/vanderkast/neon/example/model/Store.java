package com.vanderkast.neon.example.model;

import com.vanderkast.neon.core.Network;
import com.vanderkast.neon.core.model.Category;
import com.vanderkast.neon.core.model.Link;
import com.vanderkast.neon.core.model.Note;

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
