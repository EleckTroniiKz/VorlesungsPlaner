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
public class LectureDateController implements ILectureDateController{

    private LectureDateService lectureDateService;
    private LecturerService lecturerService;
    private SemesterService semesterService;
    private LectureService lectureService;
    LectureDateController(LecturerService _lecturerService, LectureDateService _lectureDateService, SemesterService _semesterService, LectureService _lectureService){
        lecturerService=_lecturerService;
        lectureDateService=_lectureDateService;
        semesterService=_semesterService;
        lectureService=_lectureService;
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("lecturedates", lectureDateService.findAll());
        return "lecturedate-list";
    }

    @GetMapping("/create")
    public String createLectureDate(Model model) {
        model.addAttribute("lecturedate", new LectureDate());
        model.addAttribute("lecturers",lecturerService.findAll());
        model.addAttribute("semesters",semesterService.findAll());
        model.addAttribute("lectures",lectureService.findAll());
        return "create-lecturedate";
    }

    @PostMapping
    public String createLectureDate(@Valid LectureDate lectureDate, Errors errors) {
        if(errors.hasErrors()){
            return "create-lecturedate";
        }
        else{
            lectureDateService.save(lectureDate);
            return "redirect:/lecturedates";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) throws ResourceNotFoundException {
        Optional<LectureDate> lectureDateToEdit = lectureDateService.findOne(id);
        if(!lectureDateToEdit.isPresent()){
            throw new ResourceNotFoundException("Vorlesungs Datum wurde nicht gefunden!");
        }
        else{
            model.addAttribute("lecturedate", lectureDateToEdit.get());
            model.addAttribute("lecturers",lecturerService.findAll());
            model.addAttribute("semesters",semesterService.findAll());
            model.addAttribute("lectures",lectureService.findAll());
            return "update-lecturedate";
        }

    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @Valid LectureDate lectureDate, Errors errors) {
        if(errors.hasErrors()){
            return "redirect:/lecturedates/"+id;
        }
        else{
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
    @RequestMapping(value="/getLectureDate", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<LectureDate> getLectureDate(@PathVariable Long id) throws ResourceNotFoundException {
        if(lectureDateService.findOne(id).isPresent()){
            return ResponseEntity.ok(lectureDateService.findOne(id).get());
        }
        else{
            return new ResponseEntity<LectureDate>(HttpStatus.OK);
        }
    }

    @RequestMapping(value="/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Void> deleteLectureDate(@PathVariable Long id) throws ResourceNotFoundException{

        lectureDateService.delete(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value="/post", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<LectureDate> createLectureDate(LectureDate lectureDate) throws BadRequestException, URISyntaxException{
        LectureDate date = null;
        try{
            date = lectureDateService.save(lectureDate);
        }catch(Error e){
            throw new BadRequestException("Something went wrong with the saving process");
        }
        return ResponseEntity.ok(date);
    }

    @RequestMapping(value="getAllLectureDates", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<LectureDate>> getAlllecturedates() {
        return ResponseEntity.ok(lectureDateService.findAll());
    }

    @RequestMapping(value="/updateLectureDate", method=RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<LectureDate> updateLectureDate(LectureDate lectureDate) throws BadRequestException {
        //wie soll das updaten ablaufen?
        return null;
    }

    @RequestMapping(value="/updateLectureDateWithID", method=RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<LectureDate> updateLectureDate(Long id, LectureDate lecturedateDetails) throws ResourceNotFoundException {
        return null;
    }

}
