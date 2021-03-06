package de.digitra.uniplaner.controller;

import de.digitra.uniplaner.domain.LectureDate;
import de.digitra.uniplaner.domain.Semester;
import de.digitra.uniplaner.domain.StudyClass;
import de.digitra.uniplaner.exceptions.BadRequestException;
import de.digitra.uniplaner.exceptions.ResourceNotFoundException;
import de.digitra.uniplaner.interfaces.ISemesterController;
import de.digitra.uniplaner.service.LectureDateService;
import de.digitra.uniplaner.service.LecturerService;
import de.digitra.uniplaner.service.SemesterService;
import de.digitra.uniplaner.service.StudyClassService;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("web/semesters" +
        "")
public class SemesterRestController implements ISemesterController {

    private SemesterService semesterService;
    private StudyClassService studyClassService;
    private LectureDateService lectureDateService;

    public SemesterRestController(SemesterService semesterService, StudyClassService studyClassService, LectureDateService lectureDateService){
        this.semesterService = semesterService;
        this.studyClassService = studyClassService;
        this.lectureDateService = lectureDateService;
    }

    @PostMapping("/createSemesterRest")
    public ResponseEntity<Semester> createSemester(@RequestBody Semester semester) throws BadRequestException {
        if (semester.getId() != null) {
            throw new BadRequestException("Semester nicht zulässig!");
        }
        else{
            return ResponseEntity.ok(semesterService.save(semester));
        }
    }

    @PutMapping("/updateSemesterRest")
    public ResponseEntity<Semester> updateSemester(@RequestBody Semester semester) throws BadRequestException {
        if(semester.getId() != null){
            semesterService.delete(semester.getId());
            return ResponseEntity.ok(semesterService.save(semester));
        }
        else{
            throw new BadRequestException("Semester ist nicht zulässig");
        }
    }

    @PutMapping("/updateSemesterById/{id}")
    public ResponseEntity<Semester> updateSemester(@PathVariable(value = "id") Long id, @Valid @RequestBody Semester semesterDetails) throws ResourceNotFoundException {
        if(semesterService.findOne(id).isPresent()){
            semesterService.delete(id);
            return ResponseEntity.ok(semesterService.save(semesterDetails));
        }
        else{
            throw new ResourceNotFoundException("Semester konnte nicht gefunden werden!");
        }
    }

    @GetMapping("/getAllSemesters")
    public ResponseEntity<List<Semester>> getAllsemesters() {
        return ResponseEntity.ok(semesterService.findAll());
    }

    @GetMapping("/getSemesterById/{id}")
    public ResponseEntity<Semester> getSemester(@PathVariable Long id) throws ResourceNotFoundException {
        Semester sem = null;
        if (semesterService.findOne(id).isPresent()) {
            return ResponseEntity.ok(semesterService.findOne(id).get());
        } else {
            throw new ResourceNotFoundException("Semester not found");
        }
    }

    @DeleteMapping("/deleteSemester/{id}")
    public ResponseEntity<Void> deleteSemester(@PathVariable Long id) {
        if(semesterService.findOne(id).isPresent()){
            Semester semester = semesterService.findOne(id).get();

            //LectureDate Update
            Set<LectureDate> lectureDates = semester.getLectureDates();
            if(lectureDates != null){
                lectureDates.remove(semester);
                for( LectureDate lectureDate: lectureDates){
                    lectureDate.setSemester(null);
                    lectureDateService.save(lectureDate);
                }
            }

            //StudyClass Update
            StudyClass studyClass = semester.getStudyClass();
            if(studyClass!=null){
                studyClass.removeSemester(semester);
            }
            semesterService.delete(id);
        }
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
