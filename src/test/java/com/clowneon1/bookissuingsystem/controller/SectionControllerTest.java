package com.clowneon1.bookissuingsystem.controller;

import com.clowneon1.bookissuingsystem.model.Section;
import com.clowneon1.bookissuingsystem.service.SectionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SectionControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();


    @Mock
    SectionService sectionService;

    @InjectMocks
    SectionController sectionController;

    //Section s1 = new Section(1L,"section1");
    Section s1 = Section.builder().id(1L).sectionName("section1").build();
    Section s2 = Section.builder().id(2L).sectionName("section2").build();
    Section s3 = Section.builder().id(3L).sectionName("section3").build();

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(sectionController).build();
    }


    @Test
    public void getAllSections() throws Exception{
        List<Section> records = new ArrayList<>(Arrays.asList(s1,s2,s3));

        when(sectionService.getAllSections()).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v3/sections")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].sectionName", is(s1.getSectionName())));
    }

    @Test
    public void getSectionById() throws Exception{
        when(sectionService.getSectionById(anyLong())).thenReturn(s1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v3/sections/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.sectionName", is(s1.getSectionName())));
    }

    @Test
    public void createSection() throws Exception {
        Section record = Section.builder().id(4L).sectionName("section4").build();

        String content = objectWriter.writeValueAsString(record);

        when(sectionService.createSection(record)).thenReturn(record);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v3/sections")
                .characterEncoding(Charset.defaultCharset())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated());

    }

    @Test
    public void updateSection() throws Exception {
        Section record = Section.builder().id(1L).sectionName("sectionx").build();

        String content = objectWriter.writeValueAsString(record);

        when(sectionService.updateSection(1L,record)).thenReturn(record);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/v3/sections/1")
                .characterEncoding(Charset.defaultCharset())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }

    @Test
    public void deleteSection() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v3/sections/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .equals("section deleted");

        verify(sectionService, times(1)).deleteSection(anyLong());
    }

    @Test
    public void deleteAllSections() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v3/sections")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .equals("All sections deleted");

        verify(sectionService, times(1)).deleteAllSection();
    }
}