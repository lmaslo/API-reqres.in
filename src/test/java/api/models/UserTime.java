package api.models;

import lombok.Data;

@Data
public class UserTime {
    private String name;
    private String job;

    public UserTime(String name, String job) {
        this.name = name;
        this.job = job;
    }
}
