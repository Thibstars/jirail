package com.github.thibstars.jirail.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.thibstars.jirail.exceptions.ClientException;
import com.github.thibstars.jirail.helper.LanguageService;
import com.github.thibstars.jirail.model.LiveBoard;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Thibault Helsmoortel
 */
public class LiveBoardServiceImpl implements LiveBoardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LiveBoardServiceImpl.class);

    private static final String ID_PLACEHOLDER = "${id}";

    private static final String LANG_PLACEHOLDER = "${lang}";

    private static final String TIME_PLACEHOLDER = "${time}";

    private static final String URL = "https://api.irail.be/liveboard/?id=" + ID_PLACEHOLDER + "&arrdep=departure&lang=" + LANG_PLACEHOLDER + "&format=json&alerts=false&time=" + TIME_PLACEHOLDER;

    private final OkHttpClient client;

    private final ObjectMapper objectMapper;

    private final LanguageService languageService;

    public LiveBoardServiceImpl(OkHttpClient client, ObjectMapper objectMapper, LanguageService languageService) {
        this.client = client;
        this.objectMapper = objectMapper;
        this.languageService = languageService;
    }

    @Override
    public Optional<LiveBoard> getForStation(String id, String language) {
        return getForStation(id, language, LocalTime.now());
    }

    @Override
    public Optional<LiveBoard> getForStation(String id, String language, LocalTime localTime) {
        try {
            LiveBoard liveBoard = fetchLiveBoard(id, language, localTime);

            if (Stream.of(liveBoard.station(), liveBoard.stationInfo(), liveBoard.departures())
                    .allMatch(Objects::isNull)) {
                LOGGER.warn("Retrieved unexpected response for Live Board of station: {}", id);

                return Optional.empty();
            }

            return Optional.of(liveBoard);
        } catch (IOException e) {
            throw new ClientException(e);
        }
    }

    private LiveBoard fetchLiveBoard(String id, String language, LocalTime localTime) throws IOException {
        LOGGER.info("Fetching live board for station: {} and local time: {}", id, localTime);

        String timeString = Optional.ofNullable(localTime)
                .orElse(LocalTime.now())
                .format(DateTimeFormatter.ofPattern("HHmm"));

        Request request = new Request.Builder()
                .url(URL.replace(ID_PLACEHOLDER, id)
                        .replace(LANG_PLACEHOLDER, languageService.getLanguageOrFallback(language))
                        .replace(TIME_PLACEHOLDER, timeString))
                .build();

        ResponseBody responseBody;
        LiveBoard liveBoard;
        try (Response response = client.newCall(request).execute()) {
            responseBody = Objects.requireNonNull(response.body());
            liveBoard = objectMapper.readValue(responseBody.string(), LiveBoard.class);
        }

        return liveBoard;
    }
}
