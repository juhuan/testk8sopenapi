package org.example;

import io.fabric8.kubernetes.api.model.NamespaceList;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class Main {
    public static void main(String[] args) {
        String base64Token = "<<填写ServiceAccount Secret>>";
        String masterUrl = "<<填写K8S管理URL>>";
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