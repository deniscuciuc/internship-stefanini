package org.dcuciuc;

import org.dcuciuc.course.Course;
import org.dcuciuc.grade.Grade;
import org.dcuciuc.mark.Mark;
import org.dcuciuc.mark.enums.MarkType;
import org.dcuciuc.profile.Profile;
import org.dcuciuc.profile.enums.Gender;
import org.dcuciuc.user.User;
import org.dcuciuc.user.enums.UserRole;

import java.time.LocalDate;

public class TestData {


    public static Profile getTestProfile() {
        return new Profile(
                "John", "Moral", Gender.MALE, "058172255", "Address"
        );
    }

    public static User getTestUserWithStudentRole(Profile profile) {
        return new User(
                "john.morax@gmail.com", "password", UserRole.STUDENT, profile, true);
    }

    public static Course getTestCourse() {
        return new Course("English");
    }

    public static Grade getTestGrade() {
        return new Grade(
                2018,
                2019,
                9,
                'a'
        );
    }

    public static Mark getTestMark() {
        return new Mark(
                MarkType.TEN,
                LocalDate.now()
        );
    }
}
