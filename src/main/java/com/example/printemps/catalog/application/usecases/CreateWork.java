package com.example.printemps.catalog.application.usecases;

import com.example.printemps.catalog.application.models.CreateWorkRequest;
import com.example.printemps.catalog.domain.Work;

public interface CreateWork {
    Work execute(CreateWorkRequest request);
}