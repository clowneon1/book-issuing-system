package com.clowneon1.bookissuingsystem.service;

import com.clowneon1.bookissuingsystem.model.Section;

import java.util.List;

public interface SectionService {

    public List<Section> getAllSections();
    public Section getSectionById(long id);
    public Section createSection(Section section);

    public Section updateSection(long id, Section section);

    public void deleteSection(long id);

    public void deleteAllSection();

}
