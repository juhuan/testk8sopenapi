k8s-admin.yaml

```yaml
---
apiVersion: v1
kind: ServiceAccount
metadata:
  name: dashboard-admin
  namespace: kube-system
---
kind: ClusterRoleBinding
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: dashboard-admin
subjects:
  - kind: ServiceAccount
    name: dashboard-admin
    namespace: kube-system
roleRef:
  kind: ClusterRole
  name: cluster-admin
  apiGroup: rbac.authorization.k8s.io
```

```bash
shell@Alicloud:~$ kubectl apply -f k8s-admin.yaml 
serviceaccount/dashboard-admin created
clusterrolebinding.rbac.authorization.k8s.io/dashboard-admin created
```

```bash
shell@Alicloud:~$ kubectl -n kube-system get sa dashboard-admin -o yaml
apiVersion: v1
kind: ServiceAccount
metadata:
  annotations:
    kubectl.kubernetes.io/last-applied-configuration: |
      {"apiVersion":"v1","kind":"ServiceAccount","metadata":{"annotations":{},"name":"dashboard-admin","namespace":"kube-system"}}
  creationTimestamp: "2022-07-08T09:09:34Z"
  name: dashboard-admin
  namespace: kube-system
  resourceVersion: "5244634"
  uid: 69ee7d40-5634-4035-bc10-a1ea345a21af
secrets:
- name: dashboard-admin-token-lt962
```

```bash
shell@Alicloud:~$ kubectl describe secret dashboard-admin-token-lt962 -n kube-system
Name:         dashboard-admin-token-lt962
Namespace:    kube-system
Labels:       <none>
Annotations:  kubernetes.io/service-account.name: dashboard-admin
              kubernetes.io/service-account.uid: 69ee7d40-5634-4035-bc10-a1ea345a21af

Type:  kubernetes.io/service-account-token

Data
====
namespace:  11 bytes
token:      eyJhbGciOixxxxxxxxxxxxxxxxxxxxxxx
ca.crt:     1460 bytes
```

记住这里的token，即ServiceAccount Secret

去ACK集群管理页面查看管理URL
![](https://gw.alipayobjects.com/zos/antfincdn/zu5J%24LJGy/ee9b75b5-7665-4a7c-8254-316bfa0c37e9.png)

```java
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
```

