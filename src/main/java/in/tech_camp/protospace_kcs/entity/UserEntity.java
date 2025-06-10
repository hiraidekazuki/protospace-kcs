package in.tech_camp.protospace_kcs.entity;

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
}


  
