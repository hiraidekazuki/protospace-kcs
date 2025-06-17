package in.tech_camp.protospace.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")  // テーブル名に合わせてください
public class UserEntity {

    @Id
    private Long id;

    private String email;

    private String password;

    private String name;

    private String profile;

    @Column(name = "group_name")  // ここでDBのカラム名を指定
    private String groupName;

    private String post;

    private List<CommentEntity> comments;

    // getter/setterはそのまま

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }
    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPost() {
        return post;
    }
    public void setPost(String post) {
        this.post = post;
    }
}