package com.example.printemps.catalog.application.usecases;

import com.example.printemps.catalog.domain.Copy;
import com.example.printemps.catalog.domain.WorkId;

import java.util.List;

public interface SearchCopiesByWork {
    List<Copy> execute(WorkId workId);
}