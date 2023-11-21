package com.ssafy.ux.finalpj.share.model.service;


import com.ssafy.ux.finalpj.share.mapper.ShareMapper;
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

    public List<TravelRouteDto> list() throws SQLException {
        return mapper.list();
    }

    public void write(TravelRouteDto travelRouteDto) throws SQLException {
        mapper.write(travelRouteDto);
    }

    public void delete() throws SQLException {
        mapper.delete();
    }

    public void update() throws SQLException {
        mapper.update();
    }
}
