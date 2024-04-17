package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Coffee {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "coffee_seq")
    @SequenceGenerator(name="coffee_seq", sequenceName = "coffee_seq", initialValue = 1, allocationSize = 1)
    private Long id;
    @Column
    private String name;
    @Column
    private String price;
}
