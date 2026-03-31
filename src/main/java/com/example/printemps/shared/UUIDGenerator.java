package com.example.printemps.shared;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
class UUIDGenerator implements DomainIdGenerator {
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
