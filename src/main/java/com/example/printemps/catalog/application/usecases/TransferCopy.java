package com.example.printemps.catalog.application.usecases;

import com.example.printemps.catalog.application.models.TransferCopyRequest;
import com.example.printemps.catalog.domain.Copy;
import com.example.printemps.catalog.domain.CopyId;

public interface TransferCopy {
    Copy execute(CopyId copyId, TransferCopyRequest request);
}
