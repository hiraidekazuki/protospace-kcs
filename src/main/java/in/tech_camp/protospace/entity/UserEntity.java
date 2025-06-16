
package in.tech_camp.protospace.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserEntity {
  private Integer id;
  private String email;
  private String password;
  private String name;
  private String profile;
  private String groupName;
  private String post;

   public UserEntity(Integer id, String email, String password, String name, String profile, String groupName, String post) {
       this.id = id;
       this.email = email;
       this.password = password;
       this.name = name;
       this.profile = profile;
       this.groupName = groupName; 
       this.post = post;
    }

    public String getPassword() {
    return password;
}

    public void setPassword(String password) {
    this.password = password;
}
    public void setProfile(String profile) {
        this.profile = profile;

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

    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPost() {
        return post;
    }
    public void setPost(String post) {
        this.post = post;
    }
}

