package com.ahirajustice.configserver.modules.microservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpMethod;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestartConfig {

    @NotBlank(message = "url is required")
    private String url;
    @NotNull(message = "method is required")
    private HttpMethod method;
    private Map<String, List<String>> headers;
    private Map<String, String> params;

}
