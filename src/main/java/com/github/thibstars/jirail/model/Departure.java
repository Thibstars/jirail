package com.github.thibstars.jirail.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Thibault Helsmoortel
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Departure(
        int id,

        int delay,

        String station,

        @JsonProperty("stationinfo")
        Station stationInfo,

        String time,

        String vehicle,

        @JsonProperty("vehicleinfo")
        Vehicle vehicleInfo,

        String platform,

        @JsonProperty("platforminfo")
        Platform platformInfo,

        int canceled,

        int left,

        String departureConnection,

        Occupancy occupancy
) {
}
