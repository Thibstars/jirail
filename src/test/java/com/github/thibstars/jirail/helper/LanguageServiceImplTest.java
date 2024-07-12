package com.github.thibstars.jirail.helper;


import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Thibault Helsmoortel
 */
class LanguageServiceImplTest {

    private LanguageServiceImpl languageService;

    @BeforeEach
    void setUp() {
        this.languageService = new LanguageServiceImpl();
    }

    @Test
    void shouldGetSupportedLanguages() {
        Assertions.assertEquals(
                Set.of("en", "nl", "fr", "de"),
                languageService.getSupportedLanguages(),
                "Supported languages must be: en, nl, fr, de."
        );
    }

    @Test
    void shouldGetLanguageOrFallback() {
        Assertions.assertEquals(
                "nl",
                languageService.getLanguageOrFallback("nl"),
                "Getting a supported language must return the same language."
        );

        Assertions.assertEquals(
                "en",
                languageService.getLanguageOrFallback("notASupportedLanguage"),
                "Getting a unsupported language must return the fallback language."
        );

        Assertions.assertEquals(
                "en",
                languageService.getLanguageOrFallback(null),
                "Getting null as a language must return the fallback language."
        );
    }
}