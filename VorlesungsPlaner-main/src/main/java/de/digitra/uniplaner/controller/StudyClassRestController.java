package de.digitra.uniplaner.controller;

import de.digitra.uniplaner.domain.StudyClass;
import de.digitra.uniplaner.exceptions.BadRequestException;
import de.digitra.uniplaner.exceptions.ResourceNotFoundException;
import de.digitra.uniplaner.interfaces.IStudyClassController;
import de.digitra.uniplaner.service.SemesterService;
import de.digitra.uniplaner.service.StudyClassService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/web/studyclass")
public class StudyClassRestController implements IStudyClassController {

    private StudyClassService studyClassService;

    public StudyClassRestController(StudyClassService studyClassService){
        this.studyClassService = studyClassService;
    }

    @PostMapping("/createStudyClassRest")
    public ResponseEntity<StudyClass> createStudyClass(@RequestBody StudyClass studyclass) throws BadRequestException {
        if (studyclass.getId() != null) {
            throw new BadRequestException("StudyClass invalid!");
        } else {
            return ResponseEntity.ok(studyClassService.save(studyclass));
        }
    }

    @PutMapping("/updateStudyClassRest")
    public ResponseEntity<StudyClass> updateStudyClass(@RequestBody StudyClass studyclass) throws BadRequestException {
        if (studyclass.getId() == null) {
            studyClassService.delete(studyclass.getId());
            return ResponseEntity.ok(studyClassService.save(studyclass));
        } else {
            throw new BadRequestException("StudyClass invalid!");
        }
    }

    @PutMapping("/updateStudyClassById/{id}")
    public ResponseEntity<StudyClass> updateStudyClass(@PathVariable Long id, @Valid @RequestBody StudyClass studyclassDetails) throws ResourceNotFoundException {
        if (studyClassService.findOne(id).isPresent()) {
            studyClassService.delete(id);
            return ResponseEntity.ok(studyclassDetails);
        } else {
            throw new ResourceNotFoundException("StudyClass invalid!");
        }
    }

    @GetMapping("/getAllStudyClasses")
    public ResponseEntity<List<StudyClass>> getAllstudyclasss() {
        return ResponseEntity.ok(studyClassService.findAll());
    }

    @GetMapping("/getStudyClassById/{id}")
    public ResponseEntity<StudyClass> getStudyClass(@PathVariable Long id) throws ResourceNotFoundException {
        StudyClass ls = null;
        if(studyClassService.findOne(id).isPresent()){
            return ResponseEntity.ok(studyClassService.findOne(id).get());
        }
        else{
            throw new ResourceNotFoundException("StudyClass not found!");
        }
    }

    @DeleteMapping("/deleteStudyClass/{id}")
    public ResponseEntity<Void> deleteStudyClass(@PathVariable Long id) {
        studyClassService.delete(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
