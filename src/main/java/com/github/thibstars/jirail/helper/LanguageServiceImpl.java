package com.github.thibstars.jirail.helper;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.SequencedSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Thibault Helsmoortel
 */
public class LanguageServiceImpl implements LanguageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LanguageServiceImpl.class);

    private static final SequencedSet<String> SUPPORTED_LANGS = new LinkedHashSet<>();

    static {
        SUPPORTED_LANGS.add("en");
        SUPPORTED_LANGS.add("nl");
        SUPPORTED_LANGS.add("fr");
        SUPPORTED_LANGS.add("de");
    }

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
