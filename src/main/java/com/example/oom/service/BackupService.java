package com.example.oom.service;

import com.example.oom.repository.OOMStatelessSessionSolutionPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BackupService {

    @Autowired
    private OOMStatelessSessionSolutionPlayerRepository repository;

    public void backup() {
        repository.backup();
    }
}
