package hexagonalArchitecture.application;

import hexagonalArchitecture.ports.GameService;
import hexagonalArchitecture.ports.UserService;
import io.vertx.ext.web.Router;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;
import hexagonalArchitecture.domain.GameSymbolType;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

public class VertxHttpController {
    private final GameService gameService;
    private final UserService userService;

    public VertxHttpController(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
    }

    public Router createRouter(Vertx vertx) {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.post("/api/createGame").handler(this::createGame);
        router.post("/api/joinGame").handler(this::joinGame);
        router.post("/api/makeAMove").handler(this::makeMove);
        router.post("/api/registerUser").handler(this::registerUser);
        router.route("/public/*").handler(StaticHandler.create());
        return router;
    }

    private void createGame(RoutingContext ctx) {
        var id = gameService.createGame();
        ctx.json(new JsonObject().put("gameId", id));
    }

    private void joinGame(RoutingContext ctx) {
        var body = ctx.body().asJsonObject();
        try {
            gameService.joinGame(body.getString("gameId"), body.getString("userId"), GameSymbolType.fromString(body.getString("symbol")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ctx.json(new JsonObject().put("result", "accepted"));
    }

    private void makeMove(RoutingContext ctx) {
        var b = ctx.body().asJsonObject();
        try {
            int x = Integer.parseInt(b.getString("x"));
            int y = Integer.parseInt(b.getString("y"));
            gameService.makeMove(b.getString("gameId"), b.getString("userId"), GameSymbolType.fromString(b.getString("symbol")),
                    x, y);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ctx.json(new JsonObject().put("result", "ok"));
    }

    private void registerUser(RoutingContext ctx) {
        var body = ctx.body().asJsonObject();
        var name = body.getString("userName");
        var user = userService.registerUser(name);
        ctx.json(new JsonObject()
                .put("userId", user.id())
                .put("userName", user.name()));
    }
}
