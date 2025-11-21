package com.kamal.cloudnative.backend.repository;

import java.util.List;

import com.kamal.cloudnative.backend.models.Tool;

public interface IToolRepository {

    public List<Tool> getTools();

    public void createToolList();

}
