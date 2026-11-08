package com.example.jobs;
import jakarta.persistence.*;
import java.math.BigDecimal;
@Entity
@Table(name = "job")
public class Job {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private Long jobId;
    @Column(name = "description", nullable = false, length = 255)
    private String desc;
    @Column(name = "salary", nullable = false)
    private BigDecimal salary;
    public Long getJobId() { return jobId; }
    public void setJobId(Long value) { jobId = value; }
    public String getDesc() { return desc; }
    public void setDesc(String value) { desc = value; }
    public BigDecimal getSalary() { return salary; }
    public void setSalary(BigDecimal value) { salary = value; }
}
