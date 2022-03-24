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
public class SemesterController implements ISemesterController {

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
    @RequestMapping(value = "/createSemester", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Semester> createSemester(@RequestBody Semester semester) throws BadRequestException {
        if (semester.getId() == null) {
            return ResponseEntity.ok(semesterService.save(semester));
        }
        else{
            throw new BadRequestException("Semester nicht zulässig!");
        }
    }

    @RequestMapping(value = "/updateSemester", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Semester> updateSemester(@RequestBody Semester semester) throws BadRequestException {
        Semester temp = null;
        if(semester.getId() != null){
            temp = semester;
            semesterService.delete(semester.getId());
            return ResponseEntity.ok(semesterService.save(temp));
        }
        else{
            throw new BadRequestException("Semester ist nicht zulässig");
        }
    }

    @RequestMapping(value = "/updateSemester/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Semester> updateSemester(@PathVariable(value = "id") Long id, @Valid @RequestBody Semester semesterDetails) throws ResourceNotFoundException {
        Semester temp = null;
        if(semesterService.findOne(id).isPresent()){
            temp = semesterDetails;
            semesterService.delete(id);
            return ResponseEntity.ok(semesterService.save(temp));
        }
        else{
            throw new ResourceNotFoundException("Semester konnte nicht gefunden werden!");
        }
    }

    @RequestMapping(value = "/getAllSemesters", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Semester>> getAllsemesters() {
        return ResponseEntity.ok(semesterService.findAll());
    }

    @RequestMapping(value = "/getSemester/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Semester> getSemester(@PathVariable Long id) throws ResourceNotFoundException {
        Semester sem = null;
        if (semesterService.findOne(id).isPresent()) {
            return ResponseEntity.ok(semesterService.findOne(id).get());
        } else {
            throw new ResourceNotFoundException("Semester not found");
        }
    }

    @RequestMapping(value = "/deleteSemester/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Void> deleteSemester(@PathVariable Long id) {
        semesterService.delete(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
