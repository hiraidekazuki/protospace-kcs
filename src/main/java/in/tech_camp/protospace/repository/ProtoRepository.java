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

  @Select("SELECT p.id, p.name, p.catchcopy, p.concept, p.image, p.user_id, " +
          "u.id AS user_id_alias, u.name AS user_name_alias " +
          "FROM protos p " +
          "JOIN users u ON p.user_id = u.id " +
          "ORDER BY p.id DESC")
  @Results({
      @Result(property = "id", column = "id"),
      @Result(property = "name", column = "name"),
      @Result(property = "catchcopy", column = "catchcopy"),
      @Result(property = "concept", column = "concept"),
      @Result(property = "image", column = "image"),
      @Result(property = "userId", column = "user_id"),
      // ネストされた user オブジェクトへのマッピング（AS句で明示）
      @Result(property = "user.id", column = "user_id_alias"),
      @Result(property = "user.name", column = "user_name_alias")
  })
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



  @Insert("INSERT INTO protos (name, catchcopy, concept, image, user_id) " +
          "VALUES (#{name}, #{catchcopy}, #{concept}, #{image}, #{userId})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void save(ProtoEntity proto);

}
