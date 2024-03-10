package masha.courses.models;

import java.util.List;

public class Course {
    private Integer course_id;
    private Integer subject_id;
    private String name;
    private String description;
    private List<Lesson> lessons;
    public Course(Integer course_id, Integer subjectId, String name, String description) {
        this.course_id = course_id;
        subject_id = subjectId;
        this.name = name;
        this.description = description;
    }
    public Course(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Integer course_id) {
        this.course_id = course_id;
    }

    public Integer getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(Integer subject_id) {
        this.subject_id = subject_id;
    }

    @Override
    public String toString() {
        return "Course{" +
                "course_id=" + course_id +
                ", subject_id=" + subject_id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
}
