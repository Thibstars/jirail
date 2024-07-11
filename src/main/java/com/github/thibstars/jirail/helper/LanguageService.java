package com.github.thibstars.jirail.helper;

import java.util.Set;

/**
 * @author Thibault Helsmoortel
 */
public interface LanguageService {

    Set<String> getSupportedLanguages();

    String getLanguageOrFallback(String language);

}
