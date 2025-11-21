package com.kamal.cloudnative.backend.services;

import java.util.List;

import com.kamal.cloudnative.backend.models.Tool;

public interface IToolService {

    public List<Tool> getTools();

}
