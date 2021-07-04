package com.example.email.outlook;

import com.azure.identity.AuthorizationCodeCredential;
import com.azure.identity.AuthorizationCodeCredentialBuilder;
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

        final AuthorizationCodeCredential authCodeCredential = new AuthorizationCodeCredentialBuilder()
                .clientId("1e1f80b0-3d7c-4e4d-bcfd-714112df2647")
                .clientSecret("q5_7aY.i0s3ciq.j6Gs0zHIhoJhvj8-kg.")
                .tenantId("679ca43b-5d50-420a-aeec-84558f11dd37")
//                .redirectUrl(redirectUri)
                .build();

        final TokenCredentialAuthProvider tokenCredentialAuthProvider
                = new TokenCredentialAuthProvider(Collections.singletonList("User.Read.All"), authCodeCredential);

        final GraphServiceClient graphClient =
                GraphServiceClient
                        .builder()
                        .authenticationProvider(tokenCredentialAuthProvider)
                        .buildClient();

        final User me = graphClient.me().buildRequest().get();
        System.out.println(me);

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
