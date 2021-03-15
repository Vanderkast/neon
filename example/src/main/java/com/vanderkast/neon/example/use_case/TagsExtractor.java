package com.vanderkast.neon.example.use_case;

import java.util.Set;

public interface TagsExtractor {
    Set<String> handle(String text);
}
