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
public class PlacementFilter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne
    @JoinColumn(name = "placement_id", referencedColumnName = "id")  // Specify referenced column in Placement
    private Placement placement;

    @ManyToOne
    @JoinColumn(name= "specialization", referencedColumnName = "specialization_id",nullable = true)
    private Specialization specialization;

    @ManyToOne
    @JoinColumn(name ="domain", referencedColumnName = "domain_id",nullable = true)
    private Domain domain;
}