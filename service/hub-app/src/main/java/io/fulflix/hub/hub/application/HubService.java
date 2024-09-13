package io.fulflix.hub.hub.application;

import io.fulflix.hub.hub.api.dto.HubRequestDto;
import io.fulflix.hub.hub.api.dto.HubResponseDto;
import io.fulflix.hub.hub.domain.Hub;
import io.fulflix.hub.hub.domain.HubRepository;
import io.fulflix.hub.hub.exception.HubErrorCode;
import io.fulflix.hub.hub.exception.HubException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HubService {

    private final HubRepository hubRepository;

    // 허브 생성
    @Transactional
    public HubResponseDto createHub(HubRequestDto hubRequestDto) {
        Hub hub = HubRequestDto.toEntity(hubRequestDto);

        Hub savedHub = hubRepository.save(hub);

        return HubResponseDto.of(savedHub);
    }

    // 허브 단건 조회
    public HubResponseDto getHubById(Long id) {
        Hub hub = findHubById(id);
        return HubResponseDto.of(hub);
    }

    // 허브 전체 조회
    public Page<HubResponseDto> getAllHubs(Pageable pageable) {
        Page<Hub> hubs = hubRepository.findAll(pageable);
        return hubs.map(HubResponseDto::of);
    }

    // 허브 이름으로 검색
    public Page<HubResponseDto> searchHubs(Pageable pageable, String keyword) {
        Page<Hub> hubs = hubRepository.findByNameContaining(keyword, pageable);
        return hubs.map(HubResponseDto::of);
    }

    // 허브 수정
    @Transactional
    public HubResponseDto updateHub(Long hubId, HubRequestDto hubRequestDto) {
        Hub hub = findHubById(hubId);

        if(hubRequestDto.getName() != null) {
            hub.setName(hubRequestDto.getName());
        }
        if(hubRequestDto.getAddress() != null) {
            hub.setAddress(hubRequestDto.getAddress());
        }
        if(hubRequestDto.getLatitude() != null) {
            hub.setLatitude(hubRequestDto.getLatitude());
        }
        if(hubRequestDto.getName() != null) {
            hub.setLongitude(hubRequestDto.getLongitude());
        }
        return HubResponseDto.of(hub);
    }

    // 허브 삭제
    @Transactional
    public void deleteHub(Long hubId) {
        Hub hub = findHubById(hubId);
        hub.delete();
    }

    public Hub findHubById(Long id) {
        return hubRepository.findById(id).orElseThrow(
                () -> new HubException(HubErrorCode.HUB_NOT_FOUND)
        );
    }

}
