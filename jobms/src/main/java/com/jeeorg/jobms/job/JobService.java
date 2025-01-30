package com.jeeorg.jobms.job;

import com.jeeorg.jobms.job.dto.JobWithCompanyDTO;

import java.util.List;
import java.util.Optional;

public interface JobService {
    List<JobWithCompanyDTO> findAll();
    void createJob(Job job);
    Optional<Job> getJob(Long jobId);
    Boolean deleteJob(Long jobId);
    Job updateJob(Long jobId, Job newJob);

}
