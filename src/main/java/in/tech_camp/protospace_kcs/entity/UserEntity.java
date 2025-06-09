package in.tech_camp.protospace_kcs.entity;

public class UserEntity {
  private Integer id;
  private String email;
  private String password;
  private String name;
  private String profile;
  private String group_name;
  private String post;

   public UserEntity(Integer id, String email, String password, String name, String profile, String group_name, String post) {
       this.id = id;
       this.email = email;
       this.password = password;
       this.name = name;
       this.profile = profile;
       this.group_name = group_name; 
       this.post = post;
    }

    // getter / setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}


  
