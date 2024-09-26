package com.nemo.oceanAcademy.common.db;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

public class SubdomainRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        // RequestContextHolder를 통해 현재 HTTP 요청 컨텍스트가 있는지 확인
        if (RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String host = request.getServerName();  // 서브도메인을 감지

            // dev 서브 도메인이거나 로컬 환경이면 dev 데이터베이스 사용
            if (host.startsWith("dev.") || "localhost".equals(host)) {
                return "dev";
            }
        }
        // HTTP 요청이 없거나 기본적으로 prod 데이터베이스 사용
        return "prod";
    }
}

