package in.tech_camp.protospace.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "comments")
@Data
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String content;     // コメント内容

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;   // 作成日時

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;  // コメントしたユーザー

    @ManyToOne
    @JoinColumn(name = "proto_id", nullable = false)
    private ProtoEntity proto;  // コメント対象のプロトタイプ

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
