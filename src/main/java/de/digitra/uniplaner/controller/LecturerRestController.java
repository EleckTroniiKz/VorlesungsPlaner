package de.digitra.uniplaner.controller;

import de.digitra.uniplaner.domain.Lecture;
import de.digitra.uniplaner.domain.LectureDate;
import de.digitra.uniplaner.domain.Lecturer;
import de.digitra.uniplaner.domain.StudyProgram;
import de.digitra.uniplaner.exceptions.BadRequestException;
import de.digitra.uniplaner.exceptions.DuplicateEmailException;
import de.digitra.uniplaner.exceptions.ResourceNotFoundException;
import de.digitra.uniplaner.interfaces.ILecturerController;
import de.digitra.uniplaner.service.LectureDateService;
import de.digitra.uniplaner.service.LectureService;
import de.digitra.uniplaner.service.LecturerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("web/lecturers")
public class LecturerRestController implements ILecturerController {

    private LecturerService lecturerService;
    private LectureService lectureService;
    private LectureDateService lectureDateService;


    public LecturerRestController(LecturerService lecturerService, LectureDateService lectureDateService,LectureService lectureService){
        this.lecturerService = lecturerService;
        this.lectureDateService = lectureDateService;
        this.lectureService = lectureService;
    }

    @PostMapping("/createLecturerRest")
    public ResponseEntity<Lecturer> createLecturer(@RequestBody Lecturer lecturer) throws BadRequestException, DuplicateEmailException {
        if(lecturer.getId() == null){
            String email = lecturer.getEmail();
            List<Lecturer> list = lecturerService.findAll();
            Boolean emailExists = false;
            for(int i = 0; i < list.size(); i++) {
                if (email == list.get(i).getEmail()) {
                    emailExists = true;
                }
            }
            if(emailExists == false){
                return ResponseEntity.ok(lecturerService.save(lecturer));
            }
            else{
                throw new DuplicateEmailException("Es existiert schon ein Dozent mit dieser E-Mail");
            }
        }
        else{
            throw new BadRequestException("Lecturer ist nicht zulässig!");
        }
    }

    @PutMapping("/updateLecturerRest")
    public ResponseEntity<Lecturer> updateLecturer(@RequestBody Lecturer lecturer) throws BadRequestException {
        if (lecturerService.findOne(lecturer.getId()) != null) {
            lecturerService.delete(lecturer.getId());
            return ResponseEntity.ok(lecturerService.save(lecturer));
        } else {
            throw new BadRequestException("Lecturer ist nicht zulässig!");
        }
    }

    @PutMapping("/updateLecturerById/{id}")
    public ResponseEntity<Lecturer> updateLecturer(@PathVariable(value = "id") Long id, @Valid @RequestBody Lecturer lecturerDetails) throws ResourceNotFoundException {
        Lecturer temp = null;
        if (lecturerService.findOne(id).isPresent()) {
            lecturerService.delete(id);
            return ResponseEntity.ok(lecturerService.save(lecturerDetails));
        } else {
            throw new ResourceNotFoundException("Couldn'T find Lecturer");
        }
    }

    @GetMapping("/getAllLecturers")
    public ResponseEntity<List<Lecturer>> getAlllecturers() {
        return ResponseEntity.ok(lecturerService.findAll());
    }

    @GetMapping("/getLecturerById/{id}")
    public ResponseEntity<Lecturer> getLecturer(@PathVariable Long id) throws ResourceNotFoundException {
        if (lecturerService.findOne(id).isPresent()) {
            return ResponseEntity.ok(lecturerService.findOne(id).get());
        } else {
            throw new ResourceNotFoundException("No Lecturer with that ID found!");
        }
    }

    @DeleteMapping("/deleteLecturer/{id}")
    public ResponseEntity<Void> deleteLecturer(@PathVariable Long id) {
        if (lecturerService.findOne(id).isPresent()) {
            Lecturer lecturer = lecturerService.findOne(id).get();

            Set<LectureDate> lecturerDates = lecturer.getLectureDates();
            if(lecturerDates!=null){
                for (LectureDate lectureDate: lecturerDates
                     ) {
                    lectureDate.setLecturer(null);
                    lectureDateService.save(lectureDate);
                }
            }

            Set<Lecture> lectures = lecturer.getLectures();
            if(lectures!=null){
                for (Lecture lecture: lectures
                ) {
                    Set<Lecturer> lecturers = lecture.getLecturers();
                    lecturers.remove(lecturer);
                    lecture.setLecturers(lecturers);
                    lectureService.save(lecture);
                }
            }

            StudyProgram studyProgram = lecturer.getStudyProgram();
            if(studyProgram!=null){
                studyProgram.removeLecturer(lecturer);
            }

            lecturerService.delete(id);
        }
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
