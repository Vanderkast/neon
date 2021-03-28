package com.vanderkast.neon.example.use_case;

import com.vanderkast.neon.core.model.Note;
import com.vanderkast.neon.example.model.Store;
import com.vanderkast.neon.example.model.TextNote;
import com.vanderkast.neon.example.model.Title;

import java.util.Collection;

public class CreateTextNote_UseCase {
    private final Store store;
    private final TagsExtractor handler;

    public CreateTextNote_UseCase(Store store, TagsExtractor handler) {
        this.store = store;
        this.handler = handler;
    }

    public void create(String text) {
        store.add(new TextNote(text), handler.handle(text));
    }

    public void create(String title, Collection<Note> links){
        create(title);
        store.add(new Title(title, links));
    }
}
