package com.chameleonvision.web;

import com.chameleonvision.config.ConfigManager;
import io.javalin.Javalin;


public class Server {
    private static SocketHandler socketHandler;

    public static void main(int port) {
        socketHandler = new SocketHandler();
        Javalin app = Javalin.create(javalinConfig -> {
            javalinConfig.showJavalinBanner = false;
            javalinConfig.addStaticFiles("web");
            javalinConfig.enableCorsForAllOrigins();
        });
        app.ws("/websocket", ws -> {
            ws.onConnect(ctx -> {
                Server.socketHandler.onConnect(ctx);
                System.out.println("Socket Connected");
            });
            ws.onClose(ctx -> {
                Server.socketHandler.onClose(ctx);
                System.out.println("Socket Disconnected");
                ConfigManager.saveGeneralSettings();
            });
            ws.onBinaryMessage(ctx -> {
                Server.socketHandler.onBinaryMessage(ctx);
            });
        });
        app.post("/api/settings/general", Requesthandler::onGeneralSettings);
        app.post("/api/settings/camera", Requesthandler::onCameraSettings);
        app.start(port);
    }
}
