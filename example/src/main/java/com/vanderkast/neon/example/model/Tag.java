package com.vanderkast.neon.example.model;

import com.vanderkast.neon.core.model.Link;
import com.vanderkast.neon.core.model.Note;

public class Tag implements Link {
    private final String name;
    private final LinkSupplier links;

    public Tag(String name, LinkSupplier links) {
        assert name != null;

        this.name = name;
        this.links = links;
    }

    @Override
    public Iterable<? extends Note> getNotes() {
        return links.get();
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return name.equals(((Tag) o).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
