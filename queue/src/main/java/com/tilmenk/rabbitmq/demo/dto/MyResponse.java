package com.tilmenk.rabbitmq.demo.dto;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class MyResponse implements Serializable {
 String answer;
}
