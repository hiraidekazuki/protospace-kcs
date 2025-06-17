package in.tech_camp.protospace.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
      @Result(property = "catchCopy", column = "catchcopy"),  // ここをcatchCopyに変更
      @Result(property = "concept", column = "concept"),
      @Result(property = "image", column = "image"),
      @Result(property = "userId", column = "user_id"),
      @Result(property = "user.id", column = "user_id_alias"),
      @Result(property = "user.name", column = "user_name_alias")
  })
  List<ProtoEntity> findAll();

  // プロトタイプ保存（戻り値をintに変更）
  @Insert("INSERT INTO protos (name, catchcopy, concept, image, user_id) " +
          "VALUES (#{name}, #{catchCopy}, #{concept}, #{image}, #{userId})")  // catchCopyに修正
  @Options(useGeneratedKeys = true, keyProperty = "id")
  int save(ProtoEntity proto);

  // 指定ユーザーIDに紐づくプロトタイプ一覧を取得
  @Select("SELECT p.id, p.name, p.catchcopy, p.concept, p.image, p.user_id, " +
          "u.id AS user_id_alias, u.name AS user_name_alias " +
          "FROM protos p " +
          "JOIN users u ON p.user_id = u.id " +
          "WHERE p.user_id = #{userId} " +
          "ORDER BY p.id DESC")
  @Results({
      @Result(property = "id", column = "id"),
      @Result(property = "name", column = "name"),
      @Result(property = "catchCopy", column = "catchcopy"),
      @Result(property = "concept", column = "concept"),
      @Result(property = "image", column = "image"),
      @Result(property = "userId", column = "user_id"),
      @Result(property = "user.id", column = "user_id_alias"),
      @Result(property = "user.name", column = "user_name_alias")
  })
  List<ProtoEntity> findByUserId(Long userId);

  // IDで1件取得（詳細ページ用）
  @Select("SELECT p.id, p.name, p.catchcopy, p.concept, p.image, p.user_id, " +
          "u.id AS user_id_alias, u.name AS user_name_alias " +
          "FROM protos p " +
          "JOIN users u ON p.user_id = u.id " +
          "WHERE p.id = #{id}")
  @Results({
      @Result(property = "id", column = "id"),
      @Result(property = "name", column = "name"),
      @Result(property = "catchCopy", column = "catchcopy"),
      @Result(property = "concept", column = "concept"),
      @Result(property = "image", column = "image"),
      @Result(property = "userId", column = "user_id"),
      @Result(property = "user.id", column = "user_id_alias"),
      @Result(property = "user.name", column = "user_name_alias")
  })
  ProtoEntity findById(Long id);

  // プロトタイプ削除
  @Delete("DELETE FROM protos WHERE id = #{id}")
  int deleteById(Long id);

  // プロトタイプ更新
  @Update("UPDATE protos SET name = #{name}, catchcopy = #{catchCopy}, concept = #{concept}, image = #{image} WHERE id = #{id}")
  int update(ProtoEntity proto);
}
