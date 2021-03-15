package com.vanderkast.neon.example.controller;

import com.vanderkast.neon.core.model.Note;
import com.vanderkast.neon.example.model.Store;
import com.vanderkast.neon.example.model.Tag;

import java.util.*;

public class StoreImpl implements Store {
    private final Map<String, List<Note>> network = new HashMap<>();
    private final Map<Note, List<String>> notesLinks = new HashMap<>();

    @Override
    public Iterable<Tag> getLinks(Note note) {
        var links = new ArrayList<Tag>();
        if (notesLinks.containsKey(note))
            notesLinks.get(note)
                    .forEach(tag -> links.add(new Tag(tag, () -> network.get(tag))));
        return links;
    }

    @Override
    public void add(Note note) {
        notesLinks.putIfAbsent(note, new ArrayList<>());
    }

    @Override
    public void add(Note note, Collection<String> tags) {
        notesLinks.put(note, new ArrayList<>(tags));
        for (String tag : tags) {
            if (network.containsKey(tag)) {
                network.get(tag).add(note);
            } else {
                var list = new ArrayList<Note>();
                list.add(note);
                network.put(tag, list);
            }
        }
    }

    @Override
    public Collection<Note> getAll() {
        return notesLinks.keySet();
    }
}
