package com.jeeorg.jobms.job.impl;


import com.jeeorg.jobms.job.Job;
import com.jeeorg.jobms.job.JobRepository;
import com.jeeorg.jobms.job.JobService;
import com.jeeorg.jobms.job.dto.JobWithCompanyDTO;
import com.jeeorg.jobms.job.external.Company;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.jeeorg.jobms.job.dto.JobWithCompanyDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    JobRepository jobRepository;

//   Bean managed by Spring, won't work if constructor is not called
    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }


    private JobWithCompanyDTO convertToDTo(Job job){
        JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();
        jobWithCompanyDTO.setJob(job);
        RestTemplate restTemplate = new RestTemplate();
        Company company = restTemplate.getForObject(
                "http://localhost:8081/companies/" + job.getCompanyId(),
                Company.class);
        jobWithCompanyDTO.setCompany(company);
        return jobWithCompanyDTO;
    }

    @Override
    public List<JobWithCompanyDTO> findAll() {

        List<Job> jobs = jobRepository.findAll();
        List<JobWithCompanyDTO> jobWithCompanyDTOS = new ArrayList<>();

        return jobs.stream().map(this::convertToDTo).collect(Collectors.toList());
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
