package com.example.printemps.penalties.application.usecases;

import com.example.printemps.penalties.domain.Penalty;

import java.util.List;

public interface SearchPenaltiesByUser {
    List<Penalty> handle(String userId);
}
