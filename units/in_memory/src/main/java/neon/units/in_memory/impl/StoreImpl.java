package neon.units.in_memory.impl;

import neon.core.model.Category;
import neon.core.model.Link;
import neon.core.model.Note;
import neon.plugins.tags.model.Tag;
import neon.units.in_memory.Store;

import java.util.*;

public class StoreImpl implements Store {
    private final Map<String, List<Note>> network;
    private final Map<Note, List<String>> notesLinks;
    private final Map<Note, List<Category>> notesCategories;
    private final List<Category> categories;

    public StoreImpl(Map<String, List<Note>> network,
                     Map<Note, List<String>> notesLinks,
                     Map<Note, List<Category>> notesCategories,
                     List<Category> categories) {
        this.network = network;
        this.notesLinks = notesLinks;
        this.notesCategories = notesCategories;
        this.categories = categories;
    }

    public StoreImpl() {
        network = new HashMap<>();
        notesLinks = new HashMap<>();
        notesCategories = new HashMap<>();
        categories = new ArrayList<>();
    }

    @Override
    public Iterable<Link> getLinks(Note note) {
        var links = new ArrayList<Link>();
        if (notesLinks.containsKey(note))
            notesLinks.get(note)
                    .forEach(tag -> links.add(new Tag(tag, () -> network.get(tag))));
        if (notesCategories.containsKey(note))
            links.addAll(notesCategories.get(note));
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
    public void add(Category category) {
        add((Note) category);
        categories.add(category);
        category.getNotes().forEach(note -> {
            if (notesCategories.containsKey(note))
                notesCategories.get(note).add(category);
            else {
                var list = new ArrayList<Category>();
                list.add(category);
                notesCategories.put(note, list);
            }
        });
    }

    @Override
    public Collection<Note> getAll() {
        return notesLinks.keySet();
    }

    @Override
    public Collection<Category> getAllCategories() {
        return Collections.unmodifiableCollection(categories);
    }

    /**
     * <h3>Deletes passed note from network.</h3>
     * <p>If note is Category calls deleteCategory method.</p>
     * <p>Removes note from it's categories and from notesCategories mapping.</p>
     * <p>Removes note from it's links.</p>
     * <p>Removes note from notesLinks mapping.</p>
     */
    @Override
    public void delete(Note note) {
        if (note instanceof Category)
            deleteCategory((Category) note);
        remove(notesCategories.get(note), note);
        notesCategories.remove(note);
        cleanLinkedTags(note);
        notesLinks.remove(note);
    }

    /**
     * <h>Deletes category.</h>
     * <p>Removes category from categories list.</p>
     * <p>Removes category from it's included notes' links.</p>
     * <p>DOESN'T deletes category as note (That's why this method is not public).</p>
     */
    private void deleteCategory(Category category) {
        category.getNotes().forEach(n -> {
            cleanLinkedTags(n);
            remove(notesCategories.get(n), category);
        });
        categories.remove(category);
    }

    /**
     * Deletes passed note from all tag links.
     */
    private void cleanLinkedTags(Note note) {
        notesLinks.get(note)
                .forEach(link -> remove(network.get(link), note));
    }

    /**
     * Just removes t from list of ts, if list is not null or empty
     */
    private <T> void remove(List<? extends T> list, T t) {
        if (list == null || list.isEmpty())
            return;
        list.remove(t);
    }
}
