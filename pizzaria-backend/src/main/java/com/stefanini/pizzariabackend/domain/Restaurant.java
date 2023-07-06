package com.stefanini.pizzariabackend.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "Restaurant")
@Table(name = "Restaurant")
public class Restaurant {

    @Id
    @SequenceGenerator(
            name = "restaurant_sequence",
            sequenceName = "restaurant_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "restaurant_sequence"
    )
    @Column(
            name = "id",
            nullable = false,
            updatable = false
    )
    private Long id;

    @Column(
            name = "address",
            nullable = false
    )
    private String address;

    @Column(
            name = "contact_number"
    )
    private String contactNumber;

    @Column(
            name = "work_hours_in_work_days",
            nullable = false
    )
    private String workHoursInWorkDays;

    @Column(
            name = "work_hours_in_weekend",
            nullable = false
    )
    private String workHoursInWeekend;

    @Column(
            name = "coordinates",
            nullable = false
    )
    private String coordinates;

    @Lob
    @Column(
            name = "images"
    )
    private List<byte[]> images;
}
