package com.github.thibstars.jirail.client;

import com.github.thibstars.jirail.model.Disturbances;
import java.util.Optional;

/**
 * @author Thibault Helsmoortel
 */
public interface DisturbancesService {

    /**
     * Retrieves the current disturbances from NMBS/SNCB.
     *
     * @return an Optional containing the Disturbances object if available, otherwise an empty Optional
     */
    Optional<Disturbances> getDisturbances(String language);
}
