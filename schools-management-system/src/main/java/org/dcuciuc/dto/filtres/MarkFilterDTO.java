package org.dcuciuc.dto.filtres;

import java.sql.Date;

public class MarkFilterDTO {
    private int currentPage;
    private int pageSize;
    private Long studentId;
    private Long courseId;

    private Long teacherId;
    private Date dateFrom;
    private Date dateTo;

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public Long getStudentId() {
        return studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public int getOffset() {
        int offset = 0;
        if (this.pageSize > 1) {
            offset = this.currentPage * this.pageSize - this.pageSize;
        }
        return offset;
    }

    private MarkFilterDTO(Builder builder) {
        this.currentPage = builder.currentPage;
        this.pageSize = builder.pageSize;
        this.studentId = builder.studentId;
        this.courseId = builder.courseId;
        this.teacherId = builder.teacherId;
        this.dateFrom = builder.dateFrom;
        this.dateTo = builder.dateTo;
    }

    public static class Builder {
        private Long studentId;
        private Long courseId;

        private int currentPage;
        private int pageSize;
        private Long teacherId;
        private Date dateFrom;
        private Date dateTo;

        public Builder(Long studentId, Long courseId) {
            this.studentId = studentId;
            this.courseId = courseId;
        }

        public Builder setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
            return this;
        }

        public Builder setPageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder setTeacherId(Long teacherId) {
            this.teacherId = teacherId;
            return this;
        }

        public Builder setDateFrom(Date dateFrom) {
            this.dateFrom = dateFrom;
            return this;
        }

        public Builder setDateTo(Date dateTo) {
            this.dateTo = dateTo;
            return this;
        }

        public MarkFilterDTO build() {
            return new MarkFilterDTO(this);
        }
    }
}
