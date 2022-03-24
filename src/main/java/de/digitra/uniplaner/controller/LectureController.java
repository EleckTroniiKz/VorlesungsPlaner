package de.digitra.uniplaner.controller;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import de.digitra.uniplaner.domain.Lecture;
import de.digitra.uniplaner.domain.StudyProgram;
import de.digitra.uniplaner.exceptions.BadRequestException;
import de.digitra.uniplaner.exceptions.ResourceNotFoundException;
import de.digitra.uniplaner.interfaces.ILectureController;
import de.digitra.uniplaner.service.LectureService;
import de.digitra.uniplaner.service.LecturerService;
import de.digitra.uniplaner.service.StudyProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/lectures")
public class LectureController implements ILectureController {


    private LectureService lectureService;
    private StudyProgramService studyProgramService;
    private LecturerService lecturerService;

    LectureController(LectureService _lectureService, StudyProgramService _studyProgramService, LecturerService _lecturerService) {
        lectureService = _lectureService;
        studyProgramService = _studyProgramService;
        lecturerService = _lecturerService;
    }


    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("lectures", lectureService.findAll());
        return "lecture-list";
    }

    @GetMapping("/create")
    public String createLecture(Model model) {
        model.addAttribute("lecture", new Lecture());
        model.addAttribute("studyPrograms", studyProgramService.findAll());
        model.addAttribute("lecturers", lecturerService.findAll());
        return "create-lecture";
    }

    @PostMapping
    public String createLecture(@Valid Lecture lecture, Errors errors) {
        if (errors.hasErrors()) {
            return "create-lecture";

        } else {
            lectureService.save(lecture);
            return "redirect:/lectures";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) throws ResourceNotFoundException {
        Optional<Lecture> lectureToEdit = lectureService.findOne(id);
        if (!lectureToEdit.isPresent()) {
            throw new ResourceNotFoundException("Vorlesung wurde nicht gefunden");
        } else {
            model.addAttribute("lecture", lectureToEdit.get());
            model.addAttribute("studyPrograms", studyProgramService.findAll());
            model.addAttribute("lecturers", lecturerService.findAll());
            return "update-lecture";
        }
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @Valid Lecture lecture, Errors errors) {
        if (errors.hasErrors()) {
            System.out.println("Error");
            return "redirect:/lectures/edit/" + id;

        } else {
            //lectureService.update(id, lecture);
            Lecture temp = lecture;
            lectureService.delete(id);
            lectureService.save(temp);
            return "redirect:/lectures";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        lectureService.delete(id);
        return "redirect:/lectures";
    }

    @GetMapping("/search")
    public String searchLectures(Model model, @RequestParam(required = false) Optional<Boolean> completed) {
        List<Lecture> lecture = lectureService.findAll();
        model.addAttribute("lectures", lecture);
        return "redirect:/lectures";
    }
//----------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/CreateLectureRest", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Lecture> createLecture(@RequestBody Lecture lecture) throws BadRequestException {
        if(lecture.getId() != null){
            throw new BadRequestException("Lecture ist nicht Zulässig!");
        }
        else{
            Lecture newLecture = lectureService.create(lecture);
            return ResponseEntity.ok(newLecture);
        }
    }

    @RequestMapping(value = "/UpdateLectureByID/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Lecture> updateLecture(@PathVariable(value = "id") Long id, @Valid @RequestBody Lecture lectureDetails) throws ResourceNotFoundException {
        Lecture lec = null;
        if (lectureService.findOne(id).isPresent()) {
            lec = lectureDetails;
            lectureService.delete(id);
            return ResponseEntity.ok(lectureService.save(lectureDetails));
        } else {
            throw new ResourceNotFoundException("Lecture not found!");
        }
    }

    @RequestMapping(value = "/UpdateLectureRest", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Lecture> updateLecture(@RequestBody Lecture lecture) throws BadRequestException {
        Lecture savedLecture = null;
        if (lecture.getId() != null) {
            lectureService.delete(lecture.getId());
            savedLecture = lectureService.save(lecture);

            return ResponseEntity.ok(savedLecture);
        }
        else{
            throw new BadRequestException(("Lecture ist nicht zulässig"));
        }
    }

    @RequestMapping(value = "/getAllLectures", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Lecture>> getAlllectures() {
        return ResponseEntity.ok(lectureService.findAll());
    }

    @RequestMapping(value = "/getLectureById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Lecture> getLecture(@PathVariable Long id) throws ResourceNotFoundException {
        Lecture temp = null;
        if (lectureService.findOne(id).isPresent()) {
            return ResponseEntity.ok(lectureService.findOne(id).get());
        } else {
            throw new ResourceNotFoundException("Lecture with that ID not found!");
        }
    }


    @RequestMapping(value = "/DeleteLecture/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Void> deleteLecture(@PathVariable Long id) {
        lectureService.delete(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
