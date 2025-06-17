package in.tech_camp.protospace.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import in.tech_camp.protospace.entity.UserEntity;

@Mapper
public interface UserRepository {

    @Select("SELECT * FROM users WHERE id = #{id}")

    UserEntity findById(Integer id);

    @Results({
        @Result(property = "groupName", column = "group_name")
    })
    UserEntity findById(Long id);


    @Select("SELECT * FROM users WHERE email = #{email}")
    @Results({
        @Result(property = "groupName", column = "group_name")
    })
    UserEntity findByEmail(String email);


    @Select("SELECT COUNT(*) > 0 FROM users WHERE email = #{email}")
    boolean existsByEmail(String email);

    @Insert("""
        INSERT INTO users (email, password, name, profile, group_name, post)
        VALUES (#{email}, #{password}, #{name}, #{profile}, #{groupName}, #{post})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(UserEntity user);

    @Select("SELECT * FROM users")
    @Results({
        @Result(property = "groupName", column = "group_name")
    })
    List<UserEntity> findAll();

}
