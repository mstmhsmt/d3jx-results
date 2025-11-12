package com.chameleonvision.web;

import com.chameleonvision.config.ConfigManager;
import io.javalin.Javalin;

public class Server {
    private static SocketHandler socketHandler;

    public static void main(int port) {
        socketHandler = new SocketHandler();

<<<<<<< commits-gh_100/PhotonVision/photonvision/663786e21b1224255c53743add8b3a9c8913f521/Server-121b786.java
        Javalin app = Javalin.create(javalinConfig -> javalinConfig.showJavalinBanner = false);
        app.config.addStaticFiles("web");
||||||| commits-gh_100/PhotonVision/photonvision/570a042e35e35538292ef4b0d0748be6cdab5647/Server-e90657c.java
        Javalin app = Javalin.create(javalinConfig -> javalinConfig.showJavalinBanner=false);
        app.config.addStaticFiles("web");
=======
        Javalin app = Javalin.create(javalinConfig -> {
            javalinConfig.showJavalinBanner = false;
            javalinConfig.addStaticFiles("web");
            javalinConfig.enableCorsForAllOrigins();
        });
>>>>>>> commits-gh_100/PhotonVision/photonvision/182ba8abe370de3ee9e358ea3344cea1f1bf3c9f/Server-020c039.java
        app.ws("/websocket", ws -> {
            ws.onConnect(ctx -> {
                socketHandler.onConnect(ctx);
                System.out.println("Socket Connected");
            });
            ws.onClose(ctx -> {
                socketHandler.onClose(ctx);
                System.out.println("Socket Disconnected");
                ConfigManager.saveGeneralSettings();
            });
            ws.onBinaryMessage(ctx -> {
                socketHandler.onBinaryMessage(ctx);
            });
        });
        app.post("/api/settings/general", Requesthandler::onGeneralSettings);
        app.post("/api/settings/camera", Requesthandler::onCameraSettings);
        app.start(port);
    }
}