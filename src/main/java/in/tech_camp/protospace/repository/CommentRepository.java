// 仮置きデータ

package in.tech_camp.protospace.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import in.tech_camp.protospace.entity.CommentEntity;

@Mapper
public interface CommentRepository {

    @Select("SELECT * FROM comments WHERE proto_id = #{protoId} ORDER BY created_at DESC")
    List<CommentEntity> findByProtoId(Integer protoId);

}
