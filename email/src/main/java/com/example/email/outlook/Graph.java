package com.example.email.outlook;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import okhttp3.Request;

import com.azure.identity.DeviceCodeCredential;
import com.azure.identity.DeviceCodeCredentialBuilder;

import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.logger.DefaultLogger;
import com.microsoft.graph.logger.LoggerLevel;
import com.microsoft.graph.models.Attendee;
import com.microsoft.graph.models.DateTimeTimeZone;
import com.microsoft.graph.models.EmailAddress;
import com.microsoft.graph.models.Event;
import com.microsoft.graph.models.ItemBody;
import com.microsoft.graph.models.User;
import com.microsoft.graph.models.AttendeeType;
import com.microsoft.graph.models.BodyType;
import com.microsoft.graph.options.HeaderOption;
import com.microsoft.graph.options.Option;
import com.microsoft.graph.options.QueryOption;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.requests.EventCollectionPage;
import com.microsoft.graph.requests.EventCollectionRequestBuilder;

/**
 * 演示有身份访问
 */
public class Graph {

    private static GraphServiceClient<Request> graphClient = null;
    private static TokenCredentialAuthProvider authProvider = null;

    public static void initializeGraphAuth(String applicationId, List<String> scopes) {
        // Create the auth provider
        final DeviceCodeCredential credential = new DeviceCodeCredentialBuilder()
                .clientId(applicationId)
                .challengeConsumer(challenge -> System.out.println(challenge.getMessage()))
                .build();

        authProvider = new TokenCredentialAuthProvider(scopes, credential);

        // Create default logger to only log errors
        DefaultLogger logger = new DefaultLogger();
        logger.setLoggingLevel(LoggerLevel.ERROR);

        // Build a Graph client
        graphClient = GraphServiceClient.builder()
                .authenticationProvider(authProvider)
                .logger(logger)
                .buildClient();
    }

    public static String getUserAccessToken() {
        try {
            URL meUrl = new URL("https://graph.microsoft.com/v1.0/me");
            return authProvider.getAuthorizationTokenAsync(meUrl).get();
        } catch (Exception ex) {
            return null;
        }
    }

    public static void main(String[] args) {
        // Initialize Graph with auth settings
        Graph.initializeGraphAuth("f3a2c4db-b13b-45e3-8984-53379a19ef5f", Collections.singletonList("User.Read"));
        final String accessToken = Graph.getUserAccessToken();
        System.out.println("====:" + accessToken);

        String token = "eyJ0eXAiOiJKV1QiLCJub25jZSI6IndDaDY3UWNDcDA5ZDZ1R2ZCc1VMZHNwRGlNY2QtS1IzdkhTc0x0MnpKaGciLCJhbGciOiJSUzI1NiIsIng1dCI6Im5PbzNaRHJPRFhFSzFqS1doWHNsSFJfS1hFZyIsImtpZCI6Im5PbzNaRHJPRFhFSzFqS1doWHNsSFJfS1hFZyJ9.eyJhdWQiOiIwMDAwMDAwMy0wMDAwLTAwMDAtYzAwMC0wMDAwMDAwMDAwMDAiLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC9jMWViNTExMi03OTQ2LTRjOWQtYmM1Ny00MDA0MGNmZTNhOTEvIiwiaWF0IjoxNjIzNDI3MjUzLCJuYmYiOjE2MjM0MjcyNTMsImV4cCI6MTYyMzQzMTE1MywiYWNjdCI6MCwiYWNyIjoiMSIsImFjcnMiOlsidXJuOnVzZXI6cmVnaXN0ZXJzZWN1cml0eWluZm8iLCJ1cm46bWljcm9zb2Z0OnJlcTEiLCJ1cm46bWljcm9zb2Z0OnJlcTIiLCJ1cm46bWljcm9zb2Z0OnJlcTMiLCJjMSIsImMyIiwiYzMiLCJjNCIsImM1IiwiYzYiLCJjNyIsImM4IiwiYzkiLCJjMTAiLCJjMTEiLCJjMTIiLCJjMTMiLCJjMTQiLCJjMTUiLCJjMTYiLCJjMTciLCJjMTgiLCJjMTkiLCJjMjAiLCJjMjEiLCJjMjIiLCJjMjMiLCJjMjQiLCJjMjUiXSwiYWlvIjoiQVNRQTIvOFRBQUFBZUh3a2ZhT2dzcU5qR0RMYzVyVGlqZlpRNUVxWkMxQmV3T0dWclFKRDBlUT0iLCJhbXIiOlsicHdkIl0sImFwcF9kaXNwbGF5bmFtZSI6IkphdmEgR3JhcGggVHV0b3JpYWwiLCJhcHBpZCI6ImYzYTJjNGRiLWIxM2ItNDVlMy04OTg0LTUzMzc5YTE5ZWY1ZiIsImFwcGlkYWNyIjoiMCIsImZhbWlseV9uYW1lIjoiQ04gTWVldGluZyBSb29tIENvbnRyb2xsZXIiLCJpZHR5cCI6InVzZXIiLCJpcGFkZHIiOiIxODAuMTYyLjEuMjIyIiwibmFtZSI6IkNOIE1lZXRpbmcgUm9vbSBDb250cm9sbGVyIiwib2lkIjoiOTI2YWVlOTgtMGMxMi00NGVjLWE0Y2UtZDQ4N2NmNmZmODZmIiwib25wcmVtX3NpZCI6IlMtMS01LTIxLTMzNDM4MzQyMjItMjAzMTc5MzgyMC0zMTcyNzAxMTE4LTE1ODgzOTkiLCJwbGF0ZiI6IjE0IiwicHVpZCI6IjEwMDMyMDAwNEQ1QzhFNUIiLCJyaCI6IjAuQVFNQUVsSHJ3VVo1blV5OFYwQUVEUDQ2a2R2RW92TTdzZU5GaVlSVE41b1o3MThEQU9rLiIsInNjcCI6Im9wZW5pZCBwcm9maWxlIFVzZXIuUmVhZCBlbWFpbCIsInN1YiI6IjJIc2pNblY3U1k2SzI2V0I4UFZvZjhhTmxSdHYzNy1LenBSUkxpTFdoRWciLCJ0ZW5hbnRfcmVnaW9uX3Njb3BlIjoiTkEiLCJ0aWQiOiJjMWViNTExMi03OTQ2LTRjOWQtYmM1Ny00MDA0MGNmZTNhOTEiLCJ1bmlxdWVfbmFtZSI6IkNOLU1lZXRpbmdSb29tQ29udHJvbGxlckBlY29sYWIuY29tIiwidXBuIjoiQ04tTWVldGluZ1Jvb21Db250cm9sbGVyQGVjb2xhYi5jb20iLCJ1dGkiOiJfM19TTEE1TWgwdUdaSWx6R2lKdkFBIiwidmVyIjoiMS4wIiwid2lkcyI6WyJiNzlmYmY0ZC0zZWY5LTQ2ODktODE0My03NmIxOTRlODU1MDkiXSwieG1zX2NjIjpbIkNQMSJdLCJ4bXNfc3QiOnsic3ViIjoieEMwYzFFcWwzLU9QQ3FvcFM0RXlmRXlIZDZ1aGI1RDBKZF9pckVUa2QzVSJ9LCJ4bXNfdGNkdCI6MTMxNTA1NDQ3M30.PZ4uz36JcENiKnLuNG39lCKNOX7u54w2vl_95reIQBia6GlG-daZoZjlRJRb74EPCqRBI5z6hGDAp5kvT0N7uVIYOIDIDt7XQn7V8BJSyZC_ozzRuv4_4pWQW1SL2PK0UIDGeyxytJm7JXIitD54o0wWxynSwxfX9tVUSBmG--la-XrVMkQClcl0iCl4keux4eQsMHAmhVrvabgoFKdIFuSfImlv1SDBIw6CGEcbGRJdz7F0twLXm1MdX0kFZouj0mAg9cV1tO1vKnjJHxJSHBGauNf950LbaGYDtjIzk49TlDJLL0mT3602h80uIMizK-IfI-TB0Gh76h4JpDDAug";
//        User user = getUser();
//        System.out.println("Welcome " + user.displayName);
//        System.out.println("Time zone: " + user.mailboxSettings.timeZone);
        System.out.println();
    }
}