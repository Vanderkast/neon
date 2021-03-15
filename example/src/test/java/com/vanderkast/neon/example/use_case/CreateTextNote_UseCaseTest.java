package com.vanderkast.neon.example.use_case;

import com.vanderkast.neon.example.model.Store;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@SuppressWarnings("unchecked")
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

    @Test
    void create_NoTags() {
        String text = "I praise the Lord, when brake the Law";
        var tags = Set.of();
        doReturn(tags).when(tagsExtractor).handle(text);

        assertThrows(CreateTextNote_UseCase.UntaggedNoteException.class,
                () -> useCase.create(text));
    }
}
