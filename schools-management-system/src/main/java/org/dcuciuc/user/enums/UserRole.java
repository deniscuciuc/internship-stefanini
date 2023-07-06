package org.dcuciuc.user.enums;

public enum UserRole {

    /**
     * The student has access only to view their data (grades, absences, etc.).
     * He can also see his data for previous years
     */
    STUDENT,

    /**
     * The parent has access only to read their children's data.
     * Their grades, absences, and other data for all years of schooling
     */
    PARENT,

    /**
     * The teacher has access to view information about parents and students as well as about the classes she teaches.
     * Teacher can give the students marks and absences in the subjects she teaches
     */
    TEACHER,

    /**
     * Head teacher is able to manage (create, edit, delete):
     * <ol>
     *     <li>students (except of marks and absences)</li>
     *     <li>subjects taught by teachers/head teachers</li>
     *     <li>parents (except of deleting)</li>
     *     <li>grades</li>
     *     <li>subjects</li>
     * </ol>
     */
    HEAD_TEACHER,

    /**
     * Director have access to:
     * <ol>
     *     <li>add new employees</li>
     *     <li>edit and delete employees (adding new roles only for: PARENT, TEACHER and HEAD_TEACHER)</li>
     * </ol>
     */
    DIRECTOR
}
