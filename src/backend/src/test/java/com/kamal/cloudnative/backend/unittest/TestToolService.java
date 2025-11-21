package com.kamal.cloudnative.backend.unittest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kamal.cloudnative.backend.models.Tool;
import com.kamal.cloudnative.backend.repository.IToolRepository;
import com.kamal.cloudnative.backend.services.ToolService;

@ExtendWith(MockitoExtension.class)
class TestToolService {

    @Mock
    private IToolRepository toolRepository;

    @InjectMocks
    private ToolService toolService;

    @Test
    void test_getTools() {
        // Prepare mock data
        List<Tool> mockTools = List.of(new Tool(1, "Hammer", "Good", "4.5"),
                new Tool(1, "Hammer", "Good", "4.5"));
        when(toolRepository.getTools()).thenReturn(mockTools);
        // Call the service to get data
        List<Tool> tools = toolService.getTools();
        // Assert
        assertThat(tools).hasSize(2);
    }
}
