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
public class StudyProgramController implements IStudyProgramController {
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
    @RequestMapping(value = "/createStudyProgram", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<StudyProgram> createStudyProgram(@RequestBody StudyProgram studyprogram) throws BadRequestException {
        if(studyprogram.getId() == null){
            return ResponseEntity.ok(studyProgramService.save(studyprogram));
        }
        else{
            throw new BadRequestException("StudyProgram nicht zulässig!");
        }
    }

    @RequestMapping(value = "/updateStudyProgram", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<StudyProgram> updateStudyProgram(@RequestBody StudyProgram studyprogram) throws BadRequestException {
       StudyProgram temp = null;
       if(studyprogram.getId() != null){
            temp = studyprogram;
            studyProgramService.delete(studyprogram.getId());
            return ResponseEntity.ok(studyProgramService.save(temp));
       }
       else{
            throw new BadRequestException("");
       }
    }

    @RequestMapping(value = "/updateStudyProgramByID/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<StudyProgram> updateStudyProgram(@PathVariable Long id, @Valid @RequestBody StudyProgram studyprogramDetails) throws ResourceNotFoundException {
        StudyProgram temp = null;
        if(studyProgramService.findOne(id).isPresent()){
            temp = studyprogramDetails;
            studyProgramService.delete(id);
            return ResponseEntity.ok(studyProgramService.save(temp));
        }
        else{
            throw new ResourceNotFoundException("StudyProgram not found!");
        }
    }

    @RequestMapping(value = "/getAllStudyPrograms", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<StudyProgram>> getAllstudyprograms() {
        return ResponseEntity.ok(studyProgramService.findAll());
    }

    @RequestMapping(value = "/getStudyProgram/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<StudyProgram> getStudyProgram(@PathVariable Long id) throws ResourceNotFoundException {
        if(studyProgramService.findOne(id).isPresent()){
            return ResponseEntity.ok(studyProgramService.findOne(id).get());
        }
        else{
            throw new ResourceNotFoundException("");
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Void> deleteStudyProgram(@PathVariable Long id) {
        studyProgramService.delete(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
