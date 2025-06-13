package in.tech_camp.protospace.entity;

public class ProtoEntity {

    private Integer id;
    private String name;
    private String catchcopy;  // ← 全て小文字に修正
    private String concept;
    private String image;
    private Integer userId;

    private UserEntity user;

    public ProtoEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // catchcopy のgetter/setter
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
