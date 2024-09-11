package io.fulflix.hub.hub.application;

import io.fulflix.hub.hub.api.dto.HubRequestDto;
import io.fulflix.hub.hub.api.dto.HubResponseDto;
import io.fulflix.hub.hub.domain.Hub;
import io.fulflix.hub.hub.domain.HubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HubService {

    private final HubRepository hubRepository;

    // 허브 생성
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

        Hub savedHub = hubRepository.save(hub);
        return HubResponseDto.of(savedHub);
    }

    @Transactional
    public void deleteHub(Long hubId) {
        Hub hub = findHubById(hubId);
        hubRepository.delete(hub);
    }




    public Hub findHubById(Long id) {
        return hubRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Hub not found with id " + id)
        );
    }
}