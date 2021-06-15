package com.example.email.outlook;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.aad.msal4j.ClientCredentialFactory;
import com.microsoft.aad.msal4j.ClientCredentialParameters;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;
import com.microsoft.aad.msal4j.IAuthenticationResult;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.CalendarCollectionPage;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.requests.UserCollectionPage;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

public class Test {


    public static void main(String args[]) throws Exception {


        final ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                .clientId("fd5de152-24c7-4037-a311-77ef739fbf17")
                .clientSecret("~8LZllFlX.HZJuSw2cs_kMoMESI~07C01-")
                .tenantId("f8cdef31-a31e-4b4a-93e4-5f571e91255a")
                .build();

        final TokenCredentialAuthProvider tokenCredentialAuthProvider =
                new TokenCredentialAuthProvider(Collections.singletonList("https://graph.microsoft.com/.default"), clientSecretCredential);
//                new TokenCredentialAuthProvider(Collections.singletonList("Calendars.Read"), clientSecretCredential);

        final GraphServiceClient graphClient =
                GraphServiceClient
                        .builder()
                        .authenticationProvider(tokenCredentialAuthProvider)
                        .buildClient();

        CalendarCollectionPage calendars = graphClient.me().calendars()
                .buildRequest()
                .get();
        System.out.println(calendars);

//        JacksonAdapterc adapterc
//        final User me = graphClient.me().buildRequest().get();
//        System.out.println("========:"+me);
//        UserCollectionPage users = graphClient.users()
//                .buildRequest()
//                .get();
//        System.out.println("--->"+users);

    }

    public static void listC() {
        final ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                .clientId("fd5de152-24c7-4037-a311-77ef739fbf17")
                .clientSecret("~8LZllFlX.HZJuSw2cs_kMoMESI~07C01-")
                .tenantId("f8cdef31-a31e-4b4a-93e4-5f571e91255a")
                .build();

        TokenCredentialAuthProvider tokenCredentialAuthProvider =
//                new TokenCredentialAuthProvider(Collections.singletonList("api://fd5de152-24c7-4037-a311-77ef739fbf17/.default"), clientSecretCredential);
                new TokenCredentialAuthProvider(Collections.singletonList("User.Read.All"), clientSecretCredential);

        final GraphServiceClient graphClient =
                GraphServiceClient
                        .builder()
                        .authenticationProvider(tokenCredentialAuthProvider)
                        .buildClient();

        final User me = graphClient.me().buildRequest().get();
        System.out.println("---->" + me);
    }
}
