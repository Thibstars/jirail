package com.github.thibstars.jirail.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.thibstars.jirail.helper.LanguageService;
import com.github.thibstars.jirail.model.LiveBoard;
import java.io.IOException;
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
class LiveBoardServiceImplTest {

    @Test
    void shouldGetForStation() throws IOException {
        OkHttpClient client = Mockito.mock(OkHttpClient.class);
        ObjectMapper objectMapper = Mockito.mock(ObjectMapper.class);
        LanguageService languageService = Mockito.mock(LanguageService.class);

        Call call = Mockito.mock(Call.class);
        Response response = Mockito.mock(Response.class);
        ResponseBody responseBody = Mockito.mock(ResponseBody.class);
        Mockito.when(responseBody.string()).thenReturn("""
                {
                  "version": "1.1",
                  "timestamp": 1489614297,
                  "station": "Ghent-Sint-Pieters",
                  "stationinfo": {
                    "id": "BE.NMBS.008821006",
                    "@id": "http://irail.be/stations/NMBS/008821006",
                    "locationX": 4.421101,
                    "locationY": 51.2172,
                    "standardname": "Antwerpen-Centraal",
                    "name": "Antwerp-Central"
                  },
                  "departures": {
                    "number": 32,
                    "departure": [
                      {
                        "id": 0,
                        "delay": 0,
                        "station": "Antwerp-Central",
                        "stationinfo": {
                          "id": "BE.NMBS.008821006",
                          "@id": "http://irail.be/stations/NMBS/008821006",
                          "locationX": 4.421101,
                          "locationY": 51.2172,
                          "standardname": "Antwerpen-Centraal",
                          "name": "Antwerp-Central"
                        },
                        "time": 1489575600,
                        "vehicle": "BE.NMBS.IC3033",
                        "vehicleinfo": {
                          "name": "BE.NMBS.IC3033",
                          "shortname": "IC3033",
                          "@id": "http://irail.be/vehicle/IC3033"
                        },
                        "platform": 4,
                        "platforminfo": {
                          "name": "4",
                          "normal": "1"
                        },
                        "canceled": 0,
                        "left": 0,
                        "departureConnection": "http://irail.be/connections/8821006/20170316/IC1832",
                        "occupancy": {
                          "@id": "http://api.irail.be/terms/unknown",
                          "name": "unknown"
                        }
                      }
                    ]
                  }
                }""");
        Mockito.when(response.body()).thenReturn(responseBody);
        Mockito.when(call.execute()).thenReturn(response);
        Mockito.when(client.newCall(ArgumentMatchers.any(Request.class))).thenReturn(call);

        LiveBoard liveBoard = Mockito.mock(LiveBoard.class, Mockito.RETURNS_DEEP_STUBS);
        Mockito.when(objectMapper.readValue(responseBody.string(), LiveBoard.class)).thenReturn(liveBoard);

        String language = "en";
        Mockito.when(languageService.getLanguageOrFallback(ArgumentMatchers.anyString())).thenReturn(language);
        LiveBoardService liveBoardService = new LiveBoardServiceImpl(client, objectMapper, languageService);

        LiveBoard result = liveBoardService.getForStation("6", language).orElseThrow();

        Assertions.assertNotNull(result, "Result must not be null.");
        Assertions.assertEquals(liveBoard, result, "Result must match the expected.");
    }
}