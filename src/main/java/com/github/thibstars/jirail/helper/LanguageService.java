package com.github.thibstars.jirail.helper;

import java.util.Set;

/**
 * The API only supports a specific set of languages. Use this service to make sure the correct ones are used.
 *
 * @author Thibault Helsmoortel
 */
public interface LanguageService {

    Set<String> getSupportedLanguages();

    String getLanguageOrFallback(String language);

}
