package com.example.email.outlook;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.httpcore.HttpClients;
import com.microsoft.graph.requests.GroupCollectionPage;
import okhttp3.OkHttpClient;
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
import okhttp3.Response;

/**
 * 演示有身份访问
 */
public class Graph {
    public static void main(String[] args) throws IOException {
        final ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                .tenantId("679ca43b-5d50-420a-aeec-84558f11dd37")
                .clientId("1e1f80b0-3d7c-4e4d-bcfd-714112df2647")
                .clientSecret("q5_7aY.i0s3ciq.j6Gs0zHIhoJhvj8-kg.")
                .build();

        final TokenCredentialAuthProvider tokenCredentialAuthProvider =
                new TokenCredentialAuthProvider(Collections.singletonList("https://graph.microsoft.com/.default"), clientSecretCredential);
//                new TokenCredentialAuthProvider(Collections.singletonList("User.Read.All"), clientSecretCredential);
        final DefaultLogger defaultLogger = new DefaultLogger();
        defaultLogger.setLoggingLevel(LoggerLevel.ERROR);
        final GraphServiceClient<Request> graphClient = GraphServiceClient
                .builder()
                .authenticationProvider(tokenCredentialAuthProvider)
                .logger(defaultLogger)
                .buildClient();

//        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6Im5PbzNaRHJPRFhFSzFqS1doWHNsSFJfS1hFZyIsImtpZCI6Im5PbzNaRHJPRFhFSzFqS1doWHNsSFJfS1hFZyJ9.eyJhdWQiOiJhcGk6Ly9mZDVkZTE1Mi0yNGM3LTQwMzctYTMxMS03N2VmNzM5ZmJmMTciLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC9mOGNkZWYzMS1hMzFlLTRiNGEtOTNlNC01ZjU3MWU5MTI1NWEvIiwiaWF0IjoxNjIzNDEwNzg2LCJuYmYiOjE2MjM0MTA3ODYsImV4cCI6MTYyMzQ5NzQ4NiwiYWlvIjoiRTJaZ1lNaTZzdVlnMzY1TzRZa0xuZFk0ZkNrMEJBQT0iLCJhcHBpZCI6ImZkNWRlMTUyLTI0YzctNDAzNy1hMzExLTc3ZWY3MzlmYmYxNyIsImFwcGlkYWNyIjoiMSIsImlkcCI6Imh0dHBzOi8vc3RzLndpbmRvd3MubmV0L2Y4Y2RlZjMxLWEzMWUtNGI0YS05M2U0LTVmNTcxZTkxMjU1YS8iLCJyaCI6IjAuQVZZQU1lX04tQjZqU2t1VDVGOVhIcEVsV2xMaFhmM0hKRGRBb3hGMzczT2Z2eGNCQUFBLiIsInRpZCI6ImY4Y2RlZjMxLWEzMWUtNGI0YS05M2U0LTVmNTcxZTkxMjU1YSIsInV0aSI6IjlCeGlTSE5xTUVHME9jNl94aWdnQUEiLCJ2ZXIiOiIxLjAifQ.b-TWmsIEw1PexPP5kl5NVfcjqtsdiH9HF1a7scMU8QzaSzEIjgAUv1KFBt7flYQzQW9rSYP1BRSLAa-60Q5nqNZgWoqbYsubHqHj_Z2QDVeo5QQk-nac6ZQhYjdvPzdDEt3vG1jDqfg8dLe-NI_iH8bQqYaFKS1UZc88k3fZ_rvSPzaW-55zmNQZ1Zldmlhkuh4OQ7qoFoH0di_gIoikWwXhQOQ-cEIdcM7jvG-sjqeVMeSl6L6ecERU8ND495_p_gnZMhw-B2hTmK5ys07kE1xymxRX1Xq9jbX9L82vXKQau4kbF3eTMa_Pz3dxFlD5Xjm8Cx14XsNGf4x5e2mY8A";
//        final Option option = new HeaderOption("Authorization", "Bearer " + token);
//
//        final IdentityContainer identityContainer = graphClient.identity().buildRequest(option).get();
//        System.out.println("fasdfkajs;dfj:" + identityContainer);

        System.out.println("-=======--" + graphClient.me().events().buildRequest().get());
//        final User me = graphClient.me().buildRequest().get();
//        System.out.println(me);


//        GraphServiceClient graphClient = GraphServiceClient.builder().authenticationProvider(tokenCredentialAuthProvider).buildClient();
//
//        Event event = graphClient.groups("02bd9fd6-8f93-4758-87c3-1fb73740a315").events("AQMkAGI5MWY5ZmUyLTJiNzYtNDE0ZC04OWEwLWM3M2FjYmM3NwAzZWYARgAAA_b2VnUAiWNLj0xeSOs499YHAMT2RdsuOqRIlQZ4vOzp66YAAAIBDQAAAMT2RdsuOqRIlQZ4vOzp66YAAAIJOgAAAA==")
//                .buildRequest()
//                .get();

//        ClientCredentialProvider authProvider = new ClientCredentialProvider(CLIENT_ID, SCOPES, CLIENT_SECRET, TENANT_GUID, NATIONAL_CLOUD);

        OkHttpClient client = HttpClients.createDefault(tokenCredentialAuthProvider);
//        Request request = new Request.Builder().url("https://graph.microsoft.com/v1.0/me").build();
        Request request = new Request.Builder().url("https://graph.microsoft.com/v1.0/users").build();
        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }
}