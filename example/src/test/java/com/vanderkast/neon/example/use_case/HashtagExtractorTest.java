package com.vanderkast.neon.example.use_case;

import com.vanderkast.neon.example.controller.HashtagExtractor;
import org.junit.jupiter.api.Test;

import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HashtagExtractorTest {

    private final TagsExtractor handler = new HashtagExtractor();

    // run it if you want to look what types of strings extracts by pattern
    void patternTest() throws NoSuchFieldException, IllegalAccessException {
        var text = "#pattern makes from one #problem two";
        System.out.println("From text \"" + text + "\" we got these tags:");
        getPattern(handler)
                .matcher(text)
                .results()
                .map(MatchResult::group)
                .forEach(System.out::println); // PatternInTextTagsHandler after extracting tags removes # symbol: .map(result -> result.group().substring(1))
    }

    Pattern getPattern(TagsExtractor handler) throws NoSuchFieldException, IllegalAccessException {
        var field = HashtagExtractor.class.getDeclaredField("tagPattern");
        field.setAccessible(true);
        return (Pattern) field.get(handler);
    }

    @Test
    void handleOne() {
        var tag = "dolor";
        var text = "Lorem ipsum #" + tag + " sit amet";
        handler.handle(text)
                .forEach(actual -> assertEquals(tag, actual));
    }

    @Test
    void handle_Some() {
        var tags = new String[]{"hurricane", "everything", "before", "Reaching", "control"};
        var text = String.format("I was born in a #%s\n" +
                "Nothing to lose and #%s to gain\n" +
                "Ran #%s I walked\n" +
                "#%s for the top\n" +
                "Out of #%s just like a runaway train", (Object[]) tags);
        var actual = handler.handle(text);
        assertEquals(tags.length, actual.size());
        for (String expected : tags) {
            assertTrue(actual.contains(expected));
        }
    }
}
