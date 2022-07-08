package org.example;

import io.fabric8.kubernetes.api.model.NamespaceList;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class Main {
    public static void main(String[] args) {
        String base64Token
                = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImFmUU4ta2h1MTU0Q013XzJRcE11WjNsS2Z0Vno2NXB1djN0dHRVdnFGMWMifQ"
                +
                ".eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJrdWJlLXN5c3RlbSIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJkYXNoYm9hcmQtYWRtaW4tdG9rZW4tbHQ5NjIiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC5uYW1lIjoiZGFzaGJvYXJkLWFkbWluIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQudWlkIjoiNjllZTdkNDAtNTYzNC00MDM1LWJjMTAtYTFlYTM0NWEyMWFmIiwic3ViIjoic3lzdGVtOnNlcnZpY2VhY2NvdW50Omt1YmUtc3lzdGVtOmRhc2hib2FyZC1hZG1pbiJ9.pqp0R0OfGREUH6b4t8ia_fiRMn3c48r0BrZitLm7Ezng-YOSkDK4nPGyz7jFKO6RPdN6Gy2cHO_YS7YBiIUzVvlfqa4cZnFnqPXPFDKepvPjjvujZekToVOFREyY3yGZHko8gFR-j1lzrql-RLEwJyiSzObUaCW8ZkU6C6t54rxNgwRB5rMls-SxphotxHaI-KQlHra8tOlN7JJOns2vO8vSsoiWGKZsHDaoN-gPgN1Gg9BJxzcPHQXPJ84FKoA9TFadBKVdqH6JG5Ccw3wsUdscDFSHKCeY7oqzucO8G4ZFnPFYP5NfhrcKoOvStzB2akyoqiVly6bkOwWhYRS5eg";
        String masterUrl = "https://47.100.121.251:6443";
        Config config = new ConfigBuilder()
                .withTrustCerts(true)
                .withMasterUrl(masterUrl)
                .withOauthToken(base64Token)
                .build();
        KubernetesClient client = new DefaultKubernetesClient(config);
        // 查看命名空间
        NamespaceList namespaceList = client.namespaces().list();
        namespaceList.getItems()
                .forEach(namespace ->
                        System.out.println(namespace.getMetadata().getName() + ":" + namespace.getStatus().getPhase()));

    }
}