package io.fulflix.hub.hubroute.application;

import io.fulflix.hub.hub.api.dto.HubResponseDto;
import io.fulflix.hub.hub.exception.HubErrorCode;
import io.fulflix.hub.hub.exception.HubException;
import io.fulflix.hub.hubroute.api.dto.HubRouteCreateDto;
import io.fulflix.hub.hubroute.api.dto.HubRouteResponseDto;
import io.fulflix.hub.hub.domain.Hub;
import io.fulflix.hub.hubroute.api.dto.HubRouteUpdateDto;
import io.fulflix.hub.hubroute.domain.HubRoute;
import io.fulflix.hub.hub.domain.HubRepository;
import io.fulflix.hub.hubroute.domain.HubRouteRepository;
import io.fulflix.hub.hubroute.exception.HubRouteErrorCode;
import io.fulflix.hub.hubroute.exception.HubRouteException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HubRouteService {

    private final HubRouteRepository hubRouteRepository;
    private final HubRepository hubRepository;

    // 허브 경로 생성
    @Transactional
    public HubRouteResponseDto createHubRoute(HubRouteCreateDto dto) {

        // 출발 허브 도착 허브 ID 비교
        validateHubIds(dto.getDepartureHubId(), dto.getArrivalHubId());

        Hub departureHub = getHubById(dto.getDepartureHubId());
        Hub arrivalHub = getHubById(dto.getArrivalHubId());

        HubRoute hubRoute = HubRoute.builder()
                .departureHub(departureHub)
                .arrivalHub(arrivalHub)
                .duration(dto.getDuration())
                .build();

        hubRouteRepository.save(hubRoute);
        return mapToDto(hubRoute);
    }

    // 허브 단건 조회
    public HubRouteResponseDto getHubRoute(Long hubRouteId) {
        HubRoute hubRoute = findHubRouteById(hubRouteId);
        return mapToDto(hubRoute);
    }

    // 허브 경로 전체 조회
    public Page<HubRouteResponseDto> getAllHubRoutes(Pageable pageable) {
        Page<HubRoute> hubRoutes = hubRouteRepository.findAll(pageable);
        return hubRoutes.map(this::mapToDto);
    }

    // 허브 경로 route 로 검색
    public Page<HubRouteResponseDto> searchHubRoutes(Pageable pageable, String keyword) {
        Page<HubRoute> hubRoutes = hubRouteRepository.findByRouteContaining(keyword, pageable);
        return hubRoutes.map(this::mapToDto);
    }

    // 허브 경로 수정
    @Transactional
    public HubRouteResponseDto updateHubRoute(Long hubRouteId, HubRouteUpdateDto dto) {

        HubRoute hubRoute = findHubRouteById(hubRouteId);

        // 출발 허브 도착 허브 ID 비교
        if (dto.getDepartureHubId() != null && dto.getArrivalHubId() != null) {
            validateHubIds(dto.getDepartureHubId(), dto.getArrivalHubId());
        }

        if (dto.getDepartureHubId() != null) {
            Hub departureHub = getHubById(dto.getDepartureHubId());
            hubRoute.setDepartureHub(departureHub);
        }

        if (dto.getArrivalHubId() != null) {
            Hub arrivalHub = getHubById(dto.getArrivalHubId());
            hubRoute.setArrivalHub(arrivalHub);
        }

        if (dto.getDuration() != null) {
            hubRoute.setDuration(dto.getDuration());
        }

        return mapToDto(hubRoute);
    }

    // 허브 경로 삭제
    @Transactional
    public void deleteHubRoute(Long hubRouteId) {
        HubRoute hubRoute = findHubRouteById(hubRouteId);
        hubRoute.delete();
    }


    // entity -> dto
    private HubRouteResponseDto mapToDto(HubRoute hubRoute) {
        HubRouteResponseDto dto = new HubRouteResponseDto();
        dto.setId(hubRoute.getId());
        dto.setDepartureHub(HubResponseDto.of(hubRoute.getDepartureHub()));
        dto.setArrivalHub(HubResponseDto.of(hubRoute.getArrivalHub()));
        dto.setDuration(hubRoute.getDuration());
        dto.setRoute(hubRoute.getRoute());
        return dto;
    }

    // 허브 경로 조회 메서드
    private HubRoute findHubRouteById(Long hubRouteId) {
        return hubRouteRepository.findById(hubRouteId)
                .orElseThrow(() -> new HubRouteException(HubRouteErrorCode.HUB_ROUTE_NOT_FOUND));
    }

    // 허브 조회 메서드
    private Hub getHubById(Long hubId) {
        return hubRepository.findById(hubId)
                .orElseThrow(() -> new HubException(HubErrorCode.HUB_NOT_FOUND));
    }

    // 출발 허브 도칙 허브 ID 비교 메서드
    private void validateHubIds(Long departureHubId, Long arrivalHubId) {
        if (departureHubId.equals(arrivalHubId)) {
            throw new HubRouteException(HubRouteErrorCode.SAME_DEPARTURE_AND_ARRIVAL);
        }
    }

}
