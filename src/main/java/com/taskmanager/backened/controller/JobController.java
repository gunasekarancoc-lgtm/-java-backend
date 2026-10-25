package com.taskmanager.backened.controller;

import com.taskmanager.backened.model.JobPost;
import com.taskmanager.backened.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "http://localhost:3000")
public class JobController {

    @Autowired
    private JobRepository jobRepo;

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    @GetMapping
    public ResponseEntity<List<JobPost>> retrieveAllJobs() {
        return ResponseEntity.ok(jobRepo.findAll());
    }

    @PostMapping
    public ResponseEntity<JobPost> publishJob(@RequestBody JobPost incomingJob) {
        return ResponseEntity.ok(jobRepo.save(incomingJob));
    }

    // CHANGED: File uploads must use @PostMapping instead of @PutMapping
    @PostMapping("/{id}/apply-with-file")
    public ResponseEntity<JobPost> applyToJobWithFile(
            @PathVariable Long id,
            @RequestParam("applicantName") String applicantName,
            @RequestParam("applicantEmail") String applicantEmail,
            @RequestParam(value = "resumeFile", required = false) MultipartFile resumeFile) {

        JobPost job = jobRepo.findById(id).orElseThrow(() -> new RuntimeException("Job not found"));
        job.setApplied(true);
        job.setApplicantName(applicantName);
        job.setApplicantEmail(applicantEmail);

        if (resumeFile != null && !resumeFile.isEmpty()) {
            try {
                File directory = new File(UPLOAD_DIR);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                String fileName = System.currentTimeMillis() + "_" + resumeFile.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR + fileName);
                Files.write(filePath, resumeFile.getBytes());
                job.setResumeFileName(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return ResponseEntity.ok(jobRepo.save(job));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobPost> updateJob(@PathVariable Long id, @RequestBody JobPost jobDetails) {
        JobPost job = jobRepo.findById(id).orElseThrow(() -> new RuntimeException("Job not found"));
        if (jobDetails.getTitle() != null) job.setTitle(jobDetails.getTitle());
        if (jobDetails.getCompany() != null) job.setCompany(jobDetails.getCompany());
        if (jobDetails.getLocation() != null) job.setLocation(jobDetails.getLocation());
        if (jobDetails.getSalary() != null) job.setSalary(jobDetails.getSalary());
        if (jobDetails.getDescription() != null) job.setDescription(jobDetails.getDescription());
        if (jobDetails.getJobType() != null) job.setJobType(jobDetails.getJobType());
        if (jobDetails.getExperience() != null) job.setExperience(jobDetails.getExperience());
        if (jobDetails.getSkills() != null) job.setSkills(jobDetails.getSkills());
        if (jobDetails.getApplied() != null) job.setApplied(jobDetails.getApplied());
        if (jobDetails.getSaved() != null) job.setSaved(jobDetails.getSaved());
        if (jobDetails.getApplicantName() != null) job.setApplicantName(jobDetails.getApplicantName());
        if (jobDetails.getApplicantEmail() != null) job.setApplicantEmail(jobDetails.getApplicantEmail());
        if (jobDetails.getResumeFileName() != null) job.setResumeFileName(jobDetails.getResumeFileName());
        
        return ResponseEntity.ok(jobRepo.save(job));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable Long id) {
        jobRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}