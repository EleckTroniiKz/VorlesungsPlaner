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
public class LecturerController{

    private LecturerService lecturerService;
    private StudyProgramService studyProgramService;
    private LectureService lectureService;

    LecturerController(LecturerService _lecturerService, StudyProgramService _studyProgramService, LectureService _lectureService) {
        lecturerService = _lecturerService;
        studyProgramService = _studyProgramService;
        lectureService = _lectureService;

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
        model.addAttribute("lectures", lectureService.findAll());
        return "create-lecturer";
    }

    @PostMapping
    public String createLecturer(@Valid Lecturer lecturer, Errors errors) {
        if (errors.hasErrors()) {
            return "redirect:/lecturers/create";
        } else {
            lecturerService.save(lecturer);
            return "redirect:/lecturers";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) throws ResourceNotFoundException {

        Optional<Lecturer> lecturerToEdit = lecturerService.findOne(id);
        if (lecturerToEdit.isPresent()) {
            model.addAttribute("lecturer", lecturerToEdit.get());
            model.addAttribute("studyPrograms", studyProgramService.findAll());
            model.addAttribute("lectures", lectureService.findAll());
            return "update-lecturer";
        } else {
            return "redirect:/lecturers";
        }

    }

    @PutMapping("/edit/{id}")
    public String update(@PathVariable Long id, @Valid Lecturer lecturer, Errors errors) {
        if (errors.hasErrors()) {
            return "redirect:/lecturers/edit/" + id;
        } else {
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

}
