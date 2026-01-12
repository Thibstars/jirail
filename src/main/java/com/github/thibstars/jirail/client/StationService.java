package com.github.thibstars.jirail.client;

import com.github.thibstars.jirail.model.StationInfo;
import java.util.Set;

/**
 * @author Thibault Helsmoortel
 */
public interface StationService {

    /**
     * Retrieves a set of all available Stations.
     *
     * @return a set of all available Stations
     */
    Set<StationInfo> getStations(String language);

}
