package com.kamal.cloudnative.backend.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.kamal.cloudnative.backend.models.Tool;

@Repository
public class ToolRepository implements IToolRepository {

    List<Tool> toolList = new ArrayList<>();

    public ToolRepository() {
        createToolList();
    }

    public List<Tool> getTools() {
        return this.toolList;
    }

    public void createToolList() {

        toolList.add(new Tool(1, "Dockers",
                "Docker is a platform for building, packaging, and running applications in portable containers. ",
                "5"));
        toolList.add(new Tool(2, "Kubernetes",
                "Kubernetes is a container orchestration platform for automating deployment and scaling.", "5"));
        toolList.add(new Tool(3, "GitOps",
                "GitOps is a deployment approach that uses Git as the single source of truth for automated operations.",
                "4.5"));
        toolList.add(new Tool(4, "ArgoCD",
                "Monitor the infrastructure repo and deploy kubernete resources  ", "5"));
        toolList.add(new Tool(5, "Helm",
                "Deploy kubernetes resources as packages in the form of chart  ", "5"));
        toolList.add(new Tool(6, "Terraform",
                "Terraform is a declarative IaC tool for provisioning infrastructure as code.", "4.5"));

    }

}
