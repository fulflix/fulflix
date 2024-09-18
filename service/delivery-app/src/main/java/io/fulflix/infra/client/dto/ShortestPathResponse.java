package io.fulflix.infra.client.dto;

public record ShortestPathResponse(
        Integer sequence,
        Long departureHubId,
        Long arrivalHubId,
        Double estimatedDistance,
        Long estimatedDuration
) {
}