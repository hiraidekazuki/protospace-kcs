package in.tech_camp.protospace.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import in.tech_camp.protospace.entity.ProtoEntity;

// import in.tech_camp.protospace.entity.ProtoEntity;

@Mapper
public interface ProtoRepository {

  @Insert("INSERT INTO protos (name, catchcopy, concept, image, user_name) VALUES (#{name}, #{catchcopy}, #{concept}, #{image}, #{user_name})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(ProtoEntity proto);

}