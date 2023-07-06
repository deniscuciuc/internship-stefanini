package org.dcuciuc.mark;

import org.dcuciuc.dto.filtres.MarkFilterDTO;

import java.util.List;

public interface MarkService {

    Mark assignMark(Mark mark, Long studentId, Long course_id, Long teacher_id);

    Mark updateMark(Mark mark);

    List<Mark> findMarksByStudentId(MarkFilterDTO markFilterDTO, Long userId);
}
