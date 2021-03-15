package com.vanderkast.neon.example.model;

import com.vanderkast.neon.core.Network;
import com.vanderkast.neon.core.model.Note;

import java.util.Collection;

public interface Store extends Network<Note> {
    @Override
    Iterable<Tag> getLinks(Note note);

    void add(Note note);

    void add(Note note, Collection<String> tags);

    Collection<Note> getAll();
}
