package de.digitra.uniplaner.controller;

import de.digitra.uniplaner.domain.LectureDate;
import de.digitra.uniplaner.exceptions.BadRequestException;
import de.digitra.uniplaner.exceptions.ResourceNotFoundException;
import de.digitra.uniplaner.interfaces.ILectureDateController;
import de.digitra.uniplaner.service.LectureDateService;
import de.digitra.uniplaner.service.LectureService;
import de.digitra.uniplaner.service.LecturerService;
import de.digitra.uniplaner.service.SemesterService;
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
@RequestMapping("/lecturedates")
public class LectureDateController implements ILectureDateController {

    private LectureDateService lectureDateService;
    private LecturerService lecturerService;
    private SemesterService semesterService;
    private LectureService lectureService;

    LectureDateController(LecturerService _lecturerService, LectureDateService _lectureDateService, SemesterService _semesterService, LectureService _lectureService) {
        lecturerService = _lecturerService;
        lectureDateService = _lectureDateService;
        semesterService = _semesterService;
        lectureService = _lectureService;
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("lecturedates", lectureDateService.findAll());
        return "lecturedate-list";
    }

    @GetMapping("/create")
    public String createLectureDate(Model model) {
        model.addAttribute("lecturedate", new LectureDate());
        model.addAttribute("lecturers", lecturerService.findAll());
        model.addAttribute("semesters", semesterService.findAll());
        model.addAttribute("lectures", lectureService.findAll());
        return "create-lecturedate";
    }

    @PostMapping
    public String createLectureDate(@Valid LectureDate lectureDate, Errors errors) {
        if (errors.hasErrors()) {
            return "create-lecturedate";
        } else {
            lectureDateService.save(lectureDate);
            return "redirect:/lecturedates";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) throws ResourceNotFoundException {
        Optional<LectureDate> lectureDateToEdit = lectureDateService.findOne(id);
        if (!lectureDateToEdit.isPresent()) {
            throw new ResourceNotFoundException("Vorlesungs Datum wurde nicht gefunden!");
        } else {
            model.addAttribute("lecturedate", lectureDateToEdit.get());
            model.addAttribute("lecturers", lecturerService.findAll());
            model.addAttribute("semesters", semesterService.findAll());
            model.addAttribute("lectures", lectureService.findAll());
            return "update-lecturedate";
        }

    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @Valid LectureDate lectureDate, Errors errors) {
        if (errors.hasErrors()) {
            return "redirect:/lecturedates/" + id;
        } else {
            lectureDateService.delete(id);
            lectureDateService.save(lectureDate);
            return "redirect:/lecturedates";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        lectureDateService.delete(id);
        return "redirect:/lecturedates";
    }

    //------------------------------------------------------------------------------------------------------------------

    @RequestMapping(value = "/CreateLectureDate", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<LectureDate> createLectureDate(@RequestBody LectureDate lectureDate) throws BadRequestException {
        LectureDate date = null;
        if(lectureDate.getId() != null) {
            return ResponseEntity.ok(lectureDateService.save(lectureDate));
        }
        throw new BadRequestException("LectureDate ist nicht zulässig!");
    }

    @RequestMapping(value = "/updateLectureDate", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<LectureDate> updateLectureDate(@RequestBody LectureDate lectureDate) throws BadRequestException {
        if(lectureDate.getId() != null){
            LectureDate temp = lectureDate;
            lectureDateService.delete(lectureDate.getId());
            return ResponseEntity.ok(lectureDateService.save(temp));
        }
        else{
            throw new BadRequestException("LectureDate nicht zulässig!");
        }
    }

    @RequestMapping(value = "/updateLectureDateWithID/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<LectureDate> updateLectureDate(@PathVariable Long id, @Valid @RequestBody LectureDate lecturedateDetails) throws ResourceNotFoundException {
        if(lectureDateService.findOne(id).isPresent()){
            LectureDate temp = lecturedateDetails;
            lectureDateService.delete(id);
            return ResponseEntity.ok(lectureDateService.save(temp));
        }
        else{
            throw new ResourceNotFoundException("LectureDate mit der id nicht gefunden!");
        }
    }

    @RequestMapping(value = "/getAllLectureDates", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<LectureDate>> getAlllecturedates() {
        return ResponseEntity.ok(lectureDateService.findAll());
    }

    @RequestMapping(value = "/getLectureDate/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<LectureDate> getLectureDate(@PathVariable Long id) throws ResourceNotFoundException {
        if (lectureDateService.findOne(id).isPresent()) {
            return ResponseEntity.ok(lectureDateService.findOne(id).get());
        } else {
            throw new ResourceNotFoundException("LectureDate mit dieser ID nicht gefunden");
        }
    }

    @RequestMapping(value = "/deleteLectureDate/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Void> deleteLectureDate(@PathVariable Long id){
        lectureDateService.delete(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }




}
