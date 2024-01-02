package com.ssafy.ux.comment.mapper;

import com.ssafy.ux.comment.model.Comments;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Insert("INSERT INTO comments (content, articleNo, userId) VALUES (#{content}, #{articleNo}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "commentNo")
    void addComment(Comments comments);

    @Select("SELECT * FROM comments WHERE articleNo = #{articleNo}")
    List<Comments> getCommentsForArticle(@Param("articleNo") int articleNo);

    @Update("UPDATE comments set content = #{content} where commentNo = #{commentNo}")
    void modifyComment(Comments comments);

    @Delete("DELETE FROM comments where commentNo = #{commentNo}")
    void deleteComment(int commentNo);
}
