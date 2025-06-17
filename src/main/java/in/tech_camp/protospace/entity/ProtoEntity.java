package in.tech_camp.protospace.entity;

import java.util.List;

public class ProtoEntity {
    private Long id;
    private String name;
    private String catchcopy;
    private String concept;
    private String image;
    private Long userId;
    private UserEntity user;
    private List<CommentEntity> comments;

    // --- Getter & Setter ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatchcopy() {
        return catchcopy;
    }

    public void setCatchcopy(String catchcopy) {
        this.catchcopy = catchcopy;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    private String user_name;

public String getUser_name() {
    return user_name;
}

public void setUser_name(String user_name) {
    this.user_name = user_name;
}

}