package neon.units.in_memory.use_case;

import neon.core.model.Note;
import neon.plugins.tags.extractor.TagsExtractor;
import neon.plugins.tags.model.TextNote;
import neon.plugins.tags.model.Title;
import neon.units.in_memory.Store;

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
