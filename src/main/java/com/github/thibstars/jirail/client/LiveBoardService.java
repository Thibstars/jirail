package com.github.thibstars.jirail.client;

import com.github.thibstars.jirail.model.LiveBoard;
import java.time.LocalTime;
import java.util.Optional;

/**
 * @author Thibault Helsmoortel
 */
public interface LiveBoardService {

    /**
     * Retrieves a Live Board for a given Station.
     *
     * @param id       the Station's id
     * @param language the language to use
     * @return a Live Board for a given Station
     */
    Optional<LiveBoard> getForStation(String id, String language);

    /**
     * Retrieves a Live Board for a given Station at a specified time.
     *
     * @param id        the Station's id
     * @param language  the language to use
     * @param localTime the time to use
     * @return a Live Board for a given Station at a specified time
     */
    Optional<LiveBoard> getForStation(String id, String language, LocalTime localTime);
}
