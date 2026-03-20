package com.example.printemps.catalog.application.usecases;

import com.example.printemps.catalog.application.models.AddCopyRequest;
import com.example.printemps.catalog.domain.Copy;

public interface AddCopy {
    Copy execute(AddCopyRequest request);
}