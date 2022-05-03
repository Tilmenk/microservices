package com.tilmenk.apiGateway.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Team implements Serializable {

    private Long id;
    private String name;
    private String creator;
    private CurrenciesResponse costs;

    private List<String> pokemon;

    public record CurrenciesResponse(double euro, double bitcoin,
                                     double dollar) implements Serializable {
    }
}
