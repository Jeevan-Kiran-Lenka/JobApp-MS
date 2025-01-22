package com.jeeorg.companyms.company.impl;


import com.jeeorg.companyms.company.Company;
import com.jeeorg.companyms.company.CompanyRepository;
import com.jeeorg.companyms.company.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> getAllCompany() {
        return companyRepository.findAll();
    }

    @Override
    public Boolean createCompany(Company company) {
        try{
            companyRepository.save(company);
            return true;
        }
        catch(Exception e){
            e.getLocalizedMessage();
            return false;
        }
    }

    @Override
    public Optional<Company> getCompany(Long companyId) {
        return companyRepository.findById(companyId);
    }

    @Override
    public Boolean updateCompany(Long companyId, Company newCompany) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        if (optionalCompany.isPresent()){
            Company company = optionalCompany.get();
            company.setName(newCompany.getName());
            company.setDescription(newCompany.getDescription());
            companyRepository.save(company);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Boolean deleteCompany(Long companyId) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        if(optionalCompany.isPresent()){
            try{
                companyRepository.delete(optionalCompany.get());
                return true;
            } catch (Exception e) {
                e.getLocalizedMessage();
                return false;
            }
        }else{
            return false;
        }
    }
}
