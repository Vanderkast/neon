package com.vanderkast.neon.example.use_case;

import com.vanderkast.neon.example.model.Store;
import com.vanderkast.neon.example.model.TextNote;

public class CreateTextNote_UseCase {
    private final Store store;
    private final TagsExtractor handler;

    public CreateTextNote_UseCase(Store store, TagsExtractor handler) {
        this.store = store;
        this.handler = handler;
    }

    public void create(String text) {
        var tags = handler.handle(text);
        if(tags == null || tags.isEmpty())
            throw new UntaggedNoteException();
        store.add(new TextNote(text), tags);
    }

    public static class UntaggedNoteException extends RuntimeException {
        public UntaggedNoteException() {
            super("Current realization of network can't store notes without tags.");
        }
    }
}
