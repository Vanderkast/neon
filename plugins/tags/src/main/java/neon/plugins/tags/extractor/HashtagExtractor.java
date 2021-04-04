package neon.plugins.tags.extractor;

import java.util.regex.Pattern;

/**
 * <p>Extracts hashtags from given text.</p>
 * <p>Example:
 * <p>text - "hello, #World! It's #good day for come #code!"</p>
 * <p>result - {"World", "good", "code"}</p>
 * </p>
 */
public class HashtagExtractor extends PatternTagExtractor {
    private static final Pattern tagPattern = Pattern.compile("#[\\w\\d]+");

    @Override
    protected Pattern getPattern() {
        return tagPattern;
    }
}
