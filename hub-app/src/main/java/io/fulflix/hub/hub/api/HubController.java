package io.fulflix.hub.hub.api;

import io.fulflix.hub.hub.application.HubService;
import io.fulflix.hub.hub.api.dto.HubRequestDto;
import io.fulflix.hub.hub.api.dto.HubResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class HubController {
    private final HubService hubService;

    // 허브 생성
    @PostMapping("/hub")
    public ResponseEntity<HubResponseDto> createHub(@RequestBody HubRequestDto hubRequestDto) {
        HubResponseDto createdHub = hubService.createHub(hubRequestDto);
        return new ResponseEntity<>(createdHub, HttpStatus.CREATED);
    }

    // 허브 단건 조회
    @GetMapping("/hub/{hubId}")
    public ResponseEntity<HubResponseDto> getHubById(@PathVariable Long hubId) {
        HubResponseDto hub = hubService.getHubById(hubId);
        return ResponseEntity.ok(hub);
    }

//    // 허브 전체 조회
//    @GetMapping("/hubs")
//    public Page<HubResponseDto> getAllHubs() {
//
//    }

    // 허브 수정
    @PutMapping("/hub/{hubId}")
    public ResponseEntity<HubResponseDto> updateHub(@PathVariable Long hubId, @RequestBody HubRequestDto hubRequestDto) {
        HubResponseDto hub = hubService.updateHub(hubId, hubRequestDto);
        return new ResponseEntity<>(hub, HttpStatus.OK);
    }

    // 허브 삭제
    @DeleteMapping("/hub/{hubId}")
    public ResponseEntity<String> deleteHub(@PathVariable Long hubId) {
        hubService.deleteHub(hubId);
        return ResponseEntity.ok("허브가 성공적으로 삭제되었습니다.");
    }

}