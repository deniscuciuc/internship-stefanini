package org.dcuciuc.grade;

import java.util.Objects;

/**
 * Grade entity determine in which class the student is in and what subgroup he/she is in.
 */
public class Grade {
    private Long id;
    private int yearFrom;
    private int yearTo;
    private int number;
    private Character subgroup;


    public Grade() {

    }

    public Grade(Long id, int yearFrom, int yearTo, int number, Character subgroup) {
        this.id = id;
        this.yearFrom = yearFrom;
        this.yearTo = yearTo;
        this.number = number;
        this.subgroup = subgroup;
    }

    public Grade(int yearFrom, int yearTo, int number, Character subgroup) {
        this.yearFrom = yearFrom;
        this.yearTo = yearTo;
        this.number = number;
        this.subgroup = subgroup;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getYearFrom() {
        return yearFrom;
    }

    public void setYearFrom(int yearFrom) {
        this.yearFrom = yearFrom;
    }

    public int getYearTo() {
        return yearTo;
    }

    public void setYearTo(int yearTo) {
        this.yearTo = yearTo;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Character getSubgroup() {
        return subgroup;
    }

    public void setSubgroup(char subgroup) {
        this.subgroup = subgroup;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", yearFrom=" + yearFrom +
                ", yearTo=" + yearTo +
                ", number=" + number +
                ", subgroup=" + subgroup +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Grade grade = (Grade) obj;

        boolean idMatches = Objects.equals(id, grade.id);
        boolean yearsMatches = yearFrom == grade.yearFrom && yearTo == grade.yearTo;
        boolean numberAndSubgroupMatches = number == grade.number && subgroup == grade.subgroup;

        return idMatches && yearsMatches && numberAndSubgroupMatches;
    }
}
