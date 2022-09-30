package api.models;

import lombok.Data;

@Data
public class UserTimeResponse {
    private String name;
    private String job;
    private String updatedAt;

    public UserTimeResponse(String name, String job, String updatedAt) {
        this.name = name;
        this.job = job;
        this.updatedAt = updatedAt;
    }
}
