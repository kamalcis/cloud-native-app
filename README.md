# Cloud-Native Project Solution Structure and features to implement

```Solution Structure
cloud-native-app/
├── src/ # Application source code (backend, frontend)
├── infrastructure/ # Terraform, Helm, ArgoCD (GitOps) configs
├── pipeline/ # CI/CD pipeline definitions
└── README.md # Project documentation

src/
├── backend/ # Spring Boot backend applications
└── frontend/ # Angular frontend applications

terraform/
├── envs/                 # Environment-specific configurations
├── modules/              # Reusable infrastructure modules
│   ├── network/          # VNet, Subnets, Nodepool, NSGs, Azure Firewall, DDos
│   ├── aks/              # AKS cluster configuration on nodepool
|   ├── security/         # Key Vault, Managed Identity, Image Verification (Signed Image at CI pipeline), Velero Backup
│   ├── argo/             # ArgoCD setup and configuration
│   └── database/         # Azure managed SQL Database, Cross Origin Disaster Recovery, Failover Group
│   └── cost/             # Azure native cost alerts based on thresehold
│
├── main.tf               # Main infrastructure configuration
├── variables.tf          # Input variables
├── outputs.tf            # Output values
└── provider.tf           # Provider configurations



helm/
├── platform/                         # Platform services (managed by platform team)
│   ├── istio/                        # Service Mesh & Ingress Implementation
│   │   ├── templates/
│   │   │   ├── namespace.yaml
│   │   │   ├── istio-base.yaml
│   │   │   ├── istiod.yaml
│   │   │   ├── ingress-gateway.yaml   # Implements Gateway API
│   │   │   ├── egress-gateway.yaml
│   │   │   ├── mesh-config.yaml
│   │   │   └── peer-authentication.yaml
│   │   ├── Chart.yaml
│   │   └── values.yaml
│   │
│   ├── observability/                # Monitoring & Logging Stack
│   │   ├── prometheus/               # Metrics Collection
│   │   │   ├── templates/
│   │   │   │   ├── prometheus-server.yaml
│   │   │   │   ├── prometheus-config.yaml
│   │   │   │   └── service-monitors.yaml
│   │   │   ├── Chart.yaml
│   │   │   └── values.yaml
│   │   ├── grafana/                  # Visualization
│   │   │   ├── templates/
│   │   │   │   ├── grafana-deployment.yaml
│   │   │   │   ├── grafana-dashboards.yaml
│   │   │   │   └── grafana-datasources.yaml
│   │   │   ├── Chart.yaml
│   │   │   └── values.yaml
│   │   └── efk/                      # Logging Pipeline
│   │       ├── templates/
│   │       │   ├── elasticsearch.yaml
│   │       │   ├── fluent-bit.yaml
│   │       │   └── kibana.yaml
│   │       ├── Chart.yaml
│   │       └── values.yaml
│   │
│   ├── security/                     # Security Platform
│   │   ├── cert-manager/             # Certificate Management
│   │   │   ├── templates/
│   │   │   │   ├── cert-manager.yaml
│   │   │   │   ├── cluster-issuer.yaml
│   │   │   │   ├── letsencrypt-prod.yaml
│   │   │   │   └── letsencrypt-staging.yaml
│   │   │   ├── Chart.yaml
│   │   │   └── values.yaml
│   │   ├── gatekeeper/               # Policy Enforcement
│   │   │   ├── templates/
│   │   │   │   ├── gatekeeper.yaml
│   │   │   │   ├── constraint-templates/
│   │   │   │   └── constraints/
│   │   │   ├── Chart.yaml
│   │   │   └── values.yaml
│   │   ├── falco/                    # Runtime Security (Corrected Location)
│   │   │   ├── templates/
│   │   │   │   ├── falco-daemonset.yaml
│   │   │   │   ├── falco-rules.yaml
│   │   │   │   └── falco-config.yaml
│   │   │   ├── Chart.yaml
│   │   │   └── values.yaml
│   │   └── velero/                   # Backup & Disaster Recovery
│   │       ├── templates/
│   │       │   ├── velero.yaml
│   │       │   ├── backup-storage-location.yaml
│   │       │   └── volume-snapshot-location.yaml
│   │       ├── Chart.yaml
│   │       └── values.yaml
│
├── apps/                             # Business Applications (managed by app teams)
│   ├── backend/                      # Spring Boot Microservices
│   │   ├── templates/
│   │   │   ├── deployment.yaml
│   │   │   ├── service.yaml
│   │   │   ├── hpa.yaml
│   │   │   ├── service-account.yaml
│   │   │   ├── rbac.yaml
│   │   │   ├── network-policy.yaml
│   │   │   ├── default-deny-network-policy.yaml
│   │   │   ├── limit-range.yaml
│   │   │   ├── secret-providerclass.yaml
│   │   │   ├── pdb.yaml
│   │   │   └── httproute.yaml        # App-specific routing
│   │   ├── charts/
│   │   ├── Chart.yaml
│   │   └── values.yaml
│   │   └── values-dev.yaml
│   │   └── values-prod.yaml
│   │   └── values-staging.yaml
│   │
│   └── frontend/                     # Angular Application
│       ├── templates/
│       │   ├── frontend-deployment.yaml
│       │   ├── frontend-service.yaml
│       │   ├── frontend-hpa.yaml
│       │   ├── frontend-rbac.yaml
│       │   ├── frontend-limit-range.yaml
│       │   └── frontend-httproute.yaml
│       ├── charts/
│       ├── Chart.yaml
│       └── values.yaml
│
└── common-config/                    # Cluster-wide configurations
    ├── templates/
    │   ├── namespaces.yaml           # Namespaces with PSS labels
    │   ├── gateway.yaml              # GatewayClass & Gateway CRDs
    │   ├── resource-quotas.yaml
    │   └── priority-classes.yaml
    ├── Chart.yaml
    └── values.yaml


argocd/
├── dev/                          # Development environment
│   ├── backend-application.yaml
│   ├── frontend-application.yaml
│   └── platform-applications.yaml
├── staging/                      # Staging environment
└── prod/                         # Production environment


pipelines/                              # trivy image scan
└── azure-security-scan-pipeline.yaml   # terraform fmt,terraform validate,tflint,checkov,helm lint,kube-score,
└── azure-ci-pipeline.yaml              # checkout, build, test, create docker image, push docker image, image sign
└── azure-deployment-pipeline.yaml      # Create infrastructure by apply infrastructure/terraform/main.tf
                                          Rest of the modules called by the main.tf




======================================== ORDER OF PIPELINE EXECUTION ====================

Commit / PR
│
▼
[Security-Scan-Pipeline]
│
▼ (if pass)
[Azure-CI-Pipeline]
│
▼ (if pass)
[Azure-Deployment-Pipeline]
│
▼
ArgoCD Sync → Apps + Observability Deployed


===================================  HOW THE PROCESS WORKS=========================

1. In azure-deployment-pipeline /infrastructure/terraform/main.tf will be applied first
   main.tf will call the following modules
           - network (vnet,subnet,nodepool,nsg)
           - aks (aks cluster)
           - argocd (Api Server, Repo Server, UI Interface, Application Controller)
           - database
           - security
3. /infrustructure/modules/argo/main.tf will install the application CRDS from
           -  /argocd/dev/backend-application.yaml using kubernetes_menifest provider

4. Now the argocd application is pointed to apps e.g. frontend and backend. So the frontend and backend helm chart will be deployed, updated, monitored through argocd now. (no pipeline needed)

```

# ToDo List ( Identified to be Implemented)

OPA/Gatekeeper Policies // Apply policy before deploying resources to cluster
Container Vulnerability Scanning in Pipeline // X-RAY of docker image , find security holes
Database Connection Pooling // Faster connection , reuse connection rather recreate it, after serving back to pool
mTLS in Istio // Ensure the identity of every service
Automated Rollbacks // IF any error found in new deployment automatically rollback
