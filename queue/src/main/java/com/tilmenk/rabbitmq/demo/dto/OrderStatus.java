package com.tilmenk.rabbitmq.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderStatus implements Serializable {

    private Order order;
    private String status;//progress,completed
    private String message;
}