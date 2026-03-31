package com.example.printemps.catalog.application.usecases;

import com.example.printemps.catalog.application.models.UpdateWorkRequest;
import com.example.printemps.catalog.domain.Work;
import com.example.printemps.catalog.domain.WorkId;

public interface UpdateWork {
    Work execute(WorkId workId, UpdateWorkRequest request);
}