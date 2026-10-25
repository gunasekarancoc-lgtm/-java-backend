package com.taskmanager.backened.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "job_posts")
public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String company;
    private String location;

    @Column(length = 1000)
    private String description;

    private String salary;

    private String jobType = "Full-Time";
    private String experience = "0 - 2 Yrs";
    private String skills = "";

    private Boolean applied = false;
    private Boolean saved = false;

    private String applicantName;
    private String applicantEmail;
    private String resumeFileName; // Stores the uploaded PDF filename

    public JobPost() {
    }

    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return this.title; }
    public void setTitle(String title) { this.title = title; }

    public String getCompany() { return this.company; }
    public void setCompany(String company) { this.company = company; }

    public String getLocation() { return this.location; }
    public void setLocation(String location) { this.location = location; }

    public String getDescription() { return this.description; }
    public void setDescription(String description) { this.description = description; }

    public String getSalary() { return this.salary; }
    public void setSalary(String salary) { this.salary = salary; }

    public String getJobType() { return this.jobType != null ? this.jobType : "Full-Time"; }
    public void setJobType(String jobType) { this.jobType = jobType; }

    public String getExperience() { return this.experience != null ? this.experience : "0 - 2 Yrs"; }
    public void setExperience(String experience) { this.experience = experience; }

    public String getSkills() { return this.skills != null ? this.skills : ""; }
    public void setSkills(String skills) { this.skills = skills; }

    public Boolean getApplied() { return this.applied != null ? this.applied : false; }
    public void setApplied(Boolean applied) { this.applied = applied; }

    public Boolean getSaved() { return this.saved != null ? this.saved : false; }
    public void setSaved(Boolean saved) { this.saved = saved; }

    public String getApplicantName() { return this.applicantName; }
    public void setApplicantName(String applicantName) { this.applicantName = applicantName; }

    public String getApplicantEmail() { return this.applicantEmail; }
    public void setApplicantEmail(String applicantEmail) { this.applicantEmail = applicantEmail; }

    public String getResumeFileName() { return this.resumeFileName; }
    public void setResumeFileName(String resumeFileName) { this.resumeFileName = resumeFileName; }
}