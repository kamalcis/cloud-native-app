# Cloud-Native Project

This repository defines a cloud-native solution with **Terraform**, **ArgoCD**, and **Helm** for deploying frontend and backend applications on **Azure Kubernetes Service (AKS)**. It supports **dev, staging, and prod environments**.

===================================FOLDER STRUCTURE================

```Solution Structure
cloud-native-project/        # Project root directory
├── infrastructure/          # Terraform-based infrastructure
├── apps/                    # Application Helm charts and source code
├── argocd/                  # ArgoCD GitOps manifests
├── pipelines/               # Azure DevOps CI/CD pipelines
└── README.md                # Main read me file of the solution


infrastructure/              # All terraform codes
├── main.tf                  # Root Terraform entry point calling modules
├── provider.tf              # AzureRM provider configuration
├── variables.tf             # Global/shared variables
├── outputs.tf               # Aggregate outputs from modules
├── modules/                 # Reusable Terraform modules
│   ├── network/             # VNET, subnets, NSGs
│   ├── aks/                 # AKS cluster + node pools
│   └── database/            # Azure SQL Database (optional)
|   └── argo/                # Argocd API, UI,
├── envs/                    # Environment-specific variable files
│   ├── dev/terraform.tfvars
│   ├── staging/terraform.tfvars
│   └── prod/terraform.tfvars
└── scripts/                 # Helper scripts for Terraform automation


apps/                            # Kubernetes apps either raw or helm
├── helm/                        # All helm charts goes into helm
│   ├── frontend/                # Helm chart for frontend
│   │   ├── Chart.yaml           # Chart specification
│   │   ├── values.yaml          # Default Configuration values
|   |   ├── values-dev.yaml      # Default Configuration values for dev env
|   |   ├── values-staging.yaml  # Default Configuration values for stagging env
|   |   ├── values-prod.yaml     # Default Configuration values for prod env
│   │   └── templates/           # Kubernetes manifests inside chart
│   │       ├── deployment.yaml
│   │       ├── service.yaml
│   │       └── ingress.yaml
│   │
│   └── backend/                 # Helm chart for backend
│       ├── Chart.yaml           # Helm Chart specification for backend
│       ├── values.yaml          # Default configuration values
│       └── templates/           # Kubernetes manifests goes into templates
│           ├── deployment.yaml
│           ├── service.yaml
│           └── ingress.yaml
│
└── manifests/                   # Raw Kubernetes manifests (non-Helm)



argocd/                            # Application CRDS with kubernetes manifests
├── dev/
│   ├── frontend-application.yaml  # value files values-dev.yaml will be used
│   └── backend-application.yaml   # value files  values-dev.yaml will be used
│
├── staging/
│   ├── frontend-application.yaml   # value files values-stagging.yaml used
│   └── backend-application.yaml
│
└── prod/
    ├── frontend-application.yaml    # value files values-prod.yaml used
    └── backend-application.yaml

pipelines/
└── azure-pipeline.yaml   # Single pipeline to apply all Terraform modules (main.ts)




===================================  HOW THE PROCESS WORKS=========================

1. In azure pipeline /infrustructure/main.tf will be applied
2. main.tf will call the modules
           - network (vnet,subnet,nodepool,nsg)
           - aks (aks cluster)
           - argocd (Api Server, Repo Server, UI Interface, Application Controller)
           - database
3. /infrustructure/modules/argo/main.tf will install the application CRDS from
           -  /argocd/dev/backend-application.yaml using kubernetes_menifest provider

4. Now the argocd application is pointed to apps e.g. frontend and backend. So the frontend and backend helm chart will be deployed, updated, monitored through argocd now. (no pipeline needed)

```
