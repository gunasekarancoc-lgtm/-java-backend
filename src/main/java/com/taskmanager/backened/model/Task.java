package com.taskmanager.backened.model;

import jakarta.persistence.*;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String assignedEmail; // Added to fix the repository dependency error

    // Getters and Setters
    public Long getId() { 
        return id; 
    }
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public String getTitle() { 
        return title; 
    }
    public void setTitle(String title) { 
        this.title = title; 
    }
    
    public String getAssignedEmail() { 
        return assignedEmail; 
    }
    public void setAssignedEmail(String assignedEmail) { 
        this.assignedEmail = assignedEmail; 
    }
}