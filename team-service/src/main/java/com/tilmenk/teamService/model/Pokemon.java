package com.tilmenk.teamService.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
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

    private String ImageUrl_large;
    private String ImageUrl_small;


    @Transient
    private Integer costs;

    public Pokemon(String name, String typ1, String typ2, Integer health,
                   Integer attack, Integer defense, Integer attack_sp,
                   Integer defense_sp, Integer speed, boolean legendary) {
        this.name = name;
        this.type1 = typ1;
        this.type2 = typ2;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.attack_sp = attack_sp;
        this.defense_sp = defense_sp;
        this.speed = speed;
        this.legendary = legendary;
    }

    public Integer getCosts() {
        int tempCosts =
                (this.attack + this.attack_sp + this.defense + this.defense_sp + this.speed + this.health) / 6;
        if (this.legendary) tempCosts *= 1.5;
        return tempCosts;
    }



    @Override
    public String toString() {
        return "Pokemon{" + "name='" + name + '\'' + ", typ1='" + type1 + '\'' + ", typ2='" + type2 + '\'' + ", health=" + health + ", attack=" + attack + ", defense=" + defense + ", attack_sp=" + attack_sp + ", defense_sp=" + defense_sp + ", legendary=" + legendary + ", speed=" + speed + ", anzahlAufLager=" + ", costs=" + costs + '}';
    }
}
