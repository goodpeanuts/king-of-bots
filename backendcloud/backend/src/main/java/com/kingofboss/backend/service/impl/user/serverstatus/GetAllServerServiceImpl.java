package com.kingofboss.backend.service.impl.user.serverstatus;

import com.kingofboss.backend.service.user.serverstatus.GetAllServerStatusService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class GetAllServerServiceImpl implements GetAllServerStatusService {

    @Override
    public List<String> getList() {
        List<String> serverStatusList = new ArrayList<>();

        String[] serverStatusUrls = {
                "http://127.0.0.1:3000/api/get-status/",
                "http://127.0.0.1:3001/matching/get-status/",
                "http://127.0.0.1:3002/bot/get-status/",
        };

        for (String serverUrl : serverStatusUrls) {
            try {
                URL url = new URL(serverUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode == 403) {
                        serverStatusList.add("在线");

                } else {
                        serverStatusList.add("离线");
                }
            } catch (IOException e) {
//                serverStatusList.add("异常：" + e.getMessage());
                serverStatusList.add("离线");
            }
        }

        return serverStatusList;
    }
}
