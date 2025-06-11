package in.tech_camp.protospace.entity;

public class ProtoEntity {

    private Integer id;
    private String name;
    private String catchcopy;
    private String concept;
    private String image;
    private String user_name;

    // created_at は DBのDEFAULTで自動生成されるなら省略OK
    // もしJavaでも扱いたければ下記を追加：
    // private LocalDateTime created_at;

    // --- Getters & Setters ---

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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    // created_atを使いたければ以下も：
    // public LocalDateTime getCreated_at() {
    //     return created_at;
    // }
    //
    // public void setCreated_at(LocalDateTime created_at) {
    //     this.created_at = created_at;
    // }
}
