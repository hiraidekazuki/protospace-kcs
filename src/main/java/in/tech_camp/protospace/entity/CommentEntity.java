package in.tech_camp.protospace.entity;

// 仮置き

import lombok.Data;

@Data
public class CommentEntity {
    private Integer id;
    private String content;     // コメント内容
    private String createdAt;   // 作成日時（文字列でも良いしDate型でもOK）
    private Integer userId;     // コメントしたユーザーID
    private Integer protoId;    // コメント対象のプロトタイプID（もしあれば）
}
