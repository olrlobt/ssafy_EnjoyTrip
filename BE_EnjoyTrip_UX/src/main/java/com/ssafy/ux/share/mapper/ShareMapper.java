package com.ssafy.ux.share.mapper;

import com.ssafy.ux.share.model.MarkerDto;
import com.ssafy.ux.share.model.SharedTravelRouteDto;
import com.ssafy.ux.share.model.TravelRouteDto;
import org.apache.ibatis.annotations.Mapper;
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
    List<SharedTravelRouteDto> listTop6TravelRoute() throws SQLException;

    @Update("update TravelRoute set hit = hit+1 where travelRouteNo = #{travelRouteNo}")
    void updateHit(int travelRouteNo) throws SQLException;

}
