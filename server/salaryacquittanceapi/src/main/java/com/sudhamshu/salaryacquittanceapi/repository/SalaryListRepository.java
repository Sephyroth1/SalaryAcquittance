package com.sudhamshu.salaryacquittanceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sudhamshu.salaryacquittanceapi.model.SalaryList;

public interface SalaryListRepository extends JpaRepository<SalaryList, Long> {

}
