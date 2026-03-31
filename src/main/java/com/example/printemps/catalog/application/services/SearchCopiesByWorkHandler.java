package com.example.printemps.catalog.application.services;

import com.example.printemps.catalog.application.gateways.CopyRepository;
import com.example.printemps.catalog.application.usecases.SearchCopiesByWork;
import com.example.printemps.catalog.domain.Copy;
import com.example.printemps.catalog.domain.WorkId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchCopiesByWorkHandler implements SearchCopiesByWork {

    private final CopyRepository copyRepository;

    public SearchCopiesByWorkHandler(CopyRepository copyRepository) {
        this.copyRepository = copyRepository;
    }

    @Override
    public List<Copy> execute(WorkId workId) {
        return copyRepository.findByWorkId(workId);
    }
}