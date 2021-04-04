package neon.units.server.config;

import neon.plugins.tags.extractor.HashtagExtractor;
import neon.units.in_memory.Store;
import neon.units.in_memory.impl.StoreImpl;
import neon.units.in_memory.use_case.CreateTextNote_UseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    Store beanStore() {
        return new StoreImpl();
    }

    @Bean
    CreateTextNote_UseCase beanCreateTextNote_UseCase(Store store) {
        return new CreateTextNote_UseCase(store, new HashtagExtractor());
    }
}
