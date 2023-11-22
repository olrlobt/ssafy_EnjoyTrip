package com.ssafy.ux.finalpj.comment.mapper;

import com.ssafy.ux.finalpj.comment.model.CommentDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Insert("INSERT INTO comments (content, articleNo) VALUES (#{content}, #{articleNo})")
    @Options(useGeneratedKeys = true, keyProperty = "commentNo")
    void addComment(CommentDto commentDto);


    @Select("SELECT * FROM comments WHERE articleNo = #{articleNo}")
    List<CommentDto> getCommentsForArticle(@Param("articleNo") int articleNo);
    
}
