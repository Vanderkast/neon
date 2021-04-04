package in_memory.use_case;

import neon.plugins.tags.extractor.TagsExtractor;
import neon.units.in_memory.Store;
import neon.units.in_memory.use_case.CreateTextNote_UseCase;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class CreateTextNote_UseCaseTest {
    private Store store = mock(Store.class);
    private TagsExtractor tagsExtractor = mock(TagsExtractor.class);

    private final CreateTextNote_UseCase useCase = new CreateTextNote_UseCase(store, tagsExtractor);

    @Test
    void create_Normal() {
        // we don't really need a hashtags, but to be more concrete
        String text = "I praise the #Lord, when brake the #law";
        var tags = Set.of("Lord", "law");
        doReturn(tags).when(tagsExtractor).handle(text);
    }
}
