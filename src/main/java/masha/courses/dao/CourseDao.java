package masha.courses.dao;

import masha.courses.models.Course;
import masha.courses.models.Direction;
import masha.courses.models.Lesson;
import masha.courses.models.Subject;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CourseDao {
    private final JdbcTemplate jdbcTemplate;

    public CourseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Course> getAllCourses()
    {
        return jdbcTemplate.query("Select * from course", new BeanPropertyRowMapper<>(Course.class));
    }
    public List<Course> getCoursesByKeyWords(String keywords, int subject_id)
    {
        keywords = "%"+keywords+"%";
        return jdbcTemplate.query("Select * from course where subject_id=? and (lower(name) like ? or lower(description) like ?);",
                new Object[]{subject_id, keywords, keywords},
                new BeanPropertyRowMapper<>(Course.class));
    }
    public List<Course> getCoursesOfSubject(int subject_id)
    {
        return jdbcTemplate.query(
                "Select * from course where subject_id=?",
                new Object[]{subject_id},
                new BeanPropertyRowMapper<>(Course.class));
    }
    public List<Course> getCoursesOfStudent(int student_id)
    {
        return jdbcTemplate.query("Select course.* from student " +
                "left join student_to_course on student.student_id = student_to_course.student_id " +
                "left join course on student_to_course.course_id = course.course_id " +
                "where student.student_id=?;", new Object[]{student_id}, new BeanPropertyRowMapper<>(Course.class));
    }
    public Optional<Course> checkIfStudentHasCourses(int student_id)
    {
        return Optional.ofNullable(jdbcTemplate.query("Select course.* from student " +
                "left join student_to_course on student.student_id = student_to_course.student_id " +
                "left join course on student_to_course.course_id = course.course_id " +
                "where student.student_id=?;", new Object[]{student_id}, new BeanPropertyRowMapper<>(Course.class))
                .stream().findAny().orElse(null));
    }
    public Optional<Course> getCourseOfStudent(int student_id, int course_id)
    {
        return Optional.ofNullable(jdbcTemplate.query("Select course.* from student " +
                "left join student_to_course on student.student_id = student_to_course.student_id " +
                "left join course on student_to_course.course_id = course.course_id " +
                "where student.student_id=? and course.course_id=?;",
                        new Object[]{student_id, course_id},
                        new BeanPropertyRowMapper<>(Course.class))
                .stream().findAny().orElse(null));
    }
    public void deleteCourseFromStudent(int student_id, int course_id)
    {
        jdbcTemplate.update("delete from student_to_course where student_id=? and course_id=?",student_id, course_id);
    }
    public Optional<Course> getCourseById(int course_id)
    {
        return Optional.ofNullable(jdbcTemplate.query(
                "Select * from course where course_id=?",
                        new Object[]{course_id},
                        new BeanPropertyRowMapper<>(Course.class))
                .stream().findAny().orElse(null));
    }
    public void createCourse(Course newCourse)
    {
        jdbcTemplate.update("Insert into course(subject_id, name, description) values (?,?,?)",
                newCourse.getSubject_id(), newCourse.getName(), newCourse.getDescription());
    }
    public List<Lesson> getLessonOfCourse(int course_id)
    {
        return jdbcTemplate.query("Select * From lesson where course_id=? order by lesson_id",
                new Object[]{course_id}, new BeanPropertyRowMapper<>(Lesson.class));
    }
    public Optional<Lesson> getLessonByLessonId(int lesson_id)
    {
        return Optional.ofNullable(jdbcTemplate.query("Select * from lesson where lesson.lesson_id=?",
                        new Object[]{lesson_id},
                        new BeanPropertyRowMapper<>(Lesson.class))
                .stream().findAny().orElse(null));
    }
    public List<Subject> getAllSubjects()
    {
        return jdbcTemplate.query("Select * from subject", new BeanPropertyRowMapper<>(Subject.class));
    }
    public List<Subject> getSubjectByDirectionId(int direction_id)
    {
        return jdbcTemplate.query("Select * from subject where direction_id=?", new Object[]{direction_id}, new BeanPropertyRowMapper<>(Subject.class));
    }
    public Optional<Subject> getSubjectByID(int subject_id)
    {
        return Optional.ofNullable(jdbcTemplate.query("Select * from subject where subject_id=?", new Object[]{subject_id}, new BeanPropertyRowMapper<>(Subject.class))
                .stream().findAny().orElse(null));
    }
    public List<Direction> getAllDirections()
    {
        return jdbcTemplate.query("Select * from direction", new BeanPropertyRowMapper<>(Direction.class));
    }
    public Optional<Direction> getDirectionById(int direction_id)
    {
        return Optional.ofNullable(jdbcTemplate.query("Select * from direction where direction_id=?", new Object[]{direction_id}, new BeanPropertyRowMapper<>(Direction.class))
                .stream().findAny().orElse(null));
    }
    public void deleteDirectionById(int direction_id)
    {
        jdbcTemplate.update("Delete from direction where direction.direction_id=?;", direction_id);
    }
    public void createDirection(Direction newDirection)
    {
        jdbcTemplate.update("Insert into direction (name, description, icon) values (?,?,?);",
                newDirection.getName(), newDirection.getDescription(), newDirection.getIcon());
    }
    public void createSubject(Subject newSubject)
    {
        jdbcTemplate.update("Insert into subject(direction_id, name, description) values (?,?,?);",
                newSubject.getDirection_id(), newSubject.getName(), newSubject.getDescription());
    }
    public void deleteSubject(int subject_id)
    {
        jdbcTemplate.update("Delete from subject where subject.subject_id=?;", subject_id);
    }

    public List<Course> getCoursesOfTeacher(int teacher_id)
    {
        return jdbcTemplate.query("Select course.* from teacher " +
                "left join teacher_to_course on teacher.teacher_id = teacher_to_course.teacher_id " +
                "left join course on teacher_to_course.course_id = course.course_id " +
                "where teacher.teacher_id=?;", new Object[]{teacher_id}, new BeanPropertyRowMapper<>(Course.class));
    }
    public Optional<Course> getCourseOfTeacher(int teacher_id, int course_id)
    {
        return Optional.ofNullable(jdbcTemplate.query("Select course.* from teacher " +
                "left join teacher_to_course on teacher_to_course.teacher_id=teacher.teacher_id " +
                "left join course on course.course_id = teacher_to_course.course_id " +
                "where teacher.teacher_id=? and course.course_id=?", new Object[]{teacher_id, course_id}, new BeanPropertyRowMapper<>(Course.class))
                .stream().findAny().orElse(null));
    }

    public List<Course> getTopCourses()
    {
        return jdbcTemplate.query("WITH top as (Select course_id, count(subject_id) as count from course group by course_id order by count fetch first 3 rows only) " +
                " SELECT * from course where course_id in (Select course_id from top);", new Object[]{}, new BeanPropertyRowMapper<>(Course.class));
    }

    public void addCourseToTeacher(int teacher_id, int course_id)
    {
        jdbcTemplate.update("Insert into teacher_to_course(teacher_id, course_id) values (?,?);", teacher_id, course_id);
    }
    public Optional<Course> getCourseBySubjectIdAndCourseName(int subject_id, String courseName)
    {
        return Optional.ofNullable(jdbcTemplate.query("select * from course where subject_id=? and name=?",
                new Object[]{subject_id,courseName}, new BeanPropertyRowMapper<>(Course.class)).stream().findAny().orElse(null));
    }
    public void updateLesson(Lesson newLesson)
    {
        jdbcTemplate.update("Update lesson set name=?, content=? where lesson_id=?",
                newLesson.getName(), newLesson.getContent(), newLesson.getLesson_id());
    }
    public void createLesson(Lesson newLesson)
    {
        jdbcTemplate.update("Insert into lesson(name, content, course_id) values (?,?,?)", newLesson.getName(), newLesson.getContent(), newLesson.getCourse_id());
    }
}




















