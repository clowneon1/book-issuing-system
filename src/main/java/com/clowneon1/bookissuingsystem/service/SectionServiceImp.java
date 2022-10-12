package com.clowneon1.bookissuingsystem.service;

import com.clowneon1.bookissuingsystem.exception.ResourceNotFoundException;
import com.clowneon1.bookissuingsystem.model.Section;
import com.clowneon1.bookissuingsystem.repository.SectionRepository;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Service
public class SectionServiceImp implements SectionService{

    private SectionRepository sectionRepository;

    public SectionServiceImp(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    @Override
    public List<Section> getAllSections() {
        return sectionRepository.findAll();
    }

    @Override
    public Section getSectionById(long id) {
        return sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Section not found" + id));
    }

    @Override
    public Section createSection(Section section) {
        return sectionRepository.save(section);
    }

    @Override
    public Section updateSection(long id, Section section){
        Section _section = sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Section not found" + id));

        _section.setSectionName(section.getSectionName());
        return sectionRepository.save(_section);
    }

    @Override
    public void deleteSection(long id) {
        sectionRepository.deleteById(id);
    }

    @Override
    public void deleteAllSection() {
        sectionRepository.deleteAll();
    }
}
