package de.digitra.uniplaner.controller;

import de.digitra.uniplaner.domain.Lecture;
import de.digitra.uniplaner.exceptions.BadRequestException;
import de.digitra.uniplaner.exceptions.ResourceNotFoundException;
import de.digitra.uniplaner.interfaces.ILectureController;
import de.digitra.uniplaner.service.LectureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("web/lectures")
public class LectureRestController  implements ILectureController {

    private LectureService lectureService;

    public LectureRestController(LectureService lectureService){
        this.lectureService = lectureService;
    }

    @PostMapping("/createLectureRest")
    public ResponseEntity<Lecture> createLecture(@Valid @RequestBody Lecture lecture) throws BadRequestException {
        if(lecture.getId() != null){
            throw new BadRequestException("Lecture ist nicht Zulässig!");
        }
        else{
            return ResponseEntity.ok(lectureService.save(lecture));
        }
    }

    @PutMapping("/updateLectureByID/{id}")
    public ResponseEntity<Lecture> updateLecture(@PathVariable(value = "id") Long id, @Valid @RequestBody Lecture lectureDetails) throws ResourceNotFoundException {
        if (lectureService.findOne(id).isPresent()) {
            lectureService.delete(id);
            return ResponseEntity.ok(lectureService.save(lectureDetails));
        } else {
            throw new ResourceNotFoundException("Lecture not found!");
        }
    }

    @PutMapping("/updateLectureRest")
    public ResponseEntity<Lecture> updateLecture(@RequestBody Lecture lecture) throws BadRequestException {
        if (lecture.getId() != null) {
            lectureService.delete(lecture.getId());
            return ResponseEntity.ok(lectureService.save(lecture));
        }
        else{
            throw new BadRequestException(("Lecture ist nicht zulässig"));
        }
    }

    @GetMapping("/getAllLectures")
    public ResponseEntity<List<Lecture>> getAlllectures() {
        return ResponseEntity.ok(lectureService.findAll());
    }

    @GetMapping("/getLectureById/{id}")
    public ResponseEntity<Lecture> getLecture(@PathVariable Long id) throws ResourceNotFoundException {
        if (lectureService.findOne(id).isPresent()) {
            return ResponseEntity.ok(lectureService.findOne(id).get());
        } else {
            throw new ResourceNotFoundException("Es wurde keine Lecture mit der Id gefunden");
        }
    }

    @DeleteMapping("/deleteLecture/{id}")
    public ResponseEntity<Void> deleteLecture(@PathVariable Long id) {
        lectureService.delete(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
