package io.fulflix.infra.client.external.naver;

import static org.assertj.core.api.Assertions.assertThat;

import io.fulflix.common.web.base.TestBase;
import io.fulflix.hub.HubAppApplication;
import io.fulflix.infra.client.external.naver.dto.RouteResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@Disabled
@SpringBootTest(classes = HubAppApplication.class, webEnvironment = WebEnvironment.MOCK)
@DisplayName("Client:NaverDirection")
class NaverDirectionClientTest extends TestBase {

    private final NaverDirectionClient naverDirectionClient;

    public NaverDirectionClientTest(NaverDirectionClient naverDirectionClient) {
        this.naverDirectionClient = naverDirectionClient;
    }

    @Test
    @DisplayName("네이버 지도 API를 이용한 두 위/경도 사이의 거리 조회")
    void getDirection() {
        // Given
        String startCoordinates = "127.1058342" + ",37.3597078";
        String endCoordinates = "129.0759853" + ",35.1794697";

        // When
        RouteResponse actual = naverDirectionClient.getDirection(
            startCoordinates,
            endCoordinates
        );

        // Then
        assertThat(actual.getCode()).isZero();
    }

}
