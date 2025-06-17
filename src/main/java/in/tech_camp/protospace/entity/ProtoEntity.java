package in.tech_camp.protospace.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * プロトタイプ（投稿）を表すエンティティ。
 */
public class ProtoEntity {
    private Long id;
    private String name;

    // Javaの命名規則に合わせてcamelCaseに修正
    private String catchCopy;

    private String concept;
    private String image;

    // 外部キー（DB保存用）
    private Long userId;

    // 関連ユーザー（JOIN取得用）
    private UserEntity user;

    // 関連コメント（JOIN取得用）※仮実装。初期値は空リストでnull安全に
    private List<CommentEntity> comments = new ArrayList<>();

    // --- Getter & Setter ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatchCopy() {
        return catchCopy;
    }

    public void setCatchCopy(String catchCopy) {
        this.catchCopy = catchCopy;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
  
    public List<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "ProtoEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", catchCopy='" + catchCopy + '\'' +
                ", concept='" + concept + '\'' +
                ", image='" + image + '\'' +
                ", userId=" + userId +
                ", user=" + (user != null ? user.getName() : "null") +
                ", comments=" + (comments != null ? comments.size() + "件" : "null") +
                '}';
    }
}