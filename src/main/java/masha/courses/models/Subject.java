package masha.courses.models;

import java.util.List;

public class Subject {
    private Integer subject_id;
    private Integer direction_id;
    private String name;
    private String description;
    private List<Course> courses;
    public Subject(Integer subjectId, Integer directionId, String name, String description) {
        subject_id = subjectId;
        direction_id = directionId;
        this.name = name;
        this.description = description;
    }

    public Subject(){}

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(Integer subject_id) {
        this.subject_id = subject_id;
    }

    public int getDirection_id() {
        return direction_id;
    }

    public void setDirection_id(Integer direction_id) {
        this.direction_id = direction_id;
    }

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

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
