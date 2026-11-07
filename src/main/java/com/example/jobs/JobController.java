package com.example.jobs;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    private final JdbcClient db;

    public JobController(JdbcClient db) {
        this.db = db;
    }

    public record Job(UUID id, String title, String description) {}

    public record JobInput(@NotBlank @Size(max = 120) String title,
                           @Size(max = 2000) String description) {}

    @PostMapping
    public ResponseEntity<Job> create(@Valid @RequestBody JobInput input) {
        Job job = new Job(UUID.randomUUID(), input.title().strip(), input.description());
        db.sql("INSERT INTO jobs (id, title, description) VALUES (:id, :title, :description)")
                .paramSource(job).update();
        return ResponseEntity.created(URI.create("/api/jobs/" + job.id())).body(job);
    }

    @GetMapping
    public List<Job> list() {
        return db.sql("SELECT id, title, description FROM jobs ORDER BY title, id")
                .query(Job.class).list();
    }

    @GetMapping("/{id}")
    public Job get(@PathVariable UUID id) {
        return db.sql("SELECT id, title, description FROM jobs WHERE id = ?")
                .param(id).query(Job.class).optional().orElseThrow(JobController::notFound);
    }

    @PutMapping("/{id}")
    public Job update(@PathVariable UUID id, @Valid @RequestBody JobInput input) {
        Job job = new Job(id, input.title().strip(), input.description());
        requireJob(db.sql("UPDATE jobs SET title = :title, description = :description WHERE id = :id")
                .paramSource(job).update());
        return job;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        requireJob(db.sql("DELETE FROM jobs WHERE id = ?").param(id).update());
    }

    private static void requireJob(int rows) {
        if (rows == 0) {
            throw notFound();
        }
    }

    private static ResponseStatusException notFound() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found");
    }
}
