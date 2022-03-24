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
@RequestMapping("/studyclasss")
public class StudyClassController implements IStudyClassController {

    private StudyClassService studyClassService;
    private StudyProgramService studyProgramService;

    StudyClassController(StudyClassService _studyClassService, StudyProgramService _studyProgrammService) {
        studyClassService = _studyClassService;
        studyProgramService = _studyProgrammService;
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("studyclasss", studyClassService.findAll());
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
            return "redirect:/studyclasss/create";
        } else {
            studyClassService.save(studyClass);
            return "redirect:/studyclasss";
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
            return "redirect:/studyclasss/edit/" + id;
        } else {
            studyClassService.delete(id);
            studyClassService.save(studyClass);
            return "redirect:/studyclasss";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        studyClassService.delete(id);
        return "redirect:/studyclasss";
    }

    @GetMapping("/search")
    public String searchSemester(Model model, @RequestParam(required = false) Optional<Boolean> completed) {
        List<StudyClass> studyClass = studyClassService.findAll();
        model.addAttribute("studyClass", studyClass);
        return "redirect:/studyclass";
    }
    //------------------------------------------------------------------------------------------------------------------

    @RequestMapping(value = "/createStudyClass", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<StudyClass> createStudyClass(@RequestBody StudyClass studyclass) throws BadRequestException {
        if (studyclass.getId() == null) {
            return ResponseEntity.ok(studyClassService.save(studyclass));
        } else {
            throw new BadRequestException("StudyClass invalid!");
        }
    }

    @RequestMapping(value = "/updateStudyClass", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<StudyClass> updateStudyClass(@RequestBody StudyClass studyclass) throws BadRequestException {
        StudyClass temp = null;
        if (studyclass.getId() == null) {
            temp = studyclass;
            studyClassService.delete(studyclass.getId());
            return ResponseEntity.ok(studyClassService.save(temp));
        } else {
            throw new BadRequestException("StudyClass invalid!");
        }
    }

    @RequestMapping(value = "/updateStudyClass/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<StudyClass> updateStudyClass(@PathVariable Long id, @Valid @RequestBody StudyClass studyclassDetails) throws ResourceNotFoundException {
        StudyClass temp = null;
        if (studyClassService.findOne(id).isPresent()) {
            temp = studyclassDetails;
            studyClassService.delete(id);
            return ResponseEntity.ok(temp);
        } else {
            throw new ResourceNotFoundException("StudyClass invalid!");
        }
    }

    @RequestMapping(value = "/getAllstudyclasss", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<StudyClass>> getAllstudyclasss() {
        return ResponseEntity.ok(studyClassService.findAll());
    }

    @RequestMapping(value = "/getStudyClass/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<StudyClass> getStudyClass(@PathVariable Long id) throws ResourceNotFoundException {
        StudyClass ls = null;
        if(studyClassService.findOne(id).isPresent()){
            return ResponseEntity.ok(studyClassService.findOne(id).get());
        }
        else{
            throw new ResourceNotFoundException("StudyClass not found!");
        }
    }

    @RequestMapping(value = "/deleteStudyClass/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Void> deleteStudyClass(@PathVariable Long id) {
        studyClassService.delete(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
