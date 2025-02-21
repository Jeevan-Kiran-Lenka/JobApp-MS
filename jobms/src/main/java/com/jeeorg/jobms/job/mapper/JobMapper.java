package com.jeeorg.jobms.job.mapper;

import com.jeeorg.jobms.job.Job;
import com.jeeorg.jobms.job.dto.JobDTO;
import com.jeeorg.jobms.job.external.Company;
import com.jeeorg.jobms.job.external.Review;

import java.util.List;

public class JobMapper {
    public static JobDTO mapToJobWithCompanyDto(Job job, Company company, List<Review> review){
        JobDTO jobDTO = new JobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setCompany(company);
        jobDTO.setTitle(job.getTitle());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setReviews(review);

        return jobDTO;
    }
}
