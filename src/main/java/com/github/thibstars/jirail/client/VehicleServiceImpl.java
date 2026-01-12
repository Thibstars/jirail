package com.github.thibstars.jirail.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.thibstars.jirail.exceptions.ClientException;
import com.github.thibstars.jirail.helper.LanguageService;
import com.github.thibstars.jirail.model.Vehicle;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Thibault Helsmoortel
 */
public class VehicleServiceImpl implements VehicleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleServiceImpl.class);

    private static final String ID_PLACEHOLDER = "${id}";

    private static final String LANG_PLACEHOLDER = "${lang}";

    private static final String URL = "https://api.irail.be/vehicle=" + ID_PLACEHOLDER + "&format=json&lang=" + LANG_PLACEHOLDER;

    private final OkHttpClient client;

    private final ObjectMapper objectMapper;

    private final LanguageService languageService;

    public VehicleServiceImpl(OkHttpClient client, ObjectMapper objectMapper, LanguageService languageService) {
        this.client = client;
        this.objectMapper = objectMapper;
        this.languageService = languageService;
    }

    @Override
    public Optional<Vehicle> getVehicle(String id, String language) {
        try {
            LOGGER.info("Fetching vehicle for id: {}", id);

            Request request = new Request.Builder()
                    .url(URL.replace(ID_PLACEHOLDER, id)
                            .replace(LANG_PLACEHOLDER, languageService.getLanguageOrFallback(language)))
                    .build();

            ResponseBody responseBody;
            Vehicle vehicle;
            try (Response response = client.newCall(request).execute()) {
                responseBody = Objects.requireNonNull(response.body());
                vehicle = objectMapper.readValue(responseBody.string(), Vehicle.class);
            }

            return Optional.ofNullable(vehicle);
        } catch (IOException e) {
            throw new ClientException(e);
        }
    }
}
