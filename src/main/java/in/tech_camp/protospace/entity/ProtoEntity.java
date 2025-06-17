package in.tech_camp.protospace.entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.Date;

@Entity
@Table(name = "protos")
public class ProtoEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "catchcopy")
    private String catchCopy;

    private Long id;
    private String name;
    private String catchcopy;

    private String concept;

    private String image;


    @Column(name = "user_id")
    private Integer userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "proto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> comments;

    // 追加したcreatedDateフィールド
    @Column(name = "created_date")
    private Date createdDate;

    // getter/setter手動追加例

    private Long userId;
    private UserEntity user;

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

    public String getCatchcopy() {
        return catchcopy;

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

    // 追加したgetter/setter
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    private String user_name;

public String getUser_name() {
    return user_name;
}

public void setUser_name(String user_name) {
    this.user_name = user_name;

}

}