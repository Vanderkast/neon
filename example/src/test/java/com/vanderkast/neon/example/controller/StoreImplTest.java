package com.vanderkast.neon.example.controller;

import com.vanderkast.neon.core.model.Note;
import com.vanderkast.neon.example.model.Store;
import com.vanderkast.neon.example.model.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.mock;

public class StoreImplTest {
    private final Store store = new StoreImpl();

    @Test
    void addOne_GetIt() {
        var note = mock(Note.class);
        store.add(note);

        var actual = store.getAll();
        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertTrue(actual.contains(note));
    }

    @Test
    void addOneTagged_GetIt() {
        var note = mock(Note.class);
        var tags = Set.of("A", "B");
        store.add(note, tags);

        var actual = store.getAll();
        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertTrue(actual.contains(note));

        var actualLinks = store.getLinks(note);
        actualLinks.forEach(link -> assertTrue(tags.contains(link.getName())));
    }

    @Test
    void addSome_GetThem() {
        var size = (int) (Math.random() * 10) + 3;
        var notes = new ArrayList<Note>();
        for (int i = 0; i < size; i++)
            notes.add(mock(Note.class));

        notes.forEach(store::add);

        var actual = store.getAll();
        assertNotNull(actual);
        assertEquals(size, actual.size());
        notes.forEach(n -> assertTrue(actual.contains(n)));
    }

    @Test
    void addTwoLinked() {
        var sharedTag = "A";

        var first = mock(Note.class);
        var firstTags = Set.of(sharedTag, "B");

        var second = mock(Note.class);
        var secondTags = Set.of(sharedTag, "C");

        store.add(first, firstTags);
        store.add(second, secondTags);

        for (Tag link : store.getLinks(first)) {
            if (link.getName().equals(sharedTag)) {
                assertTrue(contains(second, link.getNotes()));
            }
        }
    }

    /**
     * Note 1 linked with 2, and note 2 linked with 3
     */
    @Test
    void transitive() {
        var sharedA = "A";

        var note1 = mock(Note.class);
        var tags1 = Set.of(sharedA, "B");
        store.add(note1, tags1);

        var sharedC = "C";

        var note2 = mock(Note.class);
        var tags2 = Set.of(sharedA, sharedC);
        store.add(note2, tags2);

        var note3 = mock(Note.class);
        var tags3 = Set.of(sharedC, "D");
        store.add(note3, tags3);

        for (Tag tag : store.getLinks(note1)) {
            if(tag.getName().equals(sharedA)) {
                for (Note note : tag.getNotes()) {
                    if(note.equals(note2)) {
                        for(Tag tag2 : store.getLinks(note)) {
                            if(tag2.getName().equals(sharedC)) {
                                assertTrue(contains(note3, tag2.getNotes()));
                                return;
                            }
                        }
                    }
                }
            }
        }
        fail();
    }

    public boolean contains(Object what, Iterable<?> where) {
        for (Object o : where) {
            if (o instanceof Iterable) {
                var found = contains(what, (Iterable<?>) o);
                if (found)
                    return true;
            }
            if (o.equals(what))
                return true;
        }
        return false;
    }
}
