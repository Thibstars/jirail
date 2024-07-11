package com.github.thibstars.jirail.helper;

import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Thibault Helsmoortel
 */
public class LanguageServiceImpl implements LanguageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LanguageServiceImpl.class);

    private static final Set<String> SUPPORTED_LANGS = Set.of("en", "nl", "fr", "de");

    @Override
    public Set<String> getSupportedLanguages() {
        return SUPPORTED_LANGS;
    }

    @Override
    public String getLanguageOrFallback(String language) {
        Optional<String> optionalLanguage = SUPPORTED_LANGS.stream()
                .filter(lang -> lang.equals(language))
                .findFirst();

        String fallbackLanguage = SUPPORTED_LANGS.stream().findFirst().orElseThrow();
        if (optionalLanguage.isEmpty()) {
            LOGGER.warn("Language {} is not supported, using {} as a fallback.", language, fallbackLanguage);
        }

        return optionalLanguage.orElse(fallbackLanguage);
    }
}
