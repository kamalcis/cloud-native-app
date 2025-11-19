# Cloud-Native Project

===================================FOLDER STRUCTURE================

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
│   ├── network/          # VNet, Subnets, Nodepool, NSGs
│   ├── aks/              # AKS cluster configuration on nodepool
|   ├── security/         # Key Vault, Managed Identity, Image Verification (Signed Image at CI pipeline)
│   ├── argo/             # ArgoCD setup and configuration
│   └── database/         # Azure managed SQL Database, backup and recovery through primary and secondary replica
├── main.tf               # Main infrastructure configuration
├── variables.tf          # Input variables
├── outputs.tf            # Output values
└── provider.tf           # Provider configurations



helm/
├── backend/                          # Deployable helm chart / application
│   ├── templates/                    # Kubernetes manifests files
│   │   ├── deployment.yaml           # App deployment
│   │   ├── service.yaml              # Service that expose the deployment
│   │   ├── network-policy.yaml       # Pod security
│   │   └── service-account.yaml      # For Managed Identity
│   ├── charts/                       # Subcharts / Dependencies
│   ├── Chart.yaml                    # Chart metadata and dependencies
│   └── values.yaml
│   └── values-dev.yaml               # Environment specific values overriden in argocd
│   └── values-prod.yaml
│   └── values-staging.yaml
│
├── frontend/                         # Frontend application deployable chart
│   ├── templates/                    # Kubernetes manifests
│   │   ├── frontend-deployment.yaml
│   │   └── frontend-service.yaml
│   ├── charts/
│   ├── Chart.yaml                    # Chart metadata and dependencies
│   └── values.yaml                   # Default configuration values
│
└── observability/                    # Observability Helm charts
    ├── prometheus/                   # Prometheus Helm chart
    ├── grafana/                      # Grafana Helm chart
    └── efk/                          # Elasticsearch + Fluent Bit + Kibana



argocd/
├── dev/                   # Development environment application CRDs
│   ├── backend-application.yaml
│   └── frontend-application.yaml
├── staging/              # Staging environment
└── prod/                 # Production environment


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
