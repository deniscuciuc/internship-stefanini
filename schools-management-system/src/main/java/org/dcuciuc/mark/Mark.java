package org.dcuciuc.mark;

import org.dcuciuc.mark.enums.MarkType;

import java.time.LocalDate;
import java.util.Objects;


public class Mark {
    private Long id;
    private MarkType mark;
    private LocalDate createdAt;

    public Mark() {

    }

    public Mark(Long id, MarkType mark, LocalDate createdAt) {
        this.id = id;
        this.mark = mark;
        this.createdAt = createdAt;
    }

    public Mark(MarkType mark, LocalDate createdAt) {
        this.mark = mark;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MarkType getMark() {
        return mark;
    }

    public void setMark(MarkType mark) {
        this.mark = mark;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Mark{" +
                "id=" + id +
                ", mark=" + mark +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Mark markObj = (Mark) obj;

        return Objects.equals(id, markObj.id)
                && mark.equals(markObj.mark);
    }
}
