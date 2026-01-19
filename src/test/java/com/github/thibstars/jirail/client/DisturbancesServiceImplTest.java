package com.github.thibstars.jirail.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.thibstars.jirail.helper.LanguageService;
import com.github.thibstars.jirail.model.Disturbances;
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
class DisturbancesServiceImplTest {

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
                  "timestamp": "1581853952",
                  "disturbance": [
                    {
                      "id": "0",
                      "title": "Brux.-Midi/Brus.-Zuid - Amsterdam CS (NL): Incident on the Dutch rail network.",
                      "description": "Between Brux.-Midi/Brus.-Zuid and Amsterdam CS (NL): Delays and cancellations are possible. Between Rotterdam CS (NL) and Amsterdam CS (NL): Disrupted train traffic. Indefinite duration of the failure. Listen to the announcements, consult the automatic departure boards or plan your trip via the SNCB app or sncb.be for more information.",
                      "link": "http://www.belgianrail.be/jp/nmbs-realtime/help.exe/en?tpl=showmap_external&tplParamHimMsgInfoGroup=trouble&messageID=41188&channelFilter=custom2,livemap,rss_line_10,twitter,custom1,timetable&",
                      "type": "disturbance",
                      "timestamp": "1581853724"
                    },
                    {
                      "id": "1",
                      "title": "Soignies / Zinnik: Failure of a level crossing.",
                      "description": "Delays between 5 and 15 minutes are possible in both directions. Indefinite duration of the failure. Listen to the announcements, consult the automatic departure boards or plan your trip via the SNCB app or sncb.be for more information.",
                      "link": "http://www.belgianrail.be/jp/nmbs-realtime/help.exe/en?tpl=showmap_external&tplParamHimMsgInfoGroup=trouble&messageID=41187&channelFilter=custom2,livemap,rss_line_10,twitter,custom1,timetable&",
                      "type": "disturbance",
                      "timestamp": "1581849358"
                    },
                    {
                      "id": "2",
                      "title": "Storm warning.",
                      "description": "On entire network, from saturday 15/02 evening to sunday 16/02 : Delays and cancellations are possible. Listen to the announcements, consult the automatic departure boards or plan your trip via the SNCB app or sncb.be for more information.",
                      "link": "http://www.belgianrail.be/jp/nmbs-realtime/help.exe/en?tpl=showmap_external&tplParamHimMsgInfoGroup=trouble&messageID=41174&channelFilter=timetable,custom1,twitter,rss_line_10,livemap,custom2&",
                      "type": "disturbance",
                      "timestamp": "1581767543"
                    },
                    {
                      "id": "3",
                      "title": "Brux.-Nord/Brus.-Noord - Schaerbeek / Schaarbeek",
                      "description": "We are conducting work for you between Brux.-Nord/Brus.-Noord and Schaerbeek / Schaarbeek. Detailed information only available in French (FR) and in Dutch (NL).",
                      "link": "http://www.belgianrail.be/jp/nmbs-realtime/help.exe/en?tpl=showmap_external&tplParamHimMsgInfoGroup=works&messageID=41159&channelFilter=rss_line_90&",
                      "type": "planned",
                      "timestamp": "1581691640",
                      "attachment": "http://www.belgianrail.be/jp/download/brail_him/1581691545283_NL-03003S.pdf"
                    },
                    {
                      "id": "4",
                      "title": "Ostende / Oostende - Anvers-Central / Antwerpen-Centraal",
                      "description": "We are conducting work for you between Ostende / Oostende and Anvers-Central / Antwerpen-Centraal. Detailed information only available in French (FR) and in Dutch (NL).",
                      "link": "http://www.belgianrail.be/jp/nmbs-realtime/help.exe/en?tpl=showmap_external&tplParamHimMsgInfoGroup=works&messageID=40825&channelFilter=timetable,rss_line_90,custom2&",
                      "type": "planned",
                      "timestamp": "1581691528",
                      "attachment": "http://www.belgianrail.be/jp/download/brail_him/1580998838767_NL-02045S.pdf"
                    }
                  ]
                }""");
        Mockito.when(response.body()).thenReturn(responseBody);
        Mockito.when(call.execute()).thenReturn(response);
        Mockito.when(client.newCall(ArgumentMatchers.any(Request.class))).thenReturn(call);

        Disturbances disturbances = Mockito.mock(Disturbances.class, Mockito.RETURNS_DEEP_STUBS);
        Mockito.when(objectMapper.readValue(responseBody.string(), Disturbances.class)).thenReturn(disturbances);

        String language = "en";
        Mockito.when(languageService.getLanguageOrFallback(ArgumentMatchers.anyString())).thenReturn(language);
        DisturbancesService disturbancesService = new DisturbancesServiceImpl(client, objectMapper, languageService);

        Disturbances result = disturbancesService.getDisturbances(language).orElseThrow();

        Assertions.assertNotNull(result, "Result must not be null.");
        Assertions.assertEquals(disturbances, result, "Result must match the expected.");
    }
}