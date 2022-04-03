package de.digitra.uniplaner.controller;

import de.digitra.uniplaner.domain.Semester;
import de.digitra.uniplaner.domain.StudyClass;
import de.digitra.uniplaner.domain.StudyProgram;
import de.digitra.uniplaner.exceptions.BadRequestException;
import de.digitra.uniplaner.exceptions.ResourceNotFoundException;
import de.digitra.uniplaner.interfaces.IStudyClassController;
import de.digitra.uniplaner.service.SemesterService;
import de.digitra.uniplaner.service.StudyClassService;
import de.digitra.uniplaner.service.StudyProgramService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/web/studyclass")
public class StudyClassRestController implements IStudyClassController {

    private StudyClassService studyClassService;
    private StudyProgramService studyProgramService;
    private SemesterService semesterService;

    public StudyClassRestController(StudyClassService studyClassService,StudyProgramService studyProgramService,SemesterService semesterService){
        this.studyClassService = studyClassService;
        this.studyProgramService = studyProgramService;
        this.semesterService = semesterService;
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
        if(studyClassService.findOne(id).isPresent()){
            StudyClass studyClass = studyClassService.findOne(id).get();

            StudyProgram studyProgram = studyClass.getStudyProgram();
            if(studyProgram!=null){
                Set<StudyClass> studyClasses = studyProgram.getStudyClasses();
                studyClasses.remove(studyProgram);
                studyProgram.setStudyClasses(studyClasses);
                studyProgramService.save(studyProgram);
            }
            Set<Semester> semesters = studyClass.getSemesters();
            if(semesters!= null){
                for (Semester semester: semesters
                     ) {
                    semester.setStudyClass(null);
                    semesterService.save(semester);
                }
            }

            studyClassService.delete(id);
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
