package com.example.printemps.hold.infrastructure.rest.mapper;

import com.example.printemps.hold.domain.Hold;
import com.example.printemps.hold.infrastructure.rest.dto.HoldDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HoldMapper {

    public HoldDTO toDTO(Hold hold) {
        return new HoldDTO(
                hold.getId().value(),
                hold.getWorkId(),
                hold.getUserId(),
                hold.getStatus().name(),
                hold.getQueuePosition(),
                hold.getPickupUntil()
        );
    }

    public List<HoldDTO> toDTOList(List<Hold> holds) {
        return holds.stream().map(this::toDTO).toList();
    }
}
