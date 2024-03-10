package masha.courses.models;

import java.util.List;

public class Direction {
    private Integer direction_id;
    private String name;
    private String description;
    private List<Subject> subjects;
    private String icon;
    public Direction(Integer directionId, String name, String description) {
        direction_id = directionId;
        this.name = name;
        this.description = description;
    }
    public Direction(){}

    public List<Subject> getSubjects() {
        return subjects;
    }
    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getIconPath(){
        if(icon == null) return null;
        return "direction-icons/" + name + "/" + icon;
    }
    @Override
    public String toString() {
        return "Direction{" +
                "direction_id=" + direction_id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", subjects=" + subjects +
                '}';
    }
}
