package com.github.thibstars.jirail.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;

/**
 * @author Thibault Helsmoortel
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Departures(

        int number,

        @JsonProperty("departure")
        Set<Departure> departures
) {

}
