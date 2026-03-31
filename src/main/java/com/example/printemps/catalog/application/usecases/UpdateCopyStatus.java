package com.example.printemps.catalog.application.usecases;

import com.example.printemps.catalog.application.models.UpdateCopyStatusRequest;
import com.example.printemps.catalog.domain.Copy;
import com.example.printemps.catalog.domain.CopyId;

public interface UpdateCopyStatus {
    Copy execute(CopyId copyId, UpdateCopyStatusRequest request);
}