package neon.units.server.rest;

import neon.units.in_memory.Store;
import neon.units.in_memory.use_case.CreateTextNote_UseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/plugin/tag/note")
public class TextNotesRestController {
    private final Store store;
    private final CreateTextNote_UseCase useCase;

    @Autowired
    public TextNotesRestController(Store store, CreateTextNote_UseCase useCase) {
        this.store = store;
        this.useCase = useCase;
    }

    @GetMapping("hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello, World!");
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(store.getAll());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody String text) {
        useCase.create(text);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
