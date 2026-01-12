package com.github.thibstars.jirail.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.thibstars.jirail.helper.LanguageService;
import com.github.thibstars.jirail.model.Vehicle;
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
class VehicleServiceImplTest {

    @Test
    void shouldGetVehicle() throws IOException {
        OkHttpClient client = Mockito.mock(OkHttpClient.class);
        ObjectMapper objectMapper = Mockito.mock(ObjectMapper.class);
        LanguageService languageService = Mockito.mock(LanguageService.class);

        Call call = Mockito.mock(Call.class);
        Response response = Mockito.mock(Response.class);
        ResponseBody responseBody = Mockito.mock(ResponseBody.class);
        Mockito.when(responseBody.string()).thenReturn("""
                {
                  "version": "1.1",
                  "timestamp": 1489621902,
                  "vehicle": "BE.NMBS.IC3033",
                  "vehicleinfo": {
                    "name": "BE.NMBS.IC3033",
                    "locationX": 4.421101,
                    "locationY": 51.2172,
                    "shortname": "IC3033",
                    "@id": "http://irail.be/vehicle/IC3033"
                  },
                  "stops": {
                    "number": 10,
                    "stop": [
                      {
                        "id": 0,
                        "station": "Antwerp-central",
                        "stationinfo": {
                          "id": "BE.NMBS.008821006",
                          "@id": "http://irail.be/stations/NMBS/008821006",
                          "locationX": 4.421101,
                          "locationY": 51.2172,
                          "standardname": "Antwerpen-Centraal",
                          "name": "Antwerp-Central"
                        },
                        "time": 1489658760,
                        "delay": 0,
                        "platform": 4,
                        "platforminfo": {
                          "name": "4",
                          "normal": "1"
                        },
                        "canceled": 0,
                        "departureDelay": 0,
                        "departureCanceled": 0,
                        "scheduledDepartureTime": 1489658760,
                        "arrivalDelay": 0,
                        "arrivalCanceled": 0,
                        "isExtraStop": 0,
                        "scheduledArrivalTime": 1489658760,
                        "departureConnection": "http://irail.be/connections/8821006/20170316/IC3033",
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

        Vehicle vehicle = Mockito.mock(Vehicle.class, Mockito.RETURNS_DEEP_STUBS);
        Mockito.when(objectMapper.readValue(responseBody.string(), Vehicle.class)).thenReturn(vehicle);

        String language = "en";
        Mockito.when(languageService.getLanguageOrFallback(ArgumentMatchers.anyString())).thenReturn(language);
        VehicleService vehicleService = new VehicleServiceImpl(client, objectMapper, languageService);

        Vehicle result = vehicleService.getVehicle("BE.NMBS.IC1832", language).orElseThrow();

        Assertions.assertNotNull(result, "Result must not be null.");
        Assertions.assertEquals(vehicle, result, "Result must match the expected.");
    }

}
