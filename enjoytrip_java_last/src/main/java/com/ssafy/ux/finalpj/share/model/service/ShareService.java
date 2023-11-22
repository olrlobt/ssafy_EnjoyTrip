package com.ssafy.ux.finalpj.share.model.service;


import com.ssafy.ux.finalpj.share.mapper.ShareMapper;
import com.ssafy.ux.finalpj.share.model.MarkerDto;
import com.ssafy.ux.finalpj.share.model.TravelRouteDto;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ShareService {

    private ShareMapper mapper;

    public ShareService(ShareMapper mapper) {
        this.mapper = mapper;
    }

    public List<TravelRouteDto> list(TravelRouteDto travelRouteDto) throws SQLException {
        return mapper.list(travelRouteDto);
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

    public void delete() throws SQLException {
        mapper.delete();
    }

    public void update() throws SQLException {
        mapper.update();
    }
}
