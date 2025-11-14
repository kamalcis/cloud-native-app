===================================FOLDER STRUCTURE================

```Solution Structure
cloud-native-project/
├── infrastructure/          # Terraform-based infrastructure
├── apps/                    # Application Helm charts and source code
├── argocd/                  # ArgoCD GitOps manifests
├── pipelines/               # Azure DevOps CI/CD pipelines
└── README.md                # Main read me file of the solution


infrastructure/
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


apps/
├── helm/                        # All helm charts goes into helm
│   ├── frontend/                # Helm chart for frontend
│   │   ├── Chart.yaml           # Chart specification
│   │   ├── values.yaml          # Default Configuration values
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



argocd/
├── base/
│   ├── frontend-application.yaml     # ArgoCD Application CRDS for frontend
│   └── backend-application.yaml      # ArgoCD Application CRDS for backend
│
└── overlays/
    ├── dev/
    │   ├── frontend-application.yaml
    │   └── backend-application.yaml
    │
    ├── staging/
    │   ├── frontend-application.yaml
    │   └── backend-application.yaml
    │
    └── prod/
        ├── frontend-application.yaml
        └── backend-application.yaml

```
