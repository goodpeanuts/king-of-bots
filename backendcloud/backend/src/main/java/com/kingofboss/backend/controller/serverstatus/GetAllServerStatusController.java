package com.kingofboss.backend.controller.serverstatus;

import com.kingofboss.backend.service.user.serverstatus.GetAllServerStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetAllServerStatusController {
    @Autowired
    private GetAllServerStatusService getAllServerStatusService;

    @GetMapping("/api/user/server/refresh/")
    List getList() {
        return getAllServerStatusService.getList();
    }
}
