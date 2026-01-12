package com.github.thibstars.jirail.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Thibault Helsmoortel
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record StationInfo(
        String locationX,

        String locationY,

        String id,

        String name,

        @JsonProperty("standardname")
        String standardName
) {

}
