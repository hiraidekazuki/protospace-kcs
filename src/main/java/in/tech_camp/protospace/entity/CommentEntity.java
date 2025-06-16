package in.tech_camp.protospace.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String content; // コメント内容

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // 作成日時

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user; // コメントしたユーザー

    @ManyToOne
    @JoinColumn(name = "proto_id", nullable = false)
    private ProtoEntity proto; // コメント対象のプロトタイプ

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    // ===== Getter / Setter =====

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public ProtoEntity getProto() {
        return proto;
    }

    public void setProto(ProtoEntity proto) {
        this.proto = proto;
    }
}
