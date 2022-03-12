package de.digitra.uniplaner.interfaces;

import de.digitra.uniplaner.domain.Lecturer;
import de.digitra.uniplaner.exceptions.BadRequestException;
import de.digitra.uniplaner.exceptions.DuplicateEmailException;
import de.digitra.uniplaner.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Interface beschreibt einen REST Controller für die Handhabung von Ressourcen des Typs Lecturer.
 */
public interface ILecturerController {
    /**
     * {@code POST  /lecturers} : Methode erstellt eine Ressource vom Typ Lecturer.
     *
     * @param lecturer Instanz von Lecturer, die am Server erstellt werden soll.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} und im Body die erstellte Ressource.
     * Sonst wird Status Code {@code 400 (Bad Request)} zurückgeliefert, falls der übergebene Parameter lecturer bereits eine Id hat.
     * Der Status Code {@code 400 (Bad Request)} wird ebenfalls zurückgeliefert, falls der übergebene Parameter lecturer eine E-Mail-Adresse angibt, die bereits in Verwendung ist.
     * Status  {@code 500 (Internal Server Error)} wird zurückgeliefert, falls die Ressource nicht erstellt werden konnte.
     * @throws BadRequestException wird ausgelöst, falls die im Parameter lecturer übergebene Instanz bereits eine Id hat.
     * @throws DuplicateEmailException wird ausgelöst, falls die im Parameter lecturer übergebene Instanz eine E-Mail hat,
     * die bereits von einem anderen Lecturer im System verwendet wird.
     */
    @PostMapping
    ResponseEntity<Lecturer> createLecturer(@RequestBody Lecturer lecturer) throws BadRequestException, DuplicateEmailException, URISyntaxException;

    /**
     * {@code PUT  /lecturers} : aktualisiert eine existierende Ressource vom Typ Lecturer.
     *
     * @param lecturer Instanz von Lecturer, die am Server aktualisiert werden soll.
     *                 Diese Instanz enthält die aktuellen Werte.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die aktualisierte Ressource.
     * Sonst wird Status Code {@code 400 (Bad Request)} falls der übergebene Parameter lecturer nicht zulässig war.
     * Der Parameter lecturer ist nicht zulässig, falls er eine Id mit dem Wert null hat.
     * Der Status Code {@code 500 (Internal Server Error)} wird zurückgeliefert, falls die Ressource nicht aktualisiert werden konnte.
     * @throws BadRequestException wird ausgelöst, falls lecturer nicht zulässig ist.
     */
    @PutMapping
    ResponseEntity<Lecturer> updateLecturer(@RequestBody Lecturer lecturer) throws BadRequestException;

    /**
     * {@code PUT  /lecturers/:id} : aktualisiert eine existierende Ressource vom Typ Lecturer.
     *
     * @param id       Id der Ressource vom Typ Lecturer, die am Server aktualisiert werden soll.
     * @param lecturerDetails Instanz von Lecturer, die am Server aktualisiert werden soll.
     *                 Diese Instanz enthält die aktuellen Werte.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die aktualisierte Ressource.
     * @throws ResourceNotFoundException wird ausgelöst, falls die Ressource mit der angegebenen Id nicht gefunden werden konnte.
     */
    @PutMapping("/{id}")
    ResponseEntity<Lecturer> updateLecturer(@PathVariable(value = "id") Long id, @Valid @RequestBody Lecturer lecturerDetails) throws ResourceNotFoundException;

    /**
     * {@code GET  /lecturers} : Liefert eine Liste aller am Server gespeicherten Ressourcen vom Typ Lecturer.
     *
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body eine Liste von Ressourcen vom Typ Lecturer.
     */
    @GetMapping
    ResponseEntity<List<Lecturer>> getAlllecturers();

    /**
     * {@code GET  /lecturers/:id} : Liefert die Ressource vom Typ Lecturer mit der angegebenen Id zurück.
     *
     * @param id Die Id der Ressource vom Typ Lecturer, die abgerufen werden soll.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die gesuchte Ressource vom Typ Lecturer,
     * oder Status Code  {@code 404 (Not Found)}, falls die Ressource nicht gefunden wurde.
     * @throws ResourceNotFoundException wird ausgelöst, falls die Ressource mit der angegebenen Id nicht gefunden werden konnte.
     */
    @GetMapping("/{id}")
    ResponseEntity<Lecturer> getLecturer(@PathVariable Long id) throws ResourceNotFoundException;

    /**
     * {@code DELETE  /lecturers/:id} : Mit dieser Methode eine Ressource mit der angegebenen Id gelöscht.
     *
     * @param id Die Id der Ressource vom Typ Lecturer, die gelöscht werden soll.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteLecturer(@PathVariable Long id);
}
