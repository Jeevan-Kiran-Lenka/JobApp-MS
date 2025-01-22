package com.jeeorg.jobms.job.impl;


import com.jeeorg.jobms.job.Job;
import com.jeeorg.jobms.job.JobRepository;
import com.jeeorg.jobms.job.JobService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    JobRepository jobRepository;

//   Bean managed by Spring, won't work if constructor is not called
    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Override
    public void createJob(Job job) {
//        job.setId(nextId++);
        jobRepository.save(job);
    }

    @Override
    public Optional<Job> getJob(Long jobId) {
        return jobRepository.findById(jobId);
    }

    @Override
    public Boolean deleteJob(Long jobId) {
        Optional<Job> jobOptional = jobRepository.findById(jobId);
        if(jobOptional.isPresent()){
            try{
                jobRepository.delete(jobOptional.get());
                return true;
            } catch (Exception e) {
                e.getLocalizedMessage();
                return false;
            }
        }else{
            return false;
        }
    }

    @Override
    public Job updateJob(Long jobId, Job newJob) {
        Optional<Job> jobOptional = jobRepository.findById(jobId);
        if(jobOptional.isPresent()){
                Job job = jobOptional.get();
                job.setDescription(newJob.getDescription());
                job.setLocation(newJob.getLocation());
                job.setTitle(newJob.getTitle());
                job.setMaxSalary(newJob.getMaxSalary());
                job.setMinSalary(newJob.getMinSalary());
                job.setCompanyId(newJob.getCompanyId());
                jobRepository.save(job);
        }
        return newJob;
    }
}
