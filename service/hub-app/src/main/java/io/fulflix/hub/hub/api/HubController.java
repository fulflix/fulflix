package io.fulflix.hub.hub.api;

import io.fulflix.common.app.context.annotation.CurrentUser;
import io.fulflix.common.app.context.annotation.CurrentUserRole;
import io.fulflix.common.web.principal.Role;
import io.fulflix.hub.hub.api.dto.HubRequestDto;
import io.fulflix.hub.hub.api.dto.HubResponseDto;
import io.fulflix.hub.hub.application.HubService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HubController {

    private final HubService hubService;

    // 허브 생성
    @PostMapping("/hub")
    public ResponseEntity<HubResponseDto> createHub(
        @CurrentUser Long currentUser,
        @CurrentUserRole Role role,
        @RequestBody HubRequestDto hubRequestDto
    ) {
        HubResponseDto createdHub = hubService.createHub(hubRequestDto);
        return new ResponseEntity<>(createdHub, HttpStatus.CREATED);
    }

    // 허브 단건 조회
    @GetMapping("/hub/{hubId}")
    public ResponseEntity<HubResponseDto> getHubById(@PathVariable Long hubId) {
        HubResponseDto hub = hubService.getHubById(hubId);
        return ResponseEntity.ok(hub);
    }

    // 허브 전체 조회
    @GetMapping("/hubs")
    public ResponseEntity<Page<HubResponseDto>> getAllHubs(@PageableDefault(
        page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.ASC
    ) Pageable pageable) {
        Page<HubResponseDto> hubResponseDto = hubService.getAllHubs(pageable);
        return ResponseEntity.ok(hubResponseDto);
    }

    // 허브 이름으로 검색
    @GetMapping("/hubs/search")
    public ResponseEntity<Page<HubResponseDto>> searchHubs(@PageableDefault(
        page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.ASC
    ) Pageable pageable, @RequestParam String keyword) {
        Page<HubResponseDto> hubResponseDto = hubService.searchHubs(pageable, keyword);
        return ResponseEntity.ok(hubResponseDto);
    }

    // 허브 수정
    @PutMapping("/hub/{hubId}")
    public ResponseEntity<HubResponseDto> updateHub(@PathVariable Long hubId,
        @RequestBody HubRequestDto hubRequestDto) {
        HubResponseDto hub = hubService.updateHub(hubId, hubRequestDto);
        return new ResponseEntity<>(hub, HttpStatus.OK);
    }

    // 허브 삭제
    @DeleteMapping("/hub/{hubId}")
    public ResponseEntity<String> deleteHub(@PathVariable Long hubId) {
        hubService.deleteHub(hubId);
        return ResponseEntity.noContent().build();
    }

}
