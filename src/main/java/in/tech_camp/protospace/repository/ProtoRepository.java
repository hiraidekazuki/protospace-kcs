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

  // 全プロトタイプを取得
  @Select("SELECT p.id, p.name, p.catchcopy, p.concept, p.image, p.user_id, " +
          "u.id AS user_id_alias, u.name AS user_name_alias " +
          "FROM protos p " +
          "JOIN users u ON p.user_id = u.id " +
          "ORDER BY p.id DESC")
  @Results({
      @Result(property = "id", column = "id"),
      @Result(property = "name", column = "name"),
      @Result(property = "catchCopy", column = "catchcopy"),   // ← 修正
      @Result(property = "concept", column = "concept"),
      @Result(property = "image", column = "image"),
      @Result(property = "userId", column = "user_id"),
      @Result(property = "user.id", column = "user_id_alias"),
      @Result(property = "user.name", column = "user_name_alias")
  })
  List<ProtoEntity> findAll();

  @Select("SELECT * FROM protos WHERE id = #{id}")
  @Results(value = {
    @Result(property = "id", column = "id"),
    @Result(property = "catchCopy", column = "catchcopy"),  // ← 追加（忘れずに）
    @Result(property = "user", column = "user_id",
          one = @One(select = "in.tech_camp.protospace.repository.UserRepository.findById")),
    @Result(property = "comments", column = "id",
          many = @Many(select = "in.tech_camp.protospace.repository.CommentRepository.findByProtoId"))
  })
  ProtoEntity findById(Integer id);


  // プロトタイプ保存

  @Insert("INSERT INTO protos (name, catchcopy, concept, image, user_id) " +
          "VALUES (#{name}, #{catchCopy}, #{concept}, #{image}, #{userId})")   // ← 修正
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void save(ProtoEntity proto);


}

  // 指定ユーザーIDに紐づくプロトタイプ一覧を取得（追加）
  @Select("SELECT p.id, p.name, p.catchcopy, p.concept, p.image, p.user_id, " +
          "u.id AS user_id_alias, u.name AS user_name_alias " +
          "FROM protos p " +
          "JOIN users u ON p.user_id = u.id " +
          "WHERE p.user_id = #{userId} " +
          "ORDER BY p.id DESC")
  @Results({
      @Result(property = "id", column = "id"),
      @Result(property = "name", column = "name"),
      @Result(property = "catchcopy", column = "catchcopy"),
      @Result(property = "concept", column = "concept"),
      @Result(property = "image", column = "image"),
      @Result(property = "userId", column = "user_id"),
      @Result(property = "user.id", column = "user_id_alias"),
      @Result(property = "user.name", column = "user_name_alias")
  })
  List<ProtoEntity> findByUserId(Long userId);
}

