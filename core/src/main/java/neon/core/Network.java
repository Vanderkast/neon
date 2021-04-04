package neon.core;

import neon.core.model.Link;
import neon.core.model.Note;

public interface Network<T extends Note> {
    Iterable<? extends Link> getLinks(T note);
}
