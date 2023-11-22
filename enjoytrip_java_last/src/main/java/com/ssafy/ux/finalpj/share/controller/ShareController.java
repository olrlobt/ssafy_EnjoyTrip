package com.ssafy.ux.finalpj.share.controller;


import com.ssafy.ux.finalpj.share.model.TravelRouteDto;
import com.ssafy.ux.finalpj.share.model.service.ShareService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public List<TravelRouteDto> list(@RequestBody(required = false) TravelRouteDto travelRouteDto) throws SQLException {
        return service.list(travelRouteDto);
    }

    @PostMapping("/write")
    public void write(@RequestBody TravelRouteDto travelRouteDto) throws SQLException {
        log.info("경로 저장");
        service.write(travelRouteDto);
    }

    @DeleteMapping("/delete")
    public void delete() throws SQLException {
        service.delete();
    }

    @PutMapping("/update")
    public void update() throws SQLException {
        service.update();
    }

}
