package de.digitra.uniplaner.controller;

import de.digitra.uniplaner.domain.LectureDate;
import de.digitra.uniplaner.exceptions.BadRequestException;
import de.digitra.uniplaner.exceptions.ResourceNotFoundException;
import de.digitra.uniplaner.interfaces.ILectureDateController;
import de.digitra.uniplaner.service.LectureDateService;
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
    @Autowired
    private LectureDateService lectureDateService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("lecturedates", lectureDateService.findAll());
        return "lecturedate-list";
    }

    @GetMapping("/create")
    public String createLectureDate(Model model) {
        model.addAttribute("lecturedate", new LectureDate());
        return "create-lecturedate";
    }

    @PostMapping
    public String createLectureDate(@Valid LectureDate lectureDate, Errors errors) {
        if(errors.hasErrors()){
            return "create-lecturedate";
        }
        else{
            lectureDateService.save(lectureDate);
            return "redirect:/lecturedate-list";
        }
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable Long id, Model model) throws ResourceNotFoundException {
        Optional<LectureDate> lectureDateToEdit = lectureDateService.findOne(id);
        model.addAttribute("lecturedate", lectureDateToEdit);
        return "update-lecturedate";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @Valid LectureDate lectureDate, Errors errors) {
        if(errors.hasErrors()){
            return "update-lecturedate";
        }
        else{
            lectureDateService.delete(id);
            lectureDateService.save(lectureDate);
            return "redirect:/lecturedate";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        lectureDateService.delete(id);
        return "redirect:/lecturedate";
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
