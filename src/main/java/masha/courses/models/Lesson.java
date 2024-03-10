package masha.courses.models;

public class Lesson {
    private Integer lesson_id;
    private Integer course_id;
    private String name;
    private String content;

    public Lesson(Integer lessonId, Integer courseId, String name, String content) {
        lesson_id = lessonId;
        course_id = courseId;
        this.name = name;
        this.content = content;
    }
    public Lesson(){}

    public Integer getLesson_id() {
        return lesson_id;
    }

    public void setLesson_id(Integer lesson_id) {
        this.lesson_id = lesson_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public Integer getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Integer course_id) {
        this.course_id = course_id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "lesson_id=" + lesson_id +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
