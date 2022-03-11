package de.digitra.uniplaner.interfaces;

import de.digitra.uniplaner.domain.StudyClass;
import de.digitra.uniplaner.exceptions.BadRequestException;
import de.digitra.uniplaner.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Interface beschreibt einen REST Controller für die Handhabung von Ressourcen des Typs StudyClass.
 */
public interface IStudyClassController {
    /**
     * {@code POST  /studyclasss} : Methode erstellt eine Ressource vom Typ StudyClass.
     *
     * @param studyclass Instanz von StudyClass, die am Server erstellt werden soll.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} und im Body die erstellte Ressource.
     * Sonst wird Status Code {@code 400 (Bad Request)} zurückgeliefert, falls der übergebene Parameter studyclass nicht zulässig ist.
     * Parameter studyclass ist nicht zulässig, falls er bereits eine Id hat, die nicht null ist.
     * Status  {@code 500 (Internal Server Error)} wird zurückgeliefert, falls die Ressource nicht erstellt werden konnte.
     * @throws BadRequestException wird ausgelöst, falls die im Parameter studyclass übergebene Instanz nicht zulässig ist.
     */
    @PostMapping
    ResponseEntity<StudyClass> createStudyClass(@RequestBody StudyClass studyclass) throws BadRequestException, URISyntaxException;

    /**
     * {@code PUT  /studyclasss} : aktualisiert eine existierende Ressource vom Typ StudyClass.
     *
     * @param studyclass Instanz von StudyClass, die am Server aktualisiert werden soll.
     *                   Diese Instanz enthält die aktuellen Werte.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die aktualisierte Ressource.
     * Sonst wird Status Code {@code 400 (Bad Request)} falls der übergebene Parameter studyclass nicht zulässig war.
     * Der Parameter studyclass ist nicht zulässig, falls er eine Id mit dem Wert null hat.
     * Der Status Code {@code 500 (Internal Server Error)} wird zurückgeliefert, falls die Ressource nicht aktualisiert werden konnte.
     * @throws BadRequestException wird ausgelöst, falls studyclass nicht zulässig ist.
     */
    @PutMapping
    ResponseEntity<StudyClass> updateStudyClass(@RequestBody StudyClass studyclass) throws BadRequestException;

    /**
     * {@code PUT  /studyclasss/:id} : aktualisiert eine existierende Ressource vom Typ StudyClass.
     *
     * @param id         Id der Ressource vom Typ StudyClass, die am Server aktualisiert werden soll.
     * @param studyclassDetails Instanz von StudyClass, die am Server aktualisiert werden soll.
     *                   Diese Instanz enthält die aktuellen Werte.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die aktualisierte Ressource.
     * @throws ResourceNotFoundException wird ausgelöst, falls die Ressource mit der angegebenen Id nicht gefunden werden konnte.
     */
    @PutMapping("/{id}")
    ResponseEntity<StudyClass> updateStudyClass(@PathVariable(value = "id") Long id, @Valid @RequestBody StudyClass studyclassDetails) throws ResourceNotFoundException;

    /**
     * {@code GET  /studyclasss} : Liefert eine Liste aller am Server gespeicherten Ressourcen vom Typ StudyClass.
     *
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body eine Liste von Ressourcen vom Typ StudyClass.
     */
    @GetMapping
    ResponseEntity<List<StudyClass>> getAllstudyclasss();

    /**
     * {@code GET  /studyclasss/:id} : Liefert die Ressource vom Typ StudyClass mit der angegebenen Id zurück.
     *
     * @param id Die Id der Ressource vom Typ StudyClass, die abgerufen werden soll.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die gesuchte Ressource vom Typ StudyClass,
     * oder Status Code  {@code 404 (Not Found)}, falls die Ressource nicht gefunden wurde.
     * @throws ResourceNotFoundException wird ausgelöst, falls die Ressource mit der angegebenen Id nicht gefunden werden konnte.
     */
    @GetMapping("/{id}")
    ResponseEntity<StudyClass> getStudyClass(@PathVariable Long id) throws ResourceNotFoundException;

    /**
     * {@code DELETE  /studyclasss/:id} : Mit dieser Methode eine Ressource mit der angegebenen Id gelöscht.
     *
     * @param id Die Id der Ressource vom Typ StudyClass, die gelöscht werden soll.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteStudyClass(@PathVariable Long id);
}
