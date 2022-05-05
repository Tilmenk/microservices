package com.tilmenk.apiGateway.model.teamService;

import com.tilmenk.apiGateway.model.teamService.currencyService.Currencies;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Pokemon implements Serializable {

    private String name;

    private String type1;

    private String type2;


    private Integer health;

    private Integer attack;

    private Integer defense;

    private Integer attack_sp;

    private Integer defense_sp;

    private Integer speed;

    private boolean legendary;

    private String ImageUrl_large;

    private String ImageUrl_small;

    private Currencies costs;


}


