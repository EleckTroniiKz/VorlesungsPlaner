package de.digitra.uniplaner.interfaces;

import de.digitra.uniplaner.domain.Lecture;
import de.digitra.uniplaner.exceptions.BadRequestException;
import de.digitra.uniplaner.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Interface beschreibt einen REST Controller für die Handhabung von Ressourcen des Typs Lecture.
 */
public interface ILectureController {
    /**
     * {@code POST  /lectures} : Methode erstellt eine Ressource des Typs Lecture.
     *
     * @param lecture Instanz von Lecture, die am Server erstellt werden soll.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} und im Body die erstellte Ressource.
     * Sonst wird Status Code {@code 400 (Bad Request)} zurückgeliefert, falls der übergebene Parameter lecture nicht zulässig ist.
     * Parameter lecture ist nicht zulässig, falls er bereits eine Id hat, die nicht null ist.
     * Status  {@code 500 (Internal Server Error)} wird zurückgeliefert, falls die Ressource nicht erstellt werden konnte.
     * @throws BadRequestException wird ausgelöst, falls die im Parameter lecture übergebene Instanz nicht zulässig ist.
     */
    @PostMapping
    ResponseEntity<Lecture> createLecture(@RequestBody Lecture lecture) throws BadRequestException, URISyntaxException;

    /**
     * {@code PUT  /lectures} : aktualisiert eine existierende Ressource vom Typ Lecture.
     *
     * @param lecture Instanz von Lecture, die am Server aktualisiert werden soll.
     *                Diese Instanz enthält die aktuellen Werte.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die aktualisierte Ressource.
     * Sonst wird Status Code {@code 400 (Bad Request)} falls der übergebene Parameter lecture nicht zulässig war.
     * Der Parameter lecture ist nicht zulässig, falls er eine Id mit dem Wert null hat.
     * Der Status Code {@code 500 (Internal Server Error)} wird zurückgeliefert, falls die Ressource nicht aktualisiert werden konnte.
     * @throws BadRequestException wird ausgelöst, falls die im Parameter lecture übergebene Instanz nicht zulässig ist.
     */
    @PutMapping
    ResponseEntity<Lecture> updateLecture(@RequestBody Lecture lecture) throws BadRequestException;

    /**
     * {@code PUT  /lectures/:id} : aktualisiert eine existierende Ressource vom Typ Lecture.
     *
     * @param id      Id der Ressource vom Typ Lecture, die am Server aktualisiert werden soll.
     * @param lectureDetails Instanz von Lecture, die am Server aktualisiert werden soll.
     *                Diese Instanz enthält die aktuellen Werte.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die aktualisierte Ressource.
     * @throws ResourceNotFoundException wird ausgelöst, falls die Ressource mit der angegebenen Id nicht gefunden werden konnte.
     */
    @PutMapping("/{id}")
    ResponseEntity<Lecture> updateLecture(@PathVariable(value = "id") Long id, @Valid @RequestBody Lecture lectureDetails) throws ResourceNotFoundException;

    /**
     * {@code GET  /lectures} : Liefert eine Liste aller am Server gespeicherten Ressourcen vom Typ Lecture.
     *
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body eine Liste von Ressourcen vom Typ Lecture.
     */
    @GetMapping
    ResponseEntity<List<Lecture>> getAlllectures();

    /**
     * {@code GET  /lectures/:id} : Liefert die Ressource vom Typ Lecture mit der angegebenen Id zurück.
     *
     * @param id Die Id der Ressource vom Typ Lecture, die abgerufen werden soll.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 200 (OK)} and im Body die gesuchte Ressource vom Typ Lecture,
     * oder Status Code  {@code 404 (Not Found)}, falls die Ressource nicht gefunden wurde.
     * @throws ResourceNotFoundException wird ausgelöst, falls die Ressource mit der angegebenen Id nicht gefunden werden konnte.
     */
    @GetMapping("/{id}")
    ResponseEntity<Lecture> getLecture(@PathVariable Long id) throws ResourceNotFoundException;

    /**
     * {@code DELETE  /lectures/:id} : Mit dieser Methode eine Ressource mit der angegebenen Id gelöscht.
     *
     * @param id Die Id der Ressource vom Typ Lecture, die gelöscht werden soll.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     * @return Eine {@link ResponseEntity} mit Status Code {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteLecture(@PathVariable Long id);
}
