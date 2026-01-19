package com.github.thibstars.jirail.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Thibault Helsmoortel
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Disturbance(
        String id,
        String title,
        String description,
        String link,
        String type,
        String timestamp
) {

}
