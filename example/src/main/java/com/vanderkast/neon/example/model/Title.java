package com.vanderkast.neon.example.model;

import com.vanderkast.neon.core.model.Category;
import com.vanderkast.neon.core.model.Note;

public class Title extends TextNote implements Category {
    private final LinkSupplier links;

    public Title(String text, LinkSupplier includedNotes) {
        super(text);
        this.links = includedNotes;
    }

    @Override
    public Iterable<? extends Note> getNotes() {
        return links.get();
    }
}
