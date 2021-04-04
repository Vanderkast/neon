package neon.plugins.tags.model;

import neon.core.model.Note;

import java.util.function.Supplier;

public interface LinkSupplier extends Supplier<Iterable<? extends Note>> {
}
