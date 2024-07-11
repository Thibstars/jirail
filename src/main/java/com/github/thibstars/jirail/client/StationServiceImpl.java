package com.github.thibstars.jirail.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.thibstars.jirail.exceptions.ClientException;
import com.github.thibstars.jirail.helper.LanguageService;
import com.github.thibstars.jirail.model.Station;
import com.github.thibstars.jirail.model.Stations;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Thibault Helsmoortel
 */
public class StationServiceImpl implements StationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StationServiceImpl.class);

    private static final String LANG_PLACEHOLDER = "${lang}";

    private static final String URL = "https://api.irail.be/stations?format=json&lang=" + LANG_PLACEHOLDER;

    private final OkHttpClient client;

    private final ObjectMapper objectMapper;

    private final LanguageService languageService;

    private final LoadingCache<String, Map<String, Station>> cache;

    public StationServiceImpl(OkHttpClient client, ObjectMapper objectMapper, LanguageService languageService) {
        this.client = client;
        this.objectMapper = objectMapper;
        this.languageService = languageService;
        CacheLoader<String, Map<String, Station>> loader = new CacheLoader<>() {
            @NotNull
            @Override
            public Map<String, Station> load(@NotNull String key) throws Exception {
                return cache.get(key);
            }

            @NotNull
            @Override
            public Map<String, Map<String, Station>> loadAll(@NotNull Iterable<? extends String> keys) {
                return getAllStations();
            }
        };

        cache = CacheBuilder.newBuilder()
                .build(loader);

        try {
            cache.putAll(fetchAllStations());
        } catch (IOException e) {
            throw new ClientException(e);
        }
    }

    @Override
    public Set<Station> getStations(String language) {
        return new HashSet<>(cache.getUnchecked(languageService.getLanguageOrFallback(language)).values());
    }

    private Map<String, Map<String, Station>> getAllStations() {
        return cache.getAllPresent(languageService.getSupportedLanguages());
    }

    private Map<String, Map<String, Station>> fetchAllStations() throws IOException {
        Map<String, Map<String, Station>> stationMap = new HashMap<>();

        for (String language : languageService.getSupportedLanguages()) {
            stationMap.put(language, fetchStations(language));
        }

        return stationMap;
    }

    private Map<String, Station> fetchStations(String language) throws IOException {
        LOGGER.info("Fetching stations for language: {}", language);

        Request request = new Request.Builder()
                .url(URL.replace(LANG_PLACEHOLDER, language))
                .build();

        ResponseBody responseBody;
        Stations stations;
        try (Response response = client.newCall(request).execute()) {
            responseBody = Objects.requireNonNull(response.body());
            stations = objectMapper.readValue(responseBody.string(), Stations.class);
        }

        return stations != null ?
                stations.stations().stream()
                        .collect(Collectors.toMap(Station::id, Function.identity())) :
                Collections.emptyMap();
    }
}
