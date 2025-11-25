package org.conghung.lab01.dto.response;

import lombok.Builder;

@Builder
public record LoginResponse(String token) {}
