package com.ssafy.ux.sharecomments.mapper;

import com.ssafy.ux.sharecomments.model.SharedTravelRouteComments;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ShareCommentMapper {
    @Insert("INSERT INTO SHAREDTRAVELROUTECOMMENTS (content, sharedTravelRouteNo, userId) VALUES (#{content}, #{sharedTravelRouteNo}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "sharedTravelRouteNo")
    void addComment(SharedTravelRouteComments sharedTravelRouteComments);


    @Select("SELECT * FROM SHAREDTRAVELROUTECOMMENTS WHERE sharedTravelRouteNo = #{sharedTravelRouteNo}")
    List<SharedTravelRouteComments> getCommentsForArticle(@Param("sharedTravelRouteNo") int sharedTravelRouteNo);

    @Update("UPDATE SHAREDTRAVELROUTECOMMENTS set content = #{content} where sharedTravelRouteNo = #{sharedTravelRouteNo}")
    void modifyComment(SharedTravelRouteComments sharedTravelRouteComments);

    @Delete("DELETE FROM SHAREDTRAVELROUTECOMMENTS where commentNo = #{commentNo}")
    void deleteComment(int commentNo);
}
