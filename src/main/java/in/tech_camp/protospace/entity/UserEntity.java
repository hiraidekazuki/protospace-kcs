package in.tech_camp.protospace.entity;
// 仮置き
public class UserEntity {
    private Integer id;
    private String name;

    public UserEntity() {}

    public UserEntity(Integer id) {
        this.id = id;
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
}
