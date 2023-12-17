package com.ssafy.ux.share.controller;


import com.ssafy.ux.share.model.SharedTravelRouteDto;
import com.ssafy.ux.share.model.TravelRouteDto;
import com.ssafy.ux.share.model.service.ShareService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/list/{userId}")
    public List<TravelRouteDto> list(@PathVariable(required = false) String userId) throws SQLException {
        return service.list(userId);
    }

    @PostMapping("/write")
    public void write(@RequestBody TravelRouteDto travelRouteDto) throws SQLException {
        log.info("경로 저장");
        service.write(travelRouteDto);
    }

    @DeleteMapping("/delete/{travelRouteNo}")
    public void delete(@PathVariable int travelRouteNo) throws SQLException {
        service.delete(travelRouteNo);
    }

    @PutMapping("/update")
    public void update(@RequestBody TravelRouteDto travelRouteDto) throws SQLException {
        service.update(travelRouteDto);
    }

    @PostMapping("/writeShared")
    public void writeSharedTravelRoute(@RequestBody TravelRouteDto travelRouteDto) throws SQLException {
        service.writeSharedTravelRoute(travelRouteDto);
    }

    @GetMapping("/listSharedTravelRoute")
    public List<SharedTravelRouteDto> listSharedTravelRoute() throws SQLException{
        return service.listSharedTravelRoute();
    }

    @GetMapping("/listTop6TravelRoute")
    public List<SharedTravelRouteDto> listTop6TravelRoute() throws SQLException{
        return service.listTop6TravelRoute();
    }

    @PutMapping("/updatehit")
    public ResponseEntity<?> UpdateHit(@RequestBody String travelRouteNo) {
        try {
            service.updateHit(Integer.parseInt(travelRouteNo));
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
