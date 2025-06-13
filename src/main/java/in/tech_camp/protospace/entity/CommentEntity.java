package in.tech_camp.protospace.entity;

import lombok.Data;

@Data
public class CommentEntity {
    private Integer id;
    private String content;     // コメント内容
    private String createdAt;   // 作成日時
    private Integer userId;     // コメントしたユーザーID
    private Integer protoId;    // コメント対象のプロトタイプID（もしあれば）
    private UserEntity user;
    private ProtoEntity proto;
}
