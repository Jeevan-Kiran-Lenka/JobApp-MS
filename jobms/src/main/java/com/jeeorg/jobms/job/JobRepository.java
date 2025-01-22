package com.jeeorg.jobms.job;

import org.springframework.data.jpa.repository.JpaRepository;

// What are different types of repository that can be used?
public interface JobRepository extends JpaRepository<Job, Long> {

}
