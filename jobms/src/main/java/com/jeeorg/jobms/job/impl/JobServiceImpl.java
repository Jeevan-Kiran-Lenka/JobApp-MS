package com.jeeorg.jobms.job.impl;


import com.jeeorg.jobms.job.Job;
import com.jeeorg.jobms.job.JobRepository;
import com.jeeorg.jobms.job.JobService;
import com.jeeorg.jobms.job.dto.JobDTO;
import com.jeeorg.jobms.job.external.Company;
import com.jeeorg.jobms.job.external.Review;
import com.jeeorg.jobms.job.mapper.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    JobRepository jobRepository;

    @Autowired
    RestTemplate restTemplate;

//   Bean managed by Spring, won't work if constructor is not called
    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }


    private JobDTO convertToDTo(Job job){
        Company company = restTemplate.getForObject(
                "http://companyms:8081/companies/" + job.getCompanyId(),
                Company.class);
        ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange(
                "http://reviewms:8083/reviews?companyId=" + job.getCompanyId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Review>>() {
                });

        List<Review> reviews = reviewResponse.getBody();

        JobDTO jobDTO = JobMapper.mapToJobWithCompanyDto(job, company, reviews);
        return jobDTO;
    }

    @Override
    public List<JobDTO> findAll() {

        List<Job> jobs = jobRepository.findAll();

        return jobs.stream().map(this::convertToDTo).collect(Collectors.toList());
    }

    @Override
    public void createJob(Job job) {
//        job.setId(nextId++);
        jobRepository.save(job);
    }

    @Override
    public JobDTO getJob(Long jobId) {
        Job job = jobRepository.findById(jobId).orElse(null);
        return convertToDTo(job);
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
