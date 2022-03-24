package de.digitra.uniplaner.interfaces;

import de.digitra.uniplaner.domain.StudyClass;
import de.digitra.uniplaner.exceptions.BadRequestException;
import de.digitra.uniplaner.exceptions.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface IStudyClassController {
    /**
     * {@code POST  /studyclasses} : Methode erstellt eine Ressource vom Typ StudyClass.
     *
     * @param studyclass Instanz von StudyClass, die am Server erstellt werden soll.
     * Parameter studyclass ist nicht zulässig, falls er bereits eine Id hat, die nicht null ist.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} und im Body die erstellte Ressource.
     * @throws BadRequestException falls studyclass nicht zulässig ist.
     */
    @PostMapping
    ResponseEntity<StudyClass> createStudyClass(@RequestBody StudyClass studyclass) throws BadRequestException;

    /**
     * {@code PUT  /studyclasses} : aktualisiert eine existierende Ressource vom Typ StudyClass.
     *
     * @param studyclass Instanz von StudyClass, die am Server aktualisiert werden soll.
     *                   Diese Instanz enthält die aktuellen Werte.
     * Der Parameter studyclass ist nicht zulässig, falls er eine Id mit dem Wert null hat.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die aktualisierte Ressource.
     * @throws BadRequestException wird ausgelöst, falls studyclass nicht zulässig ist.
     */
    @PutMapping
    ResponseEntity<StudyClass> updateStudyClass(@RequestBody StudyClass studyclass) throws BadRequestException;

    /**
     * {@code PUT  /studyclasses/:id} : aktualisiert eine existierende Ressource vom Typ StudyClass.
     *
     * @param id                Id der Ressource vom Typ StudyClass, die am Server aktualisiert werden soll.
     * @param studyclassDetails Instanz von StudyClass, die am Server aktualisiert werden soll.
     *                          Diese Instanz enthält die aktuellen Werte.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die aktualisierte Ressource.
     * @throws ResourceNotFoundException wird ausgelöst, falls die Ressource mit der angegebenen Id nicht gefunden werden konnte.
     */
    @PutMapping("/{id}")
    ResponseEntity<StudyClass> updateStudyClass(@PathVariable(value = "id") Long id, @Valid @RequestBody StudyClass studyclassDetails) throws ResourceNotFoundException;

    /**
     * {@code GET  /studyclasses} : Liefert eine Liste aller am Server gespeicherten Ressourcen vom Typ StudyClass.
     *
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body eine Liste von Ressourcen vom Typ StudyClass.
     */
    @GetMapping
    ResponseEntity<List<StudyClass>> getAllstudyclasss();

    /**
     * {@code GET  /studyclasses/:id} : Liefert die Ressource vom Typ StudyClass mit der angegebenen Id zurück.
     *
     * @param id Die Id der Ressource vom Typ StudyClass, die abgerufen werden soll.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} und im Body die gesuchte Ressource vom Typ StudyClass.
     * @throws ResourceNotFoundException wird ausgelöst, falls die Ressource mit der angegebenen Id nicht gefunden werden konnte.
     */
    @GetMapping("/{id}")
    ResponseEntity<StudyClass> getStudyClass(@PathVariable Long id) throws ResourceNotFoundException;

    /**
     * {@code DELETE  /studyclasses/:id} : Mit dieser Methode eine Ressource mit der angegebenen Id gelöscht.
     *
     * @param id Die Id der Ressource vom Typ StudyClass, die gelöscht werden soll.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteStudyClass(@PathVariable Long id);
}
