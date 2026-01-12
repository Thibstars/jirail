package com.github.thibstars.jirail.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Thibault Helsmoortel
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record VehicleInfo(
        String name,

        @JsonProperty("shortname")
        String shortName,

        String locationX,

        String locationY
) {

}
