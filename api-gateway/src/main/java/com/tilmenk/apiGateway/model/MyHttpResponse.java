package com.tilmenk.apiGateway.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
@AllArgsConstructor
public class MyHttpResponse<T extends Object> {

    public T data;

    public String message;

    public MyHttpResponse(String message) {
        this.message = message;
    }

}
