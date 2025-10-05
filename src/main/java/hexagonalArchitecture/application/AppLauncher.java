package hexagonalArchitecture.application;

import hexagonalArchitecture.adapters.*;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;

public class AppLauncher {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        var userRepo = new FileUserRepository("users.json");
        var gameRepo = new FileGameRepository();
        var publisher = new VertxEventPublisher(vertx);

        var userService = new UserServiceImpl(userRepo);
        var gameService = new GameServiceImpl(gameRepo, publisher);

        var controller = new VertxHttpController(gameService, userService);

        HttpServer server = vertx.createHttpServer();
        server.requestHandler(controller.createRouter(vertx)).listen(8080);
    }
}
