package com.ssafy.ux.finalpj.share.mapper;

import com.ssafy.ux.finalpj.share.model.MarkerDto;
import com.ssafy.ux.finalpj.share.model.SharedTravelRouteDto;
import com.ssafy.ux.finalpj.share.model.TravelRouteDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface ShareMapper {

    List<TravelRouteDto> list(String userId) throws SQLException;
    void writeTravelRoute(TravelRouteDto travelRouteDto) throws SQLException;
    void writeMarker(MarkerDto markerDto) throws SQLException;
    void delete(int travelRouteNo) throws SQLException;
    void update(TravelRouteDto travelRouteDto) throws SQLException;
    void writeSharedTravelRoute(TravelRouteDto travelRouteDto) throws SQLException;
    List<SharedTravelRouteDto> listSharedTravelRoute() throws SQLException;


    //TOP6 List
    @Select("select * TravelRoute order by hit desc limit 6")
    List<SharedTravelRouteDto> listTop6TravelRoute() throws SQLException;

    @Update("update TravelRoute set hit = hit+1 where travelRouteNo = #{travelRouteNo}")
    void updateHit(int travelRouteNo) throws SQLException;

}
