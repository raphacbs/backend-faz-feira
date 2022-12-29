package com.coelho.fazfeira.controller;

import com.coelho.fazfeira.builder.UnitDtoBuilder;
import com.coelho.fazfeira.builder.UnitRequestBodyBuilder;
import com.coelho.fazfeira.dto.UnitRequestBody;
import com.coelho.fazfeira.service.UnitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@WebMvcTest(controllers = UnitController.class)
@AutoConfigureMockMvc
public class UnitControllerTest {

    @MockBean
    private UnitService unitService;
    @Autowired
    private UnitController unitController;
    private JacksonTester<UnitRequestBody> json;
    @Autowired
    private MockMvc mockMvc;
    private UnitRequestBody unitRequestBody;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        unitRequestBody = UnitRequestBodyBuilder.createValid();
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
        when(unitService.create(any(UnitRequestBody.class))).thenReturn(UnitDtoBuilder.create(unitRequestBody));
    }

    @Test
    public void whenPostRequestToUnitsAndValidUnit_then201() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/units")
                        .content(json.write(unitRequestBody).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }
}
