package com.taskmanager.backened.repository;

import com.taskmanager.backened.model.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<JobPost, Long> {
}