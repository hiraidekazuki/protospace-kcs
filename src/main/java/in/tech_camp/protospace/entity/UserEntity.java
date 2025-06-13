package in.tech_camp.protospace.entity;

public class UserEntity {
    private Integer id;
    private String name;
    private String email;
    private String password;

    // 他のカラム（profile, groupName, post）は必要になったら追加

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
