package io.fulflix.hub.hubroute.api;
import io.fulflix.hub.hubroute.api.dto.*;
import io.fulflix.hub.hubroute.application.HubRouteGenerator;
import io.fulflix.hub.hubroute.application.HubRouteService;
import io.fulflix.hub.hubroute.application.ShortestPathAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HubRouteController {
    private final HubRouteService hubRouteService;
    private final ShortestPathAlgorithm shortestPathAlgorithm;
    private final HubRouteGenerator hubRouteGenerator;

    // 허브 경로 생성
    @PostMapping("/hub-route")
    public ResponseEntity<HubRouteResponseDto> createHubRoute(@RequestBody HubRouteCreateDto dto) {
        HubRouteResponseDto responseDto = hubRouteService.createHubRoute(dto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 허브 경로 단건 조회
    @GetMapping("/hub-route/{hubRouteId}")
    public ResponseEntity<HubRouteResponseDto> getHubRoute(@PathVariable Long hubRouteId) {
        HubRouteResponseDto responseDto = hubRouteService.getHubRoute(hubRouteId);
        return ResponseEntity.ok(responseDto);
    }

    // 허브 경로 전체 조회
    @GetMapping("/hub-route")
    public ResponseEntity<Page<HubRouteResponseDto>> getAllHubRoutes(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable
            ) {
        Page<HubRouteResponseDto> responseDto = hubRouteService.getAllHubRoutes(pageable);
        return ResponseEntity.ok(responseDto);
    }

    // 허브 경로 route 로 검색
    @GetMapping("/hub-route/search")
    public ResponseEntity<Page<HubRouteResponseDto>> searchHubRoutes(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable,
            String keyword
    ) {
        Page<HubRouteResponseDto> responseDto = hubRouteService.searchHubRoutes(pageable, keyword);
        return ResponseEntity.ok(responseDto);
    }

    // 허브 경로 수정
    @PutMapping("/hub-route/{hubRouteId}")
    public ResponseEntity<HubRouteResponseDto> updateHubRoute(@PathVariable Long hubRouteId, @RequestBody HubRouteUpdateDto dto) {
        HubRouteResponseDto responseDto = hubRouteService.updateHubRoute(hubRouteId, dto);
        return ResponseEntity.ok(responseDto);
    }

    // 허브 경로 삭제
    @DeleteMapping("/hub-route/{hubRouteId}")
    public ResponseEntity<Void> deleteHubRoute(@PathVariable Long hubRouteId) {
        hubRouteService.deleteHubRoute(hubRouteId);
        return ResponseEntity.noContent().build();
    }

    // 허브 경로 전체 생성
    @PostMapping("/hub-route/generate")
    public ResponseEntity<Void> generateHubRoute() {
        hubRouteGenerator.generateHubRoutes();
        return ResponseEntity.noContent().build();
    }

     //최단 경로 찾기
    @PostMapping("/shortest-path")
    public List<ShortestPathResponse> findShortestPath(@RequestBody ShortestPathRequest request) {
        shortestPathAlgorithm.buildGraph();
        return shortestPathAlgorithm.findShortestPath(request.startHubId(), request.endHubId());
    }
}
