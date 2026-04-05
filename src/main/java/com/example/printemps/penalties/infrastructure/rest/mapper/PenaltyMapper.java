package com.example.printemps.penalties.infrastructure.rest.mapper;

import com.example.printemps.penalties.domain.Penalty;
import com.example.printemps.penalties.infrastructure.rest.dto.PenaltyDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PenaltyMapper {

    public PenaltyDTO toPenaltyDTO(Penalty penalty) {
        return new PenaltyDTO(
                penalty.getId().value(),
                penalty.getUserId(),
                penalty.getType().name(),
                penalty.getAmount(),
                penalty.getReason(),
                penalty.getStatus().name(),
                penalty.getCreatedAt()
        );
    }

    public List<PenaltyDTO> toPenaltyDTOList(List<Penalty> penalties) {
        return penalties.stream().map(this::toPenaltyDTO).toList();
    }
}