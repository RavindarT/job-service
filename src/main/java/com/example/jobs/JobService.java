package com.example.jobs;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class JobService {
    private final JobRepository repository;
    public JobService(JobRepository repository) { this.repository = repository; }
    @Transactional(readOnly = true)
    public List<Job> list() { return repository.findAll(); }
    @Transactional(readOnly = true)
    public Job get(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Job not found")); }
    @Transactional
    public Job create(JobRequest request) {

        Job record = new Job();
        record.setDesc(request.desc());
        record.setSalary(request.salary());
        return repository.save(record);
    }
    @Transactional
    public Job update(Long id, JobRequest request) {
        Job record = get(id);
        record.setDesc(request.desc());
        record.setSalary(request.salary());
        return repository.save(record);
    }
    @Transactional
    public void delete(Long id) { repository.delete(get(id)); }
}
