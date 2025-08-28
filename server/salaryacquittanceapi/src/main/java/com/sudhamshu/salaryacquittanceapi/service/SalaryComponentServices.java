package com.sudhamshu.salaryacquittanceapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sudhamshu.salaryacquittanceapi.annotation.LoggableAction;
import com.sudhamshu.salaryacquittanceapi.model.SalaryComponents;
import com.sudhamshu.salaryacquittanceapi.repository.SalaryComponentRepository;

@Service
public class SalaryComponentServices {
    private final SalaryComponentRepository salaryComponentsRepository;

    @Autowired
    public SalaryComponentServices(SalaryComponentRepository salaryComponentsRepository) {
        this.salaryComponentsRepository = salaryComponentsRepository;
    }

    @LoggableAction("New Rule")
    public void saveSalaryComponent(SalaryComponents salaryComponents) {
        salaryComponentsRepository.save(salaryComponents);
    }

    public List<SalaryComponents> getAllSalaryComponents() {
        return salaryComponentsRepository.findAll();
    }

    public SalaryComponents getSalaryComponentById(Long id) {
        return salaryComponentsRepository.findById(id).orElse(null);
    }

    public SalaryComponents getSalaryComponentsByName(String componentName) {
        return salaryComponentsRepository.findByComponentName(componentName);
    }

}
