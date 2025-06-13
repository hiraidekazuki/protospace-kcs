package in.tech_camp.protospace.entity;

import java.util.List;

import lombok.Data;

@Data
public class ProtoEntity {

    private Integer id;
    private String name;
    private String catchCopy;
    private String concept;
    private String image;
    private Integer userId;
    private UserEntity user;
    private List<CommentEntity> comments;

}
