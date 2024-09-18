package io.fulflix.hub.hubroute.api.dto;

public record ShortestPathRequest(
        Long startHubId,
        Long endHubId
) {
}
