package org.dcuciuc.user;

import org.dcuciuc.dto.PageResponse;
import org.dcuciuc.dto.ParentDTO;
import org.dcuciuc.dto.StudentDTO;
import org.dcuciuc.dto.TeacherDTO;
import org.dcuciuc.dto.auth.RegistrationDTO;
import org.dcuciuc.user.enums.UserRole;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public User registerUser(@RequestBody RegistrationDTO registrationDTO) {
        return userService.registerUser(registrationDTO);
    }

    @GetMapping
    public User getUserById(@RequestParam Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/by-email")
    public User getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/by-role")
    public PageResponse<User> getUsersByRole(
            @RequestParam UserRole role,
            @RequestParam Integer page,
            @RequestParam Integer pageSize
    ) {
        return userService.getUsersByRole(role, page, pageSize);
    }

    @GetMapping("/students/by-course")
    public PageResponse<StudentDTO> getStudentsByCourseId(
            @RequestParam Long courseId,
            @RequestParam Integer page,
            @RequestParam Integer pageSize
    ) {
        return userService.getStudentsByCourseId(courseId, page, pageSize);
    }

    @GetMapping("/students/by-course-grade")
    public List<StudentDTO> getStudentsByCourseIdAndGradeId(
            @RequestParam Long courseId,
            @RequestParam Long gradeId
    ) {
        return userService.getAllStudentsByCourseIdAndGradeId(courseId, gradeId);
    }

    @GetMapping("/teachers")
    public TeacherDTO getTeacherById(@RequestParam Long id) {
        return userService.getTeacherById(id);
    }

    @GetMapping("/parents")
    public ParentDTO getParentById(@RequestParam Long id) {
        return userService.getParentById(id);
    }

    @PutMapping("/parents/assign-student")
    public ParentDTO assignParentToStudent(
            @RequestParam Long parentId,
            @RequestParam Long studentId
    ) {
        return userService.assignParentToStudent(parentId, studentId);
    }
}
