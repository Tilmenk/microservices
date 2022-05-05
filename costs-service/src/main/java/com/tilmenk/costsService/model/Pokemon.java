package com.tilmenk.costsService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pokemon implements Serializable {

    private Integer health;

    private Integer attack;

    private Integer defense;

    private Integer attack_sp;

    private Integer defense_sp;

    private Integer speed;

    private Boolean legendary;
}
