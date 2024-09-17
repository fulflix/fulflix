package io.fulflix.hub;

import io.fulflix.hub.hub.api.dto.HubRequestDto;
import io.fulflix.hub.hub.application.HubService;
import io.fulflix.hub.hub.domain.Hub;
import io.fulflix.hub.hub.domain.HubRepository;
import io.fulflix.hub.hubroute.application.HubRouteGenerator;
import io.fulflix.hub.hubroute.application.ShortestPathAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ShortestPathTest {
    @Autowired
    private HubService hubService;

    @Autowired
    private HubRepository hubRepository;

    @Autowired
    private HubRouteGenerator hubRouteGenerator;

    @Autowired
    private ShortestPathAlgorithm shortestPathAlgorithm;

    @Test
    void testCreateMultipleHubs() {
        // Given: 허브 생성 데이터를 리스트로 생성
        List<HubRequestDto> hubs = List.of(
                new HubRequestDto("서울특별시 센터", "서울특별시 송파구 송파대로 55", 37.514220, 127.105165),
                new HubRequestDto("경기 북부 센터", "경기도 고양시 덕양구 권율대로 570", 37.658359, 126.832020),
                new HubRequestDto("경기 남부 센터", "경기도 이천시 덕평로 257-21", 37.234145, 127.498340),
                new HubRequestDto("부산광역시 센터", "부산 동구 중앙대로 206", 35.114725, 129.042270),
                new HubRequestDto("대구광역시 센터", "대구 북구 태평로 161", 35.881412, 128.582595),
                new HubRequestDto("인천광역시 센터", "인천 남동구 정각로 29", 37.409264, 126.687596),
                new HubRequestDto("광주광역시 센터", "광주 서구 내방로 111", 35.159618, 126.851426),
                new HubRequestDto("대전광역시 센터", "대전 서구 둔산로 100", 36.351559, 127.378953),
                new HubRequestDto("울산광역시 센터", "울산 남구 중앙로 201", 35.538377, 129.311371),
                new HubRequestDto("세종특별자치시 센터", "세종특별자치시 한누리대로 2130", 36.480009, 127.289203),
                new HubRequestDto("강원특별자치도 센터", "강원특별자치도 춘천시 중앙로 1", 37.881316, 127.729671),
                new HubRequestDto("충청북도 센터", "충북 청주시 상당구 상당로 82", 36.636998, 127.489978),
                new HubRequestDto("충청남도 센터", "충남 홍성군 홍북읍 충남대로 21", 36.601971, 126.660704),
                new HubRequestDto("전북특별자치도 센터", "전북특별자치도 전주시 완산구 효자로 225", 35.824193, 127.148000),
                new HubRequestDto("전라남도 센터", "전남 무안군 삼향읍 오룡길 1", 34.816437, 126.459966),
                new HubRequestDto("경상북도 센터", "경북 안동시 풍천면 도청대로 455", 36.567202, 128.727488),
                new HubRequestDto("경상남도 센터", "경남 창원시 의창구 중앙대로 300", 35.234124, 128.681938)
        );

        // When: 허브를 각각 생성
        for (HubRequestDto hubDto : hubs) {
            hubService.createHub(hubDto);
        }

    }


    @Test
    void testHubRouteGenerator() {
        hubRouteGenerator.generateHubRoutes();
    }

    @Test
    void setShortestPathAlgorithm() {
        shortestPathAlgorithm.buildGraph();

        //List<Hub> hubs = hubRepository.findAll();

//        for (int i = 0; i < hubs.size(); i++) {
//            for (int j = i + 1; j < hubs.size(); j++) { // (i, j) 쌍만 처리하여 중복 제거
//                Hub startHub = hubs.get(i);
//                Hub endHub = hubs.get(j);
//
//                shortestPathService.findShortestPath(startHub.getId(), endHub.getId());
//
//            }
//        }

        shortestPathAlgorithm.findShortestPath(1L, 4L);
    }

}
