package com.vanderkast.neon.example.controller;

import com.vanderkast.neon.core.model.Link;
import com.vanderkast.neon.core.model.Note;
import com.vanderkast.neon.example.model.Store;
import com.vanderkast.neon.example.model.Tag;
import com.vanderkast.neon.example.model.Title;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
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
        actualLinks.forEach(link -> {
            assertTrue(link instanceof Tag);
            assertTrue(tags.contains(((Tag) link).getName()));
        });
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

        for (Link link : store.getLinks(first)) {
            assertTrue(link instanceof Tag);
            if (((Tag) link).getName().equals(sharedTag)) {
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

        for (Link tag : store.getLinks(note1)) {
            if (linkIsTagOfName(tag, sharedA)) {
                for (Note note : tag.getNotes()) {
                    if (note.equals(note2)) {
                        for (Link tag2 : store.getLinks(note)) {
                            if (linkIsTagOfName(tag2, sharedC)) {
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

    private boolean linkIsTagOfName(Link link, String name) {
        return link instanceof Tag
                && ((Tag) link).getName().equals(name);
    }

    public boolean contains(Note note, Iterable<Link> links) {
        for (Link link : links) {
            if(contains(note, link.getNotes()))
                return true;
        }
        return false;
    }

    public boolean containsLink(Link expected, Iterable<Link> links) {
        var found = false;
        for (Link link : links)
            found = expected.equals(link);
        return found;
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

    @Test
    void title() {
        var note = mock(Note.class);
        store.add(note);

        var category = new Title("Category", List.of(note));
        store.add(category);

        var categories = store.getAllCategories();
        assertNotNull(categories);
        assertEquals(1, categories.size());
        assertTrue(categories.contains(category));

        var network = store.getAll();
        assertNotNull(network);
        assertEquals(2, network.size());
        assertTrue(network.contains(note));
        assertTrue(network.contains(category));

        assertEquals(category, store.getLinks(note).iterator().next());
        assertTrue(containsLink(category, store.getLinks(note)));
    }

    @Test
    void deleteWithLinked() {
        var sharedTag = "A";

        var first = mock(Note.class);
        var firstTags = Set.of(sharedTag, "B");
        store.add(first, firstTags);

        var second = mock(Note.class);
        var secondTags = Set.of(sharedTag, "C");
        store.add(second, secondTags);

        assertTrue(containsAll(store.getAll(), first, second)); // checks that store has all saved tags
        assertTrue(contains(second, store.getLinks(first))); // second is linked with first

        store.delete(first);

        // now network should contain only second
        var all = store.getAll();
        assertTrue(all.contains(second));
        assertFalse(all.contains(first));
        assertFalse(contains(first, store.getLinks(second))); // second is no longer linked with first
    }

    @Test
    void deleteLinkedCategory() {
        var note = mock(Note.class);
        store.add(note);

        var category = new Title("The Hobbit", List.of(note));
        store.add(category);

        assertTrue(containsAll(store.getAll(), note, category));
        assertTrue(containsLink(category, store.getLinks(note)));

        store.delete(category);

        var all = store.getAll();
        assertTrue(all.contains(note));
        assertFalse(all.contains(category));
        assertFalse(containsLink(category, store.getLinks(note)));
    }

    @SafeVarargs
    public final <T> boolean containsAll(Collection<T> where, T... whats) {
        for (T what : whats) {
            if (!where.contains(what))
                return false;
        }
        return true;
    }
}
