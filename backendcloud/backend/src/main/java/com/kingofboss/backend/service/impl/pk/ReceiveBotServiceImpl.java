package com.kingofboss.backend.service.impl.pk;

import com.kingofboss.backend.consumer.WebSocketServer;
import com.kingofboss.backend.consumer.utils.Game;
import com.kingofboss.backend.service.pk.ReceiveBotService;
import org.springframework.stereotype.Service;

@Service
public class ReceiveBotServiceImpl implements ReceiveBotService {
    @Override
    public String receiveBotMove(Integer userId, Integer direction) {
        System.out.println("receive bot move:" + userId + " " + direction + " " );
        if (WebSocketServer.users.get(userId) != null) {
            Game game = WebSocketServer.users.get(userId).game;
            if (game != null) {
                if (game.getPlayerA().getId().equals(userId)) {
                    game.setNextStepA(direction);
                } else if (game.getPlayerB().getId().equals(userId)) {
                    game.setNextStepB(direction);
                }
            }
        }

        return "receive bot move success";
    }
}
