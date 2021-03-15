package com.vanderkast.neon.example.model;

import com.vanderkast.neon.core.model.Note;

import java.util.function.Supplier;

public interface LinkSupplier extends Supplier<Iterable<? extends Note>> {
}
