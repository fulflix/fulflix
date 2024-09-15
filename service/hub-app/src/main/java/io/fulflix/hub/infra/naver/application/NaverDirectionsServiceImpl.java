package io.fulflix.hub.infra.naver.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.fulflix.hub.hub.domain.Hub;
import io.fulflix.hub.infra.naver.dto.RouteInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NaverDirectionsServiceImpl implements NaverDirectionsService {

    @Value("${naver.client-id}")
    private String clientId;

    @Value("${naver.client-secret}")
    private String clientSecret;

    @Override
    public RouteInfo getRouteInfo(Hub startHub, Hub endHub) {
        String apiUrl = "https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving";
        // 출발 허브 경도, 위도
        String startCoordinates = startHub.getLongitude() + ","
                + startHub.getLatitude();

        // 도착 허브 경도, 위도
        String endCoordinates = endHub.getLongitude() + ","
                + endHub.getLatitude();

        String url = apiUrl + "?start=" + startCoordinates + "&goal=" + endCoordinates;

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-APIGW-API-KEY-ID", clientId);
        headers.set("X-NCP-APIGW-API-KEY", clientSecret);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        // JSON 응답을 RouteInfo로 변환
        String responseBody = response.getBody();
        return parseSummary(responseBody);
    }

    private RouteInfo parseSummary(String responseBody) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseBody);
            JsonNode route = root.path("route");
            JsonNode summary = route.path("traoptimal").get(0).path("summary");

            double totalDistance = summary.path("distance").asDouble();
            long totalDuration = summary.path("duration").asLong();

            return new RouteInfo(totalDistance, totalDuration);
        } catch (Exception e) {
            e.printStackTrace();
            // 예외 처리에 맞는 적절한 값을 반환하거나 필요 시 null을 반환할 수 있습니다.
            return new RouteInfo(0, 0); // 혹은 throw new RuntimeException("Error parsing response");
        }
    }

}
