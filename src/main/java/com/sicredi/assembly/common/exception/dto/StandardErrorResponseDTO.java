package com.sicredi.assembly.common.exception.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class StandardErrorResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String message;
    private LocalDateTime datetime;
    private Long timestamp;

    public StandardErrorResponseDTO(String message) {
        super();
        this.message = message;
        this.timestamp = System.currentTimeMillis();
        this.datetime = LocalDateTime.ofEpochSecond(timestamp / 1000, 0, ZoneOffset.UTC);
    }

    public String getMessage() {
        return this.message;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public Long getTimestamp() {
        return timestamp;
    }

}
