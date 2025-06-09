package in.tech_camp.protospace.repository;

import java.util.List

import  org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import in.tech_camp.protospace.entity.ProtoEntity;

@Mapper
public interface ProtoRepository {

  @Select("SELECT * FROM protos")
  List<ProtoEntity> findAll();

  @Insert("INSERT INTO protos (name, text, image) VALUES (#{name}, #{text}, #{image})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(ProtoEntity proto);

}