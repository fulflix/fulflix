package io.fulflix.hub.hubroute.application;

import io.fulflix.hub.hub.exception.HubErrorCode;
import io.fulflix.hub.hub.exception.HubException;
import io.fulflix.hub.hubroute.api.dto.HubRouteCreateDto;
import io.fulflix.hub.hubroute.api.dto.HubRouteResponseDto;
import io.fulflix.hub.hub.domain.Hub;
import io.fulflix.hub.hubroute.domain.HubRoute;
import io.fulflix.hub.hub.domain.HubRepository;
import io.fulflix.hub.hubroute.domain.HubRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HubRouteService {
    private final HubRouteRepository hubRouteRepository;
    private final HubRepository hubRepository;


    // 허브 경로 생성
    @Transactional
    public HubRouteResponseDto createHubRoute(HubRouteCreateDto dto) {
        Hub departureHub = hubRepository.findById(dto.getDepartureHubId())
                .orElseThrow(() -> new HubException(HubErrorCode.HUB_NOT_FOUND));
        Hub arrivalHub = hubRepository.findById(dto.getArrivalHubId())
                .orElseThrow(() -> new HubException(HubErrorCode.HUB_NOT_FOUND));

        HubRoute hubRoute = HubRoute.builder()
                .departureHub(departureHub)
                .arrivalHub(arrivalHub)
                .duration(dto.getDuration())
                .build();

        HubRoute savedHubRoute = hubRouteRepository.save(hubRoute);
        return mapToDto(savedHubRoute);
    }

    // entity -> dto
    private HubRouteResponseDto mapToDto(HubRoute hubRoute) {
        HubRouteResponseDto dto = new HubRouteResponseDto();
        dto.setId(hubRoute.getId());
        dto.setDepartureHubId(hubRoute.getDepartureHub().getId());
        dto.setArrivalHubId(hubRoute.getArrivalHub().getId());
        dto.setDuration(hubRoute.getDuration());
        dto.setRoute(hubRoute.getRoute());
        return dto;
    }

}
