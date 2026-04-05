package com.example.printemps.hold.application.usecases;

import com.example.printemps.hold.domain.Hold;

import java.util.List;

public interface SearchHoldsByUserId {
    List<Hold> handle(String userId);
}
