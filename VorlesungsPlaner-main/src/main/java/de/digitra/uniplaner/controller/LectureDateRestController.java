package de.digitra.uniplaner.controller;

import de.digitra.uniplaner.domain.LectureDate;
import de.digitra.uniplaner.exceptions.BadRequestException;
import de.digitra.uniplaner.exceptions.ResourceNotFoundException;
import de.digitra.uniplaner.interfaces.ILectureDateController;
import de.digitra.uniplaner.service.LectureDateService;
import de.digitra.uniplaner.service.LectureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/web/lecturedates")
public class LectureDateRestController implements ILectureDateController {

    private LectureDateService lectureDateService;

    public LectureDateRestController(LectureDateService lectureDateService){
        this.lectureDateService = lectureDateService;
    }

    @PostMapping("/createLectureDateRest")
    public ResponseEntity<LectureDate> createLectureDate(@Valid @RequestBody LectureDate lectureDate) throws BadRequestException {
        if(lectureDate.getId() != null) {
            throw new BadRequestException("LectureDate ist nicht zulässig!");
        }
        else{
            return ResponseEntity.ok(lectureDateService.save(lectureDate));
        }
    }

    @PutMapping("/updateLectureDateRest")
    public ResponseEntity<LectureDate> updateLectureDate(@RequestBody LectureDate lectureDate) throws BadRequestException {
        if(lectureDate.getId() != null){
            lectureDateService.delete(lectureDate.getId());
            return ResponseEntity.ok(lectureDateService.save(lectureDate));
        }
        else{
            throw new BadRequestException("LectureDate nicht zulässig!");
        }
    }

    @PutMapping("/updateLectureDateByID/{id}")
    public ResponseEntity<LectureDate> updateLectureDate(@PathVariable Long id, @Valid @RequestBody LectureDate lecturedateDetails) throws ResourceNotFoundException {
        if(lectureDateService.findOne(id).isPresent()){
            lectureDateService.delete(id);
            return ResponseEntity.ok(lectureDateService.save(lecturedateDetails));
        }
        else{
            throw new ResourceNotFoundException("LectureDate mit der id nicht gefunden!");
        }
    }

    @GetMapping("/getAllLectureDates")
    public ResponseEntity<List<LectureDate>> getAlllecturedates() {
        return ResponseEntity.ok(lectureDateService.findAll());
    }


    @GetMapping("/getLectureDateById/{id}")
    public ResponseEntity<LectureDate> getLectureDate(@PathVariable Long id) throws ResourceNotFoundException {
        if (lectureDateService.findOne(id).isPresent()) {
            return ResponseEntity.ok(lectureDateService.findOne(id).get());
        } else {
            throw new ResourceNotFoundException("LectureDate mit dieser ID nicht gefunden");
        }
    }

    @DeleteMapping("/deleteLectureDate/{id}")
    public ResponseEntity<Void> deleteLectureDate(@PathVariable Long id){
        lectureDateService.delete(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }




}
