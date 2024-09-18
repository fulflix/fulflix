package io.fulflix.infra.client.dto;

public record ShortestPathRequest(
        Long startHubId,
        Long endHubId
) {
}