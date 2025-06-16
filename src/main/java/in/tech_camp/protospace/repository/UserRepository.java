package in.tech_camp.protospace.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import in.tech_camp.protospace.entity.UserEntity;

@Mapper
public interface UserRepository {

    @Select("SELECT * FROM users WHERE email = #{email}")
    @Results(id = "UserResultMap", value = {
        @Result(property = "id", column = "id"),
        @Result(property = "email", column = "email"),
        @Result(property = "password", column = "password"),
        @Result(property = "name", column = "name"),
        @Result(property = "profile", column = "profile"),
        @Result(property = "groupName", column = "group_name"),
        @Result(property = "post", column = "post")
    })
    UserEntity findByEmail(String email);

    @Select("SELECT * FROM users WHERE id = #{id}")
    @ResultMap("UserResultMap")
    UserEntity findById(Long id);
}
