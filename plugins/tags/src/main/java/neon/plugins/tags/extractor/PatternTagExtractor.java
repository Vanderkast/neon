package neon.plugins.tags.extractor;

import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Uses regex pattern to extract tags from text
 */
public abstract class PatternTagExtractor implements TagsExtractor {

    public static PatternTagExtractor of(String pattern) {
        return new Simple(pattern);
    }

    /**
     * Just simple realisation that
     */
    public static class Simple extends PatternTagExtractor {
        private final Pattern pattern;

        public Simple(String pattern) {
            this.pattern = Pattern.compile(pattern);
        }

        @Override
        protected Pattern getPattern() {
            return pattern;
        }
    }

    @Override
    public Set<String> handle(String text) {
        return getPattern()
                .matcher(text)
                .results()
                .map(result -> result.group().substring(1))
                .collect(Collectors.toSet());
    }

    protected abstract Pattern getPattern();
}
