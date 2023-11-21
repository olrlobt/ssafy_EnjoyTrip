package com.ssafy.ux.finalpj.share.controller;


import com.ssafy.ux.finalpj.share.model.TravelRouteDto;
import com.ssafy.ux.finalpj.share.model.service.ShareService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/share")
public class ShareController {

    private ShareService service;

    public ShareController(ShareService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public List<TravelRouteDto> list() throws SQLException {
        return service.list();
    }

    @GetMapping("/write")
    public void write(TravelRouteDto travelRouteDto) throws SQLException {
        service.write(travelRouteDto);
    }

    @GetMapping("/delete")
    public void delete() throws SQLException {
        service.delete();
    }

    @GetMapping("/update")
    public void update() throws SQLException {
        service.update();
    }

}
