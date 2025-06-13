package in.tech_camp.protospace.entity;


// 仮置き

import java.util.List;

public class UserEntity {
    private Integer id;
    private String name;
    private List<ProtoEntity> protos;
    private List<CommentEntity> comments;

public class UserEntity {
    private Integer id;
    private String name;
    private String email;
    private String password;


    // 他のカラム（profile, groupName, post）は必要になったら追加

    // getter/setter
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


    public List<ProtoEntity> getProtos() {
        return protos;
    }
    public void setProtos(List<ProtoEntity> protos) {
        this.protos = protos;
    }

    public List<CommentEntity> getComments() {
        return comments;
    }
    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;

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
