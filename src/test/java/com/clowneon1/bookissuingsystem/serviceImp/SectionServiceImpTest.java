package com.clowneon1.bookissuingsystem.serviceImp;

import com.clowneon1.bookissuingsystem.model.Category;
import com.clowneon1.bookissuingsystem.model.Section;
import com.clowneon1.bookissuingsystem.repository.SectionRepository;
import com.clowneon1.bookissuingsystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.core.IsInstanceOf.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class SectionServiceImpTest {

    private MockMvc mockMvc;

    @Mock
    private SectionRepository sectionRepository;

    @InjectMocks
    private SectionServiceImp sectionServiceImp;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(sectionServiceImp).build();

    }

    Section s1 = Section.builder().id(1L).sectionName("section1").build();
    Section s2 = Section.builder().id(2L).sectionName("section2").build();
    Section s3 = Section.builder().id(3L).sectionName("section3").build();

    @Test
    void getAllSections() {
        when(sectionRepository.findAll()).thenReturn(Arrays.asList(s1,s2,s3));
        assertThat(sectionServiceImp.getAllSections()).isNotNull();
        assertThat(sectionServiceImp.getAllSections().size()).isEqualTo(3);
    }

    @Test
    void getSectionById() {
        when(sectionRepository.findById(anyLong())).thenReturn(Optional.ofNullable(s1));
        assertThat(sectionServiceImp.getSectionById(anyLong())).isNotNull().isEqualTo(s1);
    }

    @Test
    void createSection() {
        Section record = Section.builder().id(4L).sectionName("comedy").build();
        when(sectionRepository.save(record)).thenReturn(record);
        assertThat(sectionServiceImp.createSection(record)).isNotNull().isEqualTo(record);
    }

    @Test
    void updateSection() {
        Section updateRecord = Section.builder().sectionName("comedy").build();
        when(sectionRepository.findById(anyLong())).thenReturn(Optional.ofNullable(s1));
        Section _section = sectionServiceImp.getSectionById(anyLong());

        when(sectionRepository.save(_section)).thenReturn(_section);
        _section.setSectionName(updateRecord.getSectionName());

        assertThat(sectionServiceImp.updateSection(anyLong(),updateRecord)).isNotNull();
    }

    @Test
    void deleteSection() {
        sectionServiceImp.deleteSection(anyLong());
        verify(sectionRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteAllSection() {
        sectionServiceImp.deleteAllSection();
        verify(sectionRepository, times(1)).deleteAll();
    }
}