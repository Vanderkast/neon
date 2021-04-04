package neon.plugins.tags.extractor;

import java.util.Set;

public interface TagsExtractor {
    Set<String> handle(String text);
}
