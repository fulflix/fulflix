package io.fulflix.hub.infra.naver.application;

import io.fulflix.hub.hub.domain.Hub;
import io.fulflix.hub.infra.naver.dto.RouteInfo;

public interface NaverDirectionsService {
   RouteInfo getRouteInfo(Hub startHub, Hub endHub);
}
