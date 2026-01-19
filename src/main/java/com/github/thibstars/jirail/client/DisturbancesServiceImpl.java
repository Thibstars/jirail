package com.github.thibstars.jirail.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.thibstars.jirail.exceptions.ClientException;
import com.github.thibstars.jirail.helper.LanguageService;
import com.github.thibstars.jirail.model.Disturbances;
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
public class DisturbancesServiceImpl implements DisturbancesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DisturbancesServiceImpl.class);

    private static final String LANG_PLACEHOLDER = "${lang}";

    private static final String URL = "https://api.irail.be/disturbances/?lang=" + LANG_PLACEHOLDER + "&format=json";

    private final OkHttpClient client;

    private final ObjectMapper objectMapper;

    private final LanguageService languageService;

    public DisturbancesServiceImpl(OkHttpClient client, ObjectMapper objectMapper, LanguageService languageService) {
        this.client = client;
        this.objectMapper = objectMapper;
        this.languageService = languageService;
    }

    @Override
    public Optional<Disturbances> getDisturbances(String language) {
        LOGGER.info("Fetching disturbances");

        try {
            Request request = new Request.Builder()
                    .url(URL.replace(LANG_PLACEHOLDER, languageService.getLanguageOrFallback(language)))
                    .build();

            ResponseBody responseBody;
            Disturbances disturbances;
            try (Response response = client.newCall(request).execute()) {
                responseBody = Objects.requireNonNull(response.body());
                disturbances = objectMapper.readValue(responseBody.string(), Disturbances.class);
            }

            return Optional.of(disturbances);
        } catch (IOException e) {
            throw new ClientException(e);
        }
    }

}
