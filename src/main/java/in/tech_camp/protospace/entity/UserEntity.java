
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
}

  

// 仮置き
//public class UserEntity {
    //private Integer id;
    //private String name;

    //public UserEntity() {}

    //public UserEntity(Integer id) {
      //  this.id = id;
    //}

//     public Integer getId() {
//         return id;
//     }

//     public void setId(Integer id) {
//         this.id = id;
//     }

//     public String getName() {
//         return name;
//     }

//     public void setName(String name) {
//         this.name = name;
//     }
// }

