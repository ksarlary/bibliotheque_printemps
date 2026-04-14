package com.example.printemps.catalog.application.usecases;

import com.example.printemps.catalog.domain.Copy;
import com.example.printemps.catalog.domain.CopyId;

public interface ArriveCopy {
    Copy execute(CopyId copyId);
}
