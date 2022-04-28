package com.tilmenk.teamService.pokemon;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table
public class Pokemon {

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

    public Pokemon() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String typ1) {
        this.type1 = typ1;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String typ2) {
        this.type2 = typ2;
    }

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public Integer getAttack() {
        return attack;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    public Integer getDefense() {
        return defense;
    }

    public void setDefense(Integer defense) {
        this.defense = defense;
    }

    public Integer getAttack_sp() {
        return attack_sp;
    }

    public void setAttack_sp(Integer attack_sp) {
        this.attack_sp = attack_sp;
    }

    public Integer getDefense_sp() {
        return defense_sp;
    }

    public void setDefense_sp(Integer defense_sp) {
        this.defense_sp = defense_sp;
    }

    public boolean isLegendary() {
        return legendary;
    }

    public void setLegendary(boolean legendary) {
        this.legendary = legendary;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
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
