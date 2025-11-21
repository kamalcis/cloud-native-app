package com.kamal.cloudnative.backend.services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.kamal.cloudnative.backend.models.Tool;
import com.kamal.cloudnative.backend.repository.IToolRepository;

@Service
public class ToolService implements IToolService {

    private final IToolRepository iToolRepository;

    public ToolService(IToolRepository iToolRepository) {
        this.iToolRepository = iToolRepository;
    }

    public List<Tool> getTools() {
        return iToolRepository.getTools();
    }

}
