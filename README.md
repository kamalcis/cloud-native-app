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
│   ├── network/          # VNet, Subnets, NSGs
│   ├── aks/              # AKS cluster configuration
|   ├── security/         # Key Vault, Managed Identity
│   ├── argo/             # ArgoCD setup and configuration
│   └── database/         # Azure SQL Database
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
|   └── values-dev.yaml               # Environment specif vlues overriden in argocd application manifests
|   └── values-prod.yaml
|   └── values-staging.yaml
└── frontend/                         # Frontend application deployable chart
    ├── templates/                    # Kubernetes manifests
    │   ├── frontend-deployment.yaml
    │   └── frontend$-service.yaml
    ├── charts/
    ├── Chart.yaml                    # Chart metadata and dependencies
    └── values.yaml                   # Default configuration values / manifests file refer this


argocd/
├── dev/                   # Development environment application CRDs
│   ├── backend-application.yaml
│   └── frontend-application.yaml
├── staging/              # Staging environment
└── prod/                 # Production environment


pipelines/
└── azure-pipeline.yaml   # Single pipeline to apply all Terraform modules (main.ts)




===================================  HOW THE PROCESS WORKS=========================

1. In azure pipeline /infrastructure/terraform/main.tf will be applied
2. main.tf will call the modules
           - network (vnet,subnet,nodepool,nsg)
           - aks (aks cluster)
           - argocd (Api Server, Repo Server, UI Interface, Application Controller)
           - database
3. /infrustructure/modules/argo/main.tf will install the application CRDS from
           -  /argocd/dev/backend-application.yaml using kubernetes_menifest provider

4. Now the argocd application is pointed to apps e.g. frontend and backend. So the frontend and backend helm chart will be deployed, updated, monitored through argocd now. (no pipeline needed)

```
