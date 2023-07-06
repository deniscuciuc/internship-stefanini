package org.dcuciuc.report.impl;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dcuciuc.course.Course;
import org.dcuciuc.report.impl.wrappers.ExcelReportWrapper;
import org.dcuciuc.report.impl.wrappers.StudentReportWrapper;
import org.dcuciuc.grade.Grade;
import org.dcuciuc.mark.Mark;
import org.dcuciuc.mark.enums.MarkType;
import org.dcuciuc.report.ExcelService;
import org.dcuciuc.report.ReportService;
import org.dcuciuc.user.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private final ExcelService excelService;

    public ReportServiceImpl(ExcelService excelService) {
        this.excelService = excelService;
    }


    @Override
    public void generateExcelReport(ExcelReportWrapper excelReportWrapper) {
        Course course = excelReportWrapper.getCourse();
        Grade grade = excelReportWrapper.getGrade();
        User teacher = excelReportWrapper.getTeacher();
        List<StudentReportWrapper> students = excelReportWrapper.getStudents();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(excelReportWrapper.getCourse().getName());

        sheet.setDefaultColumnWidth(20);
        sheet.setDefaultRowHeight((short) 460);

        int numberOfDays = 31;
        int rows = students.size() + 1;
        int columns = numberOfDays + 3;

        Object[][] data = new Object[rows][columns];

        writeHeader(data, columns, excelReportWrapper.getFrom());
        writeStudentsWithMarks(data, students, numberOfDays);

        String fileName = buildFileName(
                teacher.getProfile().getFullName(),
                grade.getNumber(),
                grade.getSubgroup(),
                excelReportWrapper.getFrom().getMonthValue(),
                excelReportWrapper.getFrom().getYear()
        );

        excelService.setFolderName(course.getName());
        excelService.setFileName(fileName);
        excelService.writeSheetData(data, sheet);
        String filePath = excelService.saveWorkbookLocal(workbook);
        excelService.saveWorkbookServer(filePath);
    }

    private void writeHeader(Object[][] data, int columns, LocalDate from) {
        data[0][0] = "Students";
        data[0][1] = from;
        for (int i = 2, days = 1; i < columns - 2; i++, days++) {
            data[0][i] = from.plusDays(days);
        }
        data[0][columns - 2] = "Total absents";
        data[0][columns - 1] = "Average mark";
    }

    private void writeStudentsWithMarks(Object[][] data, List<StudentReportWrapper> students, int numberOfDays) {
        int rowCount = 1;
        for (StudentReportWrapper student : students) {
            List<Mark> studentMarks = student.getMarks()
                    .stream()
                    .sorted(Comparator.comparing(Mark::getCreatedAt))
                    .collect(Collectors.toList());

            data[rowCount][0] = student.getUser().getProfile().getFullName();
            for (int i = 1, m = 0; i <= numberOfDays; i++) {
                if (m < studentMarks.size()) {
                    Mark mark = studentMarks.get(m);

                    LocalDate localDate = (LocalDate) data[0][i];
                    if (mark.getCreatedAt().getDayOfMonth() == localDate.getDayOfMonth()) {
                        Integer markValue = mark.getMark().getIntegerValueOfMark();

                        if (markValue.equals(0)) {
                            data[rowCount][i] = "a";
                        } else {
                            data[rowCount][i] = markValue;
                        }

                        m++;
                    } else {
                        data[rowCount][i] = "";
                    }

                }
            }
            data[rowCount][numberOfDays + 1] = countAbsents(studentMarks);
            data[rowCount][numberOfDays + 2] = getAverageMark(studentMarks);

            rowCount++;
        }
    }

    private String buildFileName(String teacherName, int gradeNumber, char gradeSubgroup, int month, int year) {
        return teacherName + " " + gradeNumber + gradeSubgroup + " " + month + "-" + year;
    }

    private Long countAbsents(List<Mark> marks) {
        return marks.stream()
                .filter(mark -> mark.getMark().equals(MarkType.ABSENT))
                .count();
    }

    private Float getAverageMark(List<Mark> marks) {
        int sum = 0, count = 0;
        for (Mark mark : marks) {
            if (!mark.getMark().equals(MarkType.ABSENT)) {
                sum += mark.getMark().getIntegerValueOfMark();
                count++;
            }
        }
        return (float) sum / (float) count;
    }
}
