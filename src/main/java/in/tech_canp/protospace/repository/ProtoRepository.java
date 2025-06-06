package in.tech_canp.protospace.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import in.tech_camp.pictweet.entity.ProtoEntity;

@Mapper
public interface ProtoRepository {

  @Insert("INSERT INTO tweets (name, text, image) VALUES (#{name}, #{text}, #{image})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(ProtoEntity proto);

}

