package com.vanderkast.neon.example.controller;

import com.vanderkast.neon.example.use_case.TagsExtractor;

import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HashtagExtractor implements TagsExtractor {
    private static final Pattern tagPattern = Pattern.compile("#[\\w\\d]+");

    /**
     * <p>Extracts tag names from given text.</p>
     * <p>Example:
     * <p>text - "hello, #World! It's #good day for come #code!"</p>
     * <p>result - {"World", "good", "code"}</p>
     * </p>
     *
     * @param text of a note.
     * @return Set of tag names
     */
    @Override
    public Set<String> handle(String text) {
        return tagPattern
                .matcher(text)
                .results()
                .map(result -> result.group().substring(1))
                .collect(Collectors.toSet());
    }
}
