package in.tech_camp.protospace.entity;

// 仮置き

import java.util.List;

public class UserEntity {
    private Integer id;
    private String name;
    private List<ProtoEntity> protos;
    private List<CommentEntity> comments;

    public UserEntity() {}

    public UserEntity(Integer id) {
        this.id = id;
    }

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
    }
}
