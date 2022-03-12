package de.digitra.uniplaner.controller;

import de.digitra.uniplaner.domain.Lecturer;
import de.digitra.uniplaner.exceptions.BadRequestException;
import de.digitra.uniplaner.exceptions.DuplicateEmailException;
import de.digitra.uniplaner.exceptions.ResourceNotFoundException;
import de.digitra.uniplaner.interfaces.ILecturerController;
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
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/lecturers")
public class LecturerController implements ILecturerController {

    private LecturerService lecturerService;
    private StudyProgramService studyProgramService;
    private LectureService lectureService;

    LecturerController(LecturerService _lecturerService, StudyProgramService _studyProgramService, LectureService _lectureService){
        lecturerService =  _lecturerService;
        studyProgramService = _studyProgramService;
        lectureService=_lectureService;

    }


    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("lecturers", lecturerService.findAll());
        return "lecturer-list";
    }

    @GetMapping("/create")
    public String createLecturer(Model model) {
        model.addAttribute("lecturer", new Lecturer());
        model.addAttribute("studyPrograms", studyProgramService.findAll());
        return "create-lecturer";
    }

    @PostMapping
    public String createLecturer(@Valid Lecturer lecturer, Errors errors) {
        if(errors.hasErrors()){
            return "redirect:/lecturers/create";
        }
        else{
            lecturerService.save(lecturer);
            return "redirect:/lecturers";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) throws ResourceNotFoundException {
        System.out.println("test");
        Optional<Lecturer> lecturerToEdit = lecturerService.findOne(id);
        if(!lecturerToEdit.isPresent()){
            throw new ResourceNotFoundException("Dozent wurde nicht gefunden!");
        }
        else {
            model.addAttribute("lecturer", lecturerToEdit.get());
            model.addAttribute("lectures", lectureService.findAll());
            model.addAttribute("studyPrograms", studyProgramService.findAll());
            return "update-lecturer";
        }
    }

    @PutMapping("/edit/{id}")
    public String update(@PathVariable Long id, @Valid Lecturer lecturer, Errors errors) {
        if(errors.hasErrors()){
            return "redirect:/lecturers/edit/"+id;
        }
        else{
            lecturerService.delete(id);
            lecturerService.save(lecturer);
            return "redirect:/lecturers";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        lecturerService.delete(id);
        return "redirect:/lecturers";
    }

    @GetMapping("/search")
    public String searchLecturers(Model model, @RequestParam(required = false) Optional<Boolean> completed) {
        List<Lecturer> lecturer = lecturerService.findAll();
        model.addAttribute("lecturer", lecturer);
        return "redirect:/lecturers";
    }
//----------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value= "/post", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Lecturer> createLecturer(Lecturer lecturer) throws BadRequestException, DuplicateEmailException, URISyntaxException {
        Lecturer doz = null;
        if(lecturerService.findOne(lecturer.getId()).isPresent() || !lecturerService.findByEmail(lecturer.getEmail()).isEmpty()) {
            throw new BadRequestException("Lecturer already exists");
        }
        try{
            doz = lecturerService.save(lecturer);
        } catch(HttpServerErrorException.InternalServerError e){
            e.printStackTrace();
            throw new Error("Couldn't save");
        }
        return ResponseEntity.ok(doz);
    }

    @RequestMapping(value="/put/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Lecturer> updateLecturer(@PathVariable(value = "id") Long id, @Valid Lecturer lecturerDetails) throws ResourceNotFoundException {
        Lecturer lect = null;
        if(lecturerService.findOne(id).isPresent()) {
            lect = lecturerService.findOne(id).get();
            lecturerService.delete(id);
            return ResponseEntity.ok(lecturerService.save(lecturerDetails));
        }
        else{
            return new ResponseEntity<Lecturer>(HttpStatus.OK);
        }
    }

    @RequestMapping(value="/put", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Lecturer> updateLecturer(Lecturer lecturer) throws BadRequestException {
        Lecturer lect = null;
        if(lecturerService.findOne(lecturer.getId()).isPresent()) {
            lect = lecturerService.findOne(lecturer.getId()).get();
            lecturerService.delete(lecturer.getId());
            return ResponseEntity.ok(lecturerService.save(lecturer));
        }
        else{
            return new ResponseEntity<Lecturer>(HttpStatus.OK);
        }
    }

    @RequestMapping(value="/get", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Lecturer>> getAlllecturers() {
        return ResponseEntity.ok(lecturerService.findAll());
    }

    @RequestMapping(value="/getLecturer", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Lecturer> getLecturer(@PathVariable Long id) throws ResourceNotFoundException {
        if(lecturerService.findOne(id).isPresent()){
            return ResponseEntity.ok(lecturerService.findOne(id).get());
        }
        else {
            new ResourceNotFoundException("No Lecturer with that ID found!");
            return new ResponseEntity<Lecturer>(HttpStatus.OK);
        }
    }

    @RequestMapping(value="/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Void> deleteLecturer(@PathVariable Long id) {
        if (lecturerService.findOne(id).isPresent()) {
            lecturerService.delete(id);
        }
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
