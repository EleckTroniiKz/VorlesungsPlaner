package de.digitra.uniplaner.controller;

import de.digitra.uniplaner.domain.Semester;
import de.digitra.uniplaner.exceptions.BadRequestException;
import de.digitra.uniplaner.exceptions.ResourceNotFoundException;
import de.digitra.uniplaner.interfaces.ISemesterController;
import de.digitra.uniplaner.service.SemesterService;
import de.digitra.uniplaner.service.StudyClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
@RequestMapping("/semesters")
public class SemesterController{

    private SemesterService semesterService;
    private StudyClassService studyClassService;

    SemesterController(SemesterService _semesterService, StudyClassService _studyClassService) {
        semesterService = _semesterService;
        studyClassService = _studyClassService;
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("semesters", semesterService.findAll());
        return "semester-list";
    }

    @GetMapping("/create")
    public String createSemester(Model model) {

        model.addAttribute("semester", new Semester());
        model.addAttribute("studyClasss", studyClassService.findAll());

        return "create-semester";
    }

    @PostMapping
    public String createSemester(@Valid Semester semester, Errors errors) {
        if (errors.hasErrors()) {
            System.out.println(errors);
            return "redirect:/semesters/create";
        } else {
            semesterService.save(semester);
            return "redirect:/semesters";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) throws ResourceNotFoundException {
        Optional<Semester> semesterToEdit = semesterService.findOne(id);
        if (!semesterToEdit.isPresent()) {
            throw new ResourceNotFoundException("Semester wurde nicht gefunden!");
        } else {
            model.addAttribute("semester", semesterToEdit.get());
            model.addAttribute("studyClasss", studyClassService.findAll());
            return "update-semester";
        }
    }


    @PutMapping("/edit/{id}")
    public String update(@PathVariable Long id, @Valid Semester semester, Errors errors) {
        if (errors.hasErrors()) {
            return "update-semester";
        } else {
            semesterService.delete(id);
            semesterService.save(semester);
            return "redirect:/semesters";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        semesterService.delete(id);
        return "redirect:/semesters ";
    }

    @GetMapping("/search")
    public String searchSemester(Model model, @RequestParam(required = false) Optional<Boolean> completed) {
        List<Semester> semester = semesterService.findAll();
        model.addAttribute("semester", semester);
        return "redirect:/semesters";
    }

    //------------------------------------------------------------------------------------------------------------------

}
