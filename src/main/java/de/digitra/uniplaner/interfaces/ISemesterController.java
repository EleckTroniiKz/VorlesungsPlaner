package de.digitra.uniplaner.interfaces;

import de.digitra.uniplaner.domain.Semester;
import de.digitra.uniplaner.exceptions.BadRequestException;
import de.digitra.uniplaner.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Interface beschreibt einen REST Controller für die Handhabung von Ressourcen des Typs Semester.
 */
public interface ISemesterController {
    /**
     * {@code POST  /semesters} : Methode erstellt eine Ressource vom Typ Semester.
     *
     * @param semester Instanz von Semester, die am Server erstellt werden soll.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} und im Body die erstellte Ressource.
     * Sonst wird Status Code {@code 400 (Bad Request)} zurückgeliefert, falls der übergebene Parameter semester nicht zulässig ist.
     * Parameter semester ist nicht zulässig, falls er bereits eine Id hat, die nicht null ist.
     * Status  {@code 500 (Internal Server Error)} wird zurückgeliefert, falls die Ressource nicht erstellt werden konnte.
     * @throws BadRequestException wird ausgelöst, falls die im Parameter semester übergebene Instanz nicht zulässig ist.
     */
    @PostMapping
    ResponseEntity<Semester> createSemester(@RequestBody Semester semester) throws BadRequestException, URISyntaxException;

    /**
     * {@code PUT  /semesters} : aktualisiert eine existierende Ressource vom Typ Semester.
     *
     * @param semester Instanz von Semester, die am Server aktualisiert werden soll.
     *                 Diese Instanz enthält die aktuellen Werte.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die aktualisierte Ressource.
     * Sonst wird Status Code {@code 400 (Bad Request)} falls der übergebene Parameter semester nicht zulässig war.
     * Der Parameter semester ist nicht zulässig, falls er eine Id mit dem Wert null hat.
     * Der Status Code {@code 500 (Internal Server Error)} wird zurückgeliefert, falls die Ressource nicht aktualisiert werden konnte.
     * @throws BadRequestException wird ausgelöst, falls semester nicht zulässig ist.
     */
    @PutMapping
    ResponseEntity<Semester> updateSemester(@RequestBody Semester semester) throws BadRequestException;

    /**
     * {@code PUT  /semesters/:id} : aktualisiert eine existierende Ressource vom Typ Semester.
     *
     * @param id       Id der Ressource vom Typ Semester, die am Server aktualisiert werden soll.
     * @param semesterDetails Instanz von Semester, die am Server aktualisiert werden soll.
     *                 Diese Instanz enthält die aktuellen Werte.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die aktualisierte Ressource.
     * @throws ResourceNotFoundException wird ausgelöst, falls die Ressource mit der angegebenen Id nicht gefunden werden konnte.
     */
    @PutMapping("/{id}")
    ResponseEntity<Semester> updateSemester(@PathVariable(value = "id") Long id, @Valid @RequestBody Semester semesterDetails) throws ResourceNotFoundException;

    /**
     * {@code GET  /semesters} : Liefert eine Liste aller am Server gespeicherten Ressourcen vom Typ Semester.
     *
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body eine Liste von Ressourcen vom Typ Semester.
     */
    @GetMapping
    ResponseEntity<List<Semester>> getAllsemesters();

    /**
     * {@code GET  /semesters/:id} : Liefert die Ressource vom Typ Semester mit der angegebenen Id zurück.
     *
     * @param id Die Id der Ressource vom Typ Semester, die abgerufen werden soll.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die gesuchte Ressource vom Typ Semester,
     * oder Status Code  {@code 404 (Not Found)}, falls die Ressource nicht gefunden wurde.
     * @throws ResourceNotFoundException wird ausgelöst, falls die Ressource mit der angegebenen Id nicht gefunden werden konnte.
     */
    @GetMapping("/{id}")
    ResponseEntity<Semester> getSemester(@PathVariable Long id) throws ResourceNotFoundException;

    /**
     * {@code DELETE  /semesters/:id} : Mit dieser Methode eine Ressource mit der angegebenen Id gelöscht.
     *
     * @param id Die Id der Ressource vom Typ Semester, die gelöscht werden soll.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteSemester(@PathVariable Long id);
}
