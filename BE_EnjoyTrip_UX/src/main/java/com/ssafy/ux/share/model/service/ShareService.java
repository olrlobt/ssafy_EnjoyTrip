package com.ssafy.ux.share.model.service;


import com.ssafy.ux.share.mapper.ShareMapper;
import com.ssafy.ux.share.model.MarkerDto;
import com.ssafy.ux.share.model.SharedTravelRouteDto;
import com.ssafy.ux.share.model.TravelRouteDto;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ShareService {

    private ShareMapper mapper;

    public ShareService(ShareMapper mapper) {
        this.mapper = mapper;
    }

    public List<TravelRouteDto> list(String userId) throws SQLException {
        return mapper.list(userId);
    }

    public void write(TravelRouteDto travelRouteDto) throws SQLException {
        mapper.writeTravelRoute(travelRouteDto);
        int size = travelRouteDto.getMarkers().size();
        for (int step = 0; step < size; step++) {
            MarkerDto marker = travelRouteDto.getMarkers().get(step);
            marker.setTravelRouteNo(travelRouteDto.getTravelRouteNo());
            marker.setStep(step);
            mapper.writeMarker(marker);
        }
    }

    public void delete(int travelRouteNo) throws SQLException {
        mapper.delete(travelRouteNo);
    }

    public void update(TravelRouteDto travelRouteDto) throws SQLException {
        mapper.update(travelRouteDto);
    }


    public void updateHit(int travelRouteNo) throws SQLException{
        mapper.updateHit(travelRouteNo);
    }

    public void writeSharedTravelRoute(TravelRouteDto travelRouteDto) throws SQLException {
        mapper.writeSharedTravelRoute(travelRouteDto);
    }

    public List<SharedTravelRouteDto> listSharedTravelRoute() throws SQLException {
        return mapper.listSharedTravelRoute();
    }

    public List<SharedTravelRouteDto> listTop6TravelRoute() throws SQLException {
        return mapper.listTop6TravelRoute();
    }

}
