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
}


  
