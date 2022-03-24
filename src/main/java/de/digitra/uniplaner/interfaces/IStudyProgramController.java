package de.digitra.uniplaner.interfaces;

import de.digitra.uniplaner.domain.StudyProgram;
import de.digitra.uniplaner.exceptions.BadRequestException;
import de.digitra.uniplaner.exceptions.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface IStudyProgramController {
    /**
     * {@code POST  /studyprograms} : Methode erstellt eine Ressource vom Typ StudyProgram.
     *
     * @param studyprogram Instanz von StudyProgram, die am Server erstellt werden soll.
     * Parameter studyprogram ist nicht zulässig, falls er bereits eine Id hat, die nicht null ist.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} und im Body die erstellte Ressource.
     * @throws BadRequestException falls studyprogram nicht zulässig ist.
     */
    @PostMapping
    ResponseEntity<StudyProgram> createStudyProgram(@RequestBody StudyProgram studyprogram) throws BadRequestException;

    /**
     * {@code PUT  /studyprograms} : aktualisiert eine existierende Ressource vom Typ StudyProgram.
     *
     * @param studyprogram Instanz von StudyProgram, die am Server aktualisiert werden soll.
     *                     Diese Instanz enthält die aktuellen Werte.
     * Der Parameter studyprogram ist nicht zulässig, falls er eine Id mit dem Wert null hat.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die aktualisierte Ressource.
     * @throws BadRequestException wird ausgelöst, falls studyprogram nicht zulässig ist.
     */
    @PutMapping
    ResponseEntity<StudyProgram> updateStudyProgram(@RequestBody StudyProgram studyprogram) throws BadRequestException;

    /**
     * {@code PUT  /studyprograms/:id} : aktualisiert eine existierende Ressource vom Typ StudyProgram.
     *
     * @param id                  Id der Ressource vom Typ StudyProgram, die am Server aktualisiert werden soll.
     * @param studyprogramDetails Instanz von StudyProgram, die am Server aktualisiert werden soll.
     *                            Diese Instanz enthält die aktuellen Werte.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die aktualisierte Ressource.
     * @throws ResourceNotFoundException wird ausgelöst, falls die Ressource mit der angegebenen Id nicht gefunden werden konnte.
     */
    @PutMapping("/{id}")
    ResponseEntity<StudyProgram> updateStudyProgram(@PathVariable(value = "id") Long id, @Valid @RequestBody StudyProgram studyprogramDetails) throws ResourceNotFoundException;

    /**
     * {@code GET  /studyprograms} : Liefert eine Liste aller am Server gespeicherten Ressourcen vom Typ StudyProgram.
     *
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body eine Liste von Ressourcen vom Typ StudyProgram.
     */
    @GetMapping
    ResponseEntity<List<StudyProgram>> getAllstudyprograms();

    /**
     * {@code GET  /studyprograms/:id} : Liefert die Ressource vom Typ StudyProgram mit der angegebenen Id zurück.
     *
     * @param id Die Id der Ressource vom Typ StudyProgram, die abgerufen werden soll.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} und im Body die gesuchte Ressource vom Typ StudyProgram.
     * @throws ResourceNotFoundException wird ausgelöst, falls die Ressource mit der angegebenen Id nicht gefunden werden konnte.
     */
    @GetMapping("/{id}")
    ResponseEntity<StudyProgram> getStudyProgram(@PathVariable Long id) throws ResourceNotFoundException;

    /**
     * {@code DELETE  /studyprograms/:id} : Mit dieser Methode eine Ressource mit der angegebenen Id gelöscht.
     *
     * @param id Die Id der Ressource vom Typ StudyProgram, die gelöscht werden soll.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteStudyProgram(@PathVariable Long id);
}
