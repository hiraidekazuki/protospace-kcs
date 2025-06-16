package in.tech_camp.protospace.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import in.tech_camp.protospace.entity.UserEntity;

@Mapper
public interface UserRepository {

    @Select("SELECT * FROM users WHERE id = #{id}")
    UserEntity findById(Integer id);

    @Select("SELECT * FROM users WHERE email = #{email}")
    UserEntity findByEmail(String email);

}
