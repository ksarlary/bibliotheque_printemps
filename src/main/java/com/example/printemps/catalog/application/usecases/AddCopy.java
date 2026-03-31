package com.example.printemps.catalog.application.usecases;

import com.example.printemps.catalog.application.models.AddCopyRequest;
import com.example.printemps.catalog.domain.Copy;
import com.example.printemps.catalog.domain.WorkId;

public interface AddCopy {
    Copy execute(WorkId workId, AddCopyRequest request);
}