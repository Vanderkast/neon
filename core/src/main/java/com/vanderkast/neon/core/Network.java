package com.vanderkast.neon.core;

import com.vanderkast.neon.core.model.Link;
import com.vanderkast.neon.core.model.Note;

public interface Network<T extends Note> {
    Iterable<? extends Link> getLinks(T note);
}
