package com.jeeorg.jobms.job;

import com.jeeorg.jobms.job.dto.JobDTO;

import java.util.List;

public interface JobService {
    List<JobDTO> findAll();
    void createJob(Job job);
    JobDTO getJob(Long jobId);
    Boolean deleteJob(Long jobId);
    Job updateJob(Long jobId, Job newJob);

}
