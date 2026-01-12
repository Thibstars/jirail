package com.github.thibstars.jirail.model;

import ch.qos.logback.classic.spi.PlatformInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Thibault Helsmoortel
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Stop(
        int id,
        String station,
        @JsonProperty("stationinfo")
        StationInfo stationInfo,
        long time,
        long delay,
        int platform,
        @JsonProperty("platforminfo")
        PlatformInfo platformInfo,
        int canceled,
        long departureDelay,
        int departureCanceled,
        long scheduledDepartureTime,
        long arrivalDelay,
        int arrivalCanceled,
        int isExtraStop,
        long scheduledArrivalTime,
        String departureConnection,
        Occupancy occupancy
) {

}
