package com.sudhamshu.salaryacquittanceapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sudhamshu.salaryacquittanceapi.model.AuditLogs;
import com.sudhamshu.salaryacquittanceapi.repository.AuditLogsRepository;

@Service
public class AuditLogsService {
    private final AuditLogsRepository auditLogsRepository;

    @Autowired
    public AuditLogsService(AuditLogsRepository auditLogsRepository) {
        this.auditLogsRepository = auditLogsRepository;
    }

    public List<AuditLogs> getAllAuditLogs() {
        return auditLogsRepository.findAllByOrderByCreatedDateDescCreatedTimeDesc();
    }
}
