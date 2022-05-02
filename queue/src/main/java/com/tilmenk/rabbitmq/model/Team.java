package com.tilmenk.rabbitmq.model;


import lombok.*;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Team implements Serializable {

    private Long id;

    private List<Pokemon> pokemon;

    private String name;

    private String creator;

    private double costs;

}
