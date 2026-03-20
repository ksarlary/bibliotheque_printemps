package com.example.printemps.catalog.application.usecases;

import com.example.printemps.catalog.domain.Work;
import java.util.List;

public interface SearchWorks {
    List<Work> execute(String keyword);
}


