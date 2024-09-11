package io.fulflix.hub.hubroute.api;

import io.fulflix.hub.hubroute.application.HubRouteService;
import io.fulflix.hub.hubroute.api.dto.HubRouteCreateDto;
import io.fulflix.hub.hubroute.api.dto.HubRouteResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HubRouteController {
    private final HubRouteService hubRouteService;

    // 허브 경로 생성
    @PostMapping("/hub-route")
    public ResponseEntity<HubRouteResponseDto> createHubRoute(@RequestBody HubRouteCreateDto dto) {
        HubRouteResponseDto responseDto = hubRouteService.createHubRoute(dto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}