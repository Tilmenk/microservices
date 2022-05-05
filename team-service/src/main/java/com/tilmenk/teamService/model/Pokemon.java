package com.tilmenk.teamService.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@ToString
public class Pokemon implements Serializable {

    @Id
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

    @Transient
    private Currencies costs;

    private String ImageUrl_large;
    private String ImageUrl_small;

}


