package com.ssafy.ux.finalpj.share.mapper;

import com.ssafy.ux.finalpj.share.model.MarkerDto;
import com.ssafy.ux.finalpj.share.model.TravelRouteDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface ShareMapper {

    List<TravelRouteDto> list() throws SQLException;
    void writeTravelRoute(TravelRouteDto travelRouteDto) throws SQLException;
    void writeMarker(MarkerDto markerDto) throws SQLException;
    void delete() throws SQLException;
    void update() throws SQLException;

}
