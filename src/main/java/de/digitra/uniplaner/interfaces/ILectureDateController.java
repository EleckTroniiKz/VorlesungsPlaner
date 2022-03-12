package de.digitra.uniplaner.interfaces;

import de.digitra.uniplaner.domain.LectureDate;
import de.digitra.uniplaner.exceptions.BadRequestException;
import de.digitra.uniplaner.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Interface beschreibt einen REST Controller für die Handhabung von Ressourcen des Typs LectureDate.
 */
public interface ILectureDateController {
    /**
     * {@code POST  /lecturedates} : Methode erstellt eine Ressource vom Typ LectureDate.
     *
     * @param lecturedate Instanz von LectureDate, die am Server erstellt werden soll.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} und im Body die erstellte Ressource.
     * Sonst wird Status Code {@code 400 (Bad Request)} zurückgeliefert, falls der übergebene Parameter lecturedate nicht zulässig ist.
     * Parameter lecturedate ist nicht zulässig, falls er bereits eine Id hat, die nicht null ist.
     * Status  {@code 500 (Internal Server Error)} wird zurückgeliefert, falls die Ressource nicht erstellt werden konnte.
     * @throws BadRequestException wird ausgelöst, falls die im Parameter lecturedate übergebene Instanz nicht zulässig ist.
     */
    @PostMapping
    ResponseEntity<LectureDate> createLectureDate(@RequestBody LectureDate lecturedate) throws BadRequestException, URISyntaxException;

    /**
     * {@code PUT  /lecturedates} : aktualisiert eine existierende Ressource vom Typ LectureDate.
     *
     * @param lecturedate Instanz von LectureDate, die am Server aktualisiert werden soll.
     *                    Diese Instanz enthält die aktuellen Werte.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die aktualisierte Ressource.
     * Sonst wird Status Code {@code 400 (Bad Request)} falls der übergebene Parameter lecturedate nicht zulässig war.
     * Der Parameter lecturedate ist nicht zulässig, falls er eine Id mit dem Wert null hat.
     * Der Status Code {@code 500 (Internal Server Error)} wird zurückgeliefert, falls die Ressource nicht aktualisiert werden konnte.
     * @throws BadRequestException wird ausgelöst, falls die im Parameter lecturedate übergebene Instanz nicht zulässig ist.
     */
    @PutMapping
    ResponseEntity<LectureDate> updateLectureDate(@RequestBody LectureDate lecturedate) throws BadRequestException;

    /**
     * {@code PUT  /lecturedates/:id} : aktualisiert eine existierende Ressource vom Typ LectureDate.
     *
     * @param id          Id der Ressource vom Typ LectureDate, die am Server aktualisiert werden soll.
     * @param lecturedateDetails Instanz von LectureDate, die am Server aktualisiert werden soll.
     *                    Diese Instanz enthält die aktuellen Werte.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die aktualisierte Ressource.
     * @throws ResourceNotFoundException wird ausgelöst, falls die Ressource mit der angegebenen Id nicht gefunden werden konnte.
     */
    @PutMapping("/{id}")
    ResponseEntity<LectureDate> updateLectureDate(@PathVariable(value = "id") Long id, @Valid @RequestBody LectureDate lecturedateDetails) throws ResourceNotFoundException;

    /**
     * {@code GET  /lecturedates} : Liefert eine Liste aller am Server gespeicherten Ressourcen vom Typ LectureDate.
     *
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body eine Liste von Ressourcen vom Typ LectureDate.
     */
    @GetMapping
    ResponseEntity<List<LectureDate>> getAlllecturedates();

    /**
     * {@code GET  /lecturedates/:id} : Liefert die Ressource vom Typ LectureDate mit der angegebenen Id zurück.
     *
     * @param id Die Id der Ressource vom Typ LectureDate, die abgerufen werden soll.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die gesuchte Ressource vom Typ LectureDate,
     * oder Status Code  {@code 404 (Not Found)}, falls die Ressource nicht gefunden wurde.
     * @throws ResourceNotFoundException wird ausgelöst, falls die Ressource mit der angegebenen Id nicht gefunden werden konnte.
     */
    @GetMapping("/{id}")
    ResponseEntity<LectureDate> getLectureDate(@PathVariable Long id) throws ResourceNotFoundException;

    /**
     * {@code DELETE  /lecturedates/:id} : Mit dieser Methode eine Ressource mit der angegebenen Id gelöscht.
     *
     * @param id Die Id der Ressource vom Typ LectureDate, die gelöscht werden soll.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteLectureDate(@PathVariable Long id) throws ResourceNotFoundException;
}
