package com.github.thibstars.jirail.client;

import com.github.thibstars.jirail.model.Vehicle;
import java.util.Optional;

/**
 * @author Thibault Helsmoortel
 */
public interface VehicleService {

    /**
     * Retrieves information about a vehicle.
     *
     * @param id       the Vehicle's id
     * @param language the language to use
     * @return information about a vehicle
     */
    Optional<Vehicle> getVehicle(String id, String language);
}
