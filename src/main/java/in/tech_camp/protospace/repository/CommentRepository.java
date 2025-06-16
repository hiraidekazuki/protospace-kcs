package in.tech_camp.protospace.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import in.tech_camp.protospace.entity.CommentEntity;

@Mapper
public interface CommentRepository {

    @Select("SELECT * FROM comments WHERE proto_id = #{protoId} ORDER BY created_at DESC")
    @Results({
        @Result(property = "user", column = "user_id",
                one = @One(select = "in.tech_camp.protospace.repository.UserRepository.findById")),
        @Result(property = "proto", column = "proto_id",
                one = @One(select = "in.tech_camp.protospace.repository.ProtoRepository.findById"))
    })
    List<CommentEntity> findByProtoId(Integer protoId);

  
    @Insert("INSERT INTO comments (content, user_id, proto_id) VALUES (#{content}, #{user.id}, #{proto.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(CommentEntity comment);

}
