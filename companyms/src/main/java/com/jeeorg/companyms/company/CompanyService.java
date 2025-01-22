package com.jeeorg.companyms.company;

import java.util.List;
import java.util.Optional;

public interface CompanyService {

    List<Company> getAllCompany();
    Boolean createCompany(Company company);
    Optional<Company> getCompany (Long companyId);
    Boolean updateCompany(Long companyId, Company newCompany);
    Boolean deleteCompany(Long companyId);

}
