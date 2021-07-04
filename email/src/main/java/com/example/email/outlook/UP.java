package com.example.email.outlook;

import com.azure.identity.UsernamePasswordCredential;
import com.azure.identity.UsernamePasswordCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.GraphServiceClient;

import java.util.Collections;
import java.util.List;

public class UP {
    public static void main(String[] args) {
        final UsernamePasswordCredential usernamePasswordCredential = new UsernamePasswordCredentialBuilder()
                .clientId("1e1f80b0-3d7c-4e4d-bcfd-714112df2647")
                .username("ruanyanchao@kitedge.onmicrosoft.com")
                .password("Kitedge1102")
                .build();
        List<String> scopes = Collections.singletonList("User.Read.All");
        final TokenCredentialAuthProvider tokenCredentialAuthProvider = new TokenCredentialAuthProvider(scopes, usernamePasswordCredential);

        final GraphServiceClient graphClient =
                GraphServiceClient
                        .builder()
                        .authenticationProvider(tokenCredentialAuthProvider)
                        .buildClient();

        final User me = graphClient.me().buildRequest().get();
        System.out.println(me);
    }
}
