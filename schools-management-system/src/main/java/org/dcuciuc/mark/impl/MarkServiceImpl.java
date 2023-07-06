package org.dcuciuc.mark.impl;

import org.dcuciuc.dto.filtres.MarkFilterDTO;
import org.dcuciuc.dao.exceptions.EntityNotFoundException;
import org.dcuciuc.mark.Mark;
import org.dcuciuc.mark.MarkDAO;
import org.dcuciuc.mark.MarkService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarkServiceImpl implements MarkService {

    private final MarkDAO<Mark> markDAO;

    public MarkServiceImpl(MarkDAO<Mark> markDAO) {
        this.markDAO = markDAO;
    }


    @Override
    public Mark assignMark(Mark mark, Long studentId, Long course_id, Long teacher_id) {
        // 1. Save mark in database
        Mark savedMark = markDAO.createMark(mark, studentId, course_id, teacher_id);

        // 2. return mark with id
        return savedMark;
    }

    @Override
    public Mark updateMark(Mark mark) {
        if (mark.getId() == null) {
            throw new EntityNotFoundException("Mark have no id");
        }

        // todo: move throwing entity not found in dao layer
        Mark foundMark = markDAO.getById(mark.getId());

        return markDAO.update(mark, mark.getId());
    }

    @Override
    public List<Mark> findMarksByStudentId(MarkFilterDTO markFilterDTO, Long userId) {
        // 1. find marks by filter
        List<Mark> foundMarks = markDAO.findMarksByStudentAndCourseIdFiltered(markFilterDTO);

        // 2. if list is empty throw entity not found exception
        if (foundMarks.isEmpty()) {
            throw new EntityNotFoundException("Marks for such user not found");
        }

        // 3. return
        return foundMarks;
    }
}
