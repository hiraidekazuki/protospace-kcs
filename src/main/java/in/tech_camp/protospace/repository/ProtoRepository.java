package in.tech_camp.protospace.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import in.tech_camp.protospace.entity.ProtoEntity;

@Mapper
public interface ProtoRepository {

  @Insert("INSERT INTO protos (name, catchcopy, concept, image, user_name) VALUES (#{name}, #{catchcopy}, #{concept}, #{image}, #{user_name})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void save(ProtoEntity proto);

  @Select("SELECT * FROM protos")
  List<ProtoEntity> findAll();
}
