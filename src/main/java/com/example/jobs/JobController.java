package com.example.jobs;
import java.net.URI;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/jobs")
public class JobController {
    private final JobService service;
    public JobController(JobService service) { this.service = service; }
    @GetMapping public List<Job> list() { return service.list(); }
    @GetMapping("/{id}") public Job get(@PathVariable Long id) { return service.get(id); }
    @PostMapping public ResponseEntity<Job> create(@Valid @RequestBody JobRequest request) {
        Job created = service.create(request);
        return ResponseEntity.created(URI.create("/api/jobs/" + created.getJobId())).body(created);
    }
    @PutMapping("/{id}") public Job update(@PathVariable Long id, @Valid @RequestBody JobRequest request) { return service.update(id, request); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
