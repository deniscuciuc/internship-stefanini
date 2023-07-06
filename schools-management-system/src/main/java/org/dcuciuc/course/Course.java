package org.dcuciuc.course;

import java.util.Objects;

public class Course {
    private Long id;
    private String name;


    public Course() {

    }

    public Course(String name) {
        this.name = name;
    }

    public Course(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Course course = (Course) obj;

        boolean idMatches = Objects.equals(id, course.id);
        boolean namesMatches = name.equals(course.name);
        return idMatches && namesMatches;
    }
}
