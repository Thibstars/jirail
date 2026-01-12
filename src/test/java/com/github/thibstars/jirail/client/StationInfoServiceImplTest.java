package com.github.thibstars.jirail.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.thibstars.jirail.helper.LanguageService;
import com.github.thibstars.jirail.model.StationInfo;
import com.github.thibstars.jirail.model.Stations;
import java.io.IOException;
import java.util.Set;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

/**
 * @author Thibault Helsmoortel
 */
class StationInfoServiceImplTest {

    @Test
    void shouldGetStations() throws IOException {
        OkHttpClient client = Mockito.mock(OkHttpClient.class);
        ObjectMapper objectMapper = Mockito.mock(ObjectMapper.class);
        LanguageService languageService = Mockito.mock(LanguageService.class);

        Call call = Mockito.mock(Call.class);
        Response response = Mockito.mock(Response.class);
        ResponseBody responseBody = Mockito.mock(ResponseBody.class);
        Mockito.when(responseBody.string()).thenReturn("""
                {
                  "version": "1.1",
                  "timestamp": 1489621486,
                  "station": {
                    "id": "BE.NMBS.008821006",
                    "@id": "http://irail.be/stations/NMBS/008821006",
                    "locationX": 4.421101,
                    "locationY": 51.2172,
                    "standardname": "Antwerpen-Centraal",
                    "name": "Antwerp-Central"
                  }
                }""");
        Mockito.when(response.body()).thenReturn(responseBody);
        Mockito.when(call.execute()).thenReturn(response);
        Mockito.when(client.newCall(ArgumentMatchers.any(Request.class))).thenReturn(call);

        StationInfo stationInfo = Mockito.mock(StationInfo.class);
        Mockito.when(stationInfo.id()).thenReturn("BE.NMBS.008821006");
        Stations stations = new Stations(Set.of(stationInfo));
        Mockito.when(objectMapper.readValue(responseBody.string(), Stations.class)).thenReturn(stations);

        String language = "en";
        Mockito.when(languageService.getLanguageOrFallback(ArgumentMatchers.anyString())).thenReturn(language);
        Mockito.when(languageService.getSupportedLanguages()).thenReturn(Set.of("en"));
        StationServiceImpl stationService = new StationServiceImpl(client, objectMapper, languageService);

        Set<StationInfo> result = stationService.getStations(language);

        Assertions.assertNotNull(result, "Result must not be null.");
        Assertions.assertFalse(result.isEmpty(), "Result must not be empty.");
        Assertions.assertEquals(1, result.size(), "Result size must be correct.");
        Assertions.assertEquals(stationInfo, result.stream().toList().getFirst(), "Result must match the expected.");
    }
}