package de.digitra.uniplaner.controller;

import de.digitra.uniplaner.domain.StudyProgram;
import de.digitra.uniplaner.exceptions.BadRequestException;
import de.digitra.uniplaner.exceptions.ResourceNotFoundException;
import de.digitra.uniplaner.interfaces.IStudyProgramController;
import de.digitra.uniplaner.service.StudyProgramService;
import org.apache.coyote.Response;
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
@RequestMapping("/studyprograms")
public class StudyProgramController{
    @Autowired
    private StudyProgramService studyProgramService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("studyPrograms", studyProgramService.findAll());
        return "studyprogram-list";
    }

    @GetMapping("/create")
    public String createStudyProgram(Model model) {
        model.addAttribute("studyPrograms", new StudyProgram());
        return "create-studyprogram";
    }

    @PostMapping
    public String createStudyProgram(@Valid StudyProgram studyProgram, Errors errors) {
        if (errors.hasErrors()) {
            return "redirect:/studyprograms/crteate";
        } else {
            studyProgramService.save(studyProgram);
            return "redirect:/studyprograms";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) throws ResourceNotFoundException {
        Optional<StudyProgram> studyProgramToEdit = studyProgramService.findOne(id);

        if (!studyProgramToEdit.isPresent()) {
            throw new ResourceNotFoundException("Program wurde nicht gefunden!");
        } else {
            model.addAttribute("studyprogram", studyProgramToEdit.get());
            return "update-studyprogram";
        }
    }

    @PutMapping("/edit/{id}")
    public String update(@PathVariable Long id, @Valid StudyProgram studyProgram, Errors errors) {
        if (errors.hasErrors()) {
            return "redirect:/studyprograms/edit/" + id;
        } else {
            studyProgramService.delete(id);
            studyProgramService.save(studyProgram);
            return "redirect:/studyprograms";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        studyProgramService.delete(id);
        return "redirect:/studyprograms";
    }

    @GetMapping("/search")
    public String searchStudyProgram(Model model, @RequestParam(required = false) Optional<Boolean> completed) {
        List<StudyProgram> studyProgram = studyProgramService.findAll();
        model.addAttribute("studyprogram", studyProgram);
        return "redirect:/studyprogram";
    }

    //_________________________________________________________________________________________________________________

}
