package com.tilmenk.teamService.model;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Getter
@Setter
@Builder
public class Team implements Serializable {
    @Id
    @SequenceGenerator(name = "team_sequence", sequenceName =
            "team_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
            "team_sequence")
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Pokemon> pokemon;

    private String name;

    private String creator;

}
