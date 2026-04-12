package com.example.printemps.catalog.application.usecases;

import com.example.printemps.catalog.domain.Work;
import com.example.printemps.catalog.domain.WorkId;

import java.util.List;

public interface SearchSimilarWorks {
    List<Work> execute(WorkId workId);
}
