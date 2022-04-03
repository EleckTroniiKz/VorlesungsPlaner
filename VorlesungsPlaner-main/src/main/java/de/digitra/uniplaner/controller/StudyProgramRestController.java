package de.digitra.uniplaner.controller;

import de.digitra.uniplaner.domain.StudyProgram;
import de.digitra.uniplaner.exceptions.BadRequestException;
import de.digitra.uniplaner.exceptions.ResourceNotFoundException;
import de.digitra.uniplaner.interfaces.IStudyProgramController;
import de.digitra.uniplaner.service.StudyClassService;
import de.digitra.uniplaner.service.StudyProgramService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/web/studyprograms")
public class StudyProgramRestController implements IStudyProgramController {

    private StudyProgramService studyProgramService;

    public StudyProgramRestController(StudyProgramService studyProgramService){
        this.studyProgramService = studyProgramService;
    }

    @PostMapping("/createStudyProgramRest")
    public ResponseEntity<StudyProgram> createStudyProgram(@RequestBody StudyProgram studyprogram) throws BadRequestException {
        if(studyprogram.getId() != null){
            throw new BadRequestException("StudyProgram nicht zulässig!");
        }
        else{
            return ResponseEntity.ok(studyProgramService.save(studyprogram));
        }
    }

    @PutMapping ("/updateStudyProgramRest")
    public ResponseEntity<StudyProgram> updateStudyProgram(@RequestBody StudyProgram studyprogram) throws BadRequestException {
        if(studyprogram.getId() != null){
            studyProgramService.delete(studyprogram.getId());
            return ResponseEntity.ok(studyProgramService.save(studyprogram));
        }
        else{
            throw new BadRequestException("");
        }
    }

    @PutMapping("/updateStudyProgramByID/{id}")
    public ResponseEntity<StudyProgram> updateStudyProgram(@PathVariable Long id, @Valid @RequestBody StudyProgram studyprogramDetails) throws ResourceNotFoundException {
        if(studyProgramService.findOne(id).isPresent()){
            studyProgramService.delete(id);
            return ResponseEntity.ok(studyProgramService.save(studyprogramDetails));
        }
        else{
            throw new ResourceNotFoundException("StudyProgram not found!");
        }
    }

    @GetMapping("/getAllStudyPrograms")
    public ResponseEntity<List<StudyProgram>> getAllstudyprograms() {
        return ResponseEntity.ok(studyProgramService.findAll());
    }

    @GetMapping("/getStudyProgramById/{id}")
    public ResponseEntity<StudyProgram> getStudyProgram(@PathVariable Long id) throws ResourceNotFoundException {
        if(studyProgramService.findOne(id).isPresent()){
            return ResponseEntity.ok(studyProgramService.findOne(id).get());
        }
        else{
            throw new ResourceNotFoundException("");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteStudyProgram(@PathVariable Long id) {
        studyProgramService.delete(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
