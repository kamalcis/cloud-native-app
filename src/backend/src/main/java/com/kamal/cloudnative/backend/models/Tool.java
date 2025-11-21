package com.kamal.cloudnative.backend.models;

public class Tool {
    private int id;
    private String name;
    private String description;
    private String competenceLevel;

    public Tool(int id, String name, String description, String competenceLevel) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.competenceLevel = competenceLevel;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCompetenceLevel() {
        return competenceLevel;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompetenceLevel(String competenceLevel) {
        this.competenceLevel = competenceLevel;
    }

}
