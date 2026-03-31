package com.example.printemps.catalog.application.usecases;

import com.example.printemps.catalog.domain.Work;
import com.example.printemps.catalog.domain.WorkId;

public interface SearchWorkById {
    Work execute(WorkId workId);
}