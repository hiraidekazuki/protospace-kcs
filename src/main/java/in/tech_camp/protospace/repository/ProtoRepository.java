package in.tech_camp.protospace.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import in.tech_camp.protospace.entity.ProtoEntity;

@Mapper
public interface ProtoRepository {

  @Insert("INSERT INTO protos (name, catchcopy, concept, image, user_id) VALUES (#{name}, #{catchCopy}, #{concept}, #{image}, #{userId})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void save(ProtoEntity proto);

  @Select("SELECT * FROM protos")
  List<ProtoEntity> findAll();

  @Select("SELECT * FROM protos WHERE id = #{id}")
  @Results(value = {
    @Result(property = "id", column = "id"),
    @Result(property = "user", column = "user_id",
          one = @One(select = "in.tech_camp.protospace.repository.UserRepository.findById")),
    @Result(property = "comments", column = "id",
          many = @Many(select = "in.tech_camp.protospace.repository.CommentRepository.findByProtoId"))
})
ProtoEntity findById(Integer id);


}
