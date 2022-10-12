package com.clowneon1.bookissuingsystem.controller;

import com.clowneon1.bookissuingsystem.model.Section;
import com.clowneon1.bookissuingsystem.model.User;
import com.clowneon1.bookissuingsystem.service.SectionService;
import com.clowneon1.bookissuingsystem.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v3")
public class SectionController {
    SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping("/sections")
    public ResponseEntity<List<Section>> getAllSections(){
        return new ResponseEntity<>(sectionService.getAllSections(), HttpStatus.OK);
    }

    @GetMapping("/sections/{id}")
    public ResponseEntity<Section> getSectionById(@PathVariable("id") long sectionId){
        return new ResponseEntity<>(sectionService.getSectionById(sectionId), HttpStatus.OK);

    }

    @PostMapping("/sections")
    public ResponseEntity<Section> createSection(@RequestBody Section sectionRequest){
        return new ResponseEntity<>(sectionService.createSection(sectionRequest),HttpStatus.CREATED);
    }

    @PutMapping("/sections/{id}")
    public ResponseEntity<Section> updateSection(@PathVariable("id") long sectionId, @RequestBody Section sectionRequest){
        return new ResponseEntity<>(sectionService.updateSection(sectionId,sectionRequest),HttpStatus.OK);
    }

    @DeleteMapping("/sections/{id}")
    public ResponseEntity<String> deleteSection(@PathVariable("id") long sectionId) {
        sectionService.deleteSection(sectionId);
        return new ResponseEntity<>("Section deleted",HttpStatus.OK);
    }

    @DeleteMapping("/sections")
    public ResponseEntity<String> deleteAllSections() {
        sectionService.deleteAllSection();
        return new ResponseEntity<>("All Sections deleted",HttpStatus.OK);
    }
}
