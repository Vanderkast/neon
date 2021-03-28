package com.vanderkast.neon.example.model;

import com.vanderkast.neon.core.model.Note;

public class TextNote implements Note {
    private final String text;

    public TextNote(String text) {
        assert text != null;

        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return text.equals(((TextNote) o).text);
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }

    @Override
    public String toString() {
        return text;
    }
}
