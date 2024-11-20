package com.aditya.project.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Placement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String organization;

    private String profile;
    private String description;

    @Column(nullable = true)
    private Integer intake;

    @Column(nullable = true)
    private Float minimum_grade;

}
