package de.digitra.uniplaner.controller;

import de.digitra.uniplaner.domain.StudyClass;
import de.digitra.uniplaner.exceptions.BadRequestException;
import de.digitra.uniplaner.exceptions.ResourceNotFoundException;
import de.digitra.uniplaner.interfaces.IStudyClassController;
import de.digitra.uniplaner.service.StudyClassService;
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
@RequestMapping("/studyclass")
public class StudyClassController{

    private StudyClassService studyClassService;
    private StudyProgramService studyProgramService;

    StudyClassController(StudyClassService _studyClassService, StudyProgramService _studyProgrammService) {
        studyClassService = _studyClassService;
        studyProgramService = _studyProgrammService;
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("studyclass", studyClassService.findAll());
        return "studyclass-list";
    }

    @GetMapping("/create")
    public String createStudyClass(Model model) {
        model.addAttribute("studyclass", new StudyClass());
        model.addAttribute("studyProgram", studyProgramService.findAll());
        return "create-studyclass";
    }

    @PostMapping
    public String createStudyClass(@Valid StudyClass studyClass, Errors errors) {
        if (errors.hasErrors()) {
            return "redirect:/studyclass/create";
        } else {
            studyClassService.save(studyClass);
            return "redirect:/studyclass";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) throws ResourceNotFoundException {
        Optional<StudyClass> studyClassToEdit = studyClassService.findOne(id);
        if (!studyClassToEdit.isPresent()) {
            throw new ResourceNotFoundException("Klasse wurde nicht gefunden!");
        } else {
            model.addAttribute("studyclass", studyClassToEdit.get());
            model.addAttribute("studyPrograms", studyProgramService.findAll());
            return "update-studyclass";
        }
    }

    @PutMapping("/edit/{id}")
    public String update(@PathVariable Long id, @Valid StudyClass studyClass, Errors errors) {
        if (errors.hasErrors()) {
            return "redirect:/studyclass/edit/" + id;
        } else {
            studyClassService.delete(id);
            studyClassService.save(studyClass);
            return "redirect:/studyclass";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        studyClassService.delete(id);
        return "redirect:/studyclass";
    }

    @GetMapping("/search")
    public String searchSemester(Model model, @RequestParam(required = false) Optional<Boolean> completed) {
        List<StudyClass> studyClass = studyClassService.findAll();
        model.addAttribute("studyClass", studyClass);
        return "redirect:/studyclass";
    }
    //------------------------------------------------------------------------------------------------------------------

}
