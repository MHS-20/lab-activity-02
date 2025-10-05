package hexagonalArchitecture.application;

import hexagonalArchitecture.ports.GameService;
import hexagonalArchitecture.ports.UserService;
import io.vertx.ext.web.Router;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;
import hexagonalArchitecture.domain.GameSymbolType;

public class VertxHttpController {
    private final GameService gameService;
    private final UserService userService;

    public VertxHttpController(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
    }

    public Router createRouter(Vertx vertx) {
        Router router = Router.router(vertx);
        router.post("/api/createGame").handler(this::createGame);
        router.post("/api/joinGame").handler(this::joinGame);
        router.post("/api/makeMove").handler(this::makeMove);
        router.post("/api/registerUser").handler(this::registerUser);

        return router;
    }

    private void createGame(RoutingContext ctx) {
        var id = gameService.createGame();
        ctx.json(new io.vertx.core.json.JsonObject().put("gameId", id));
    }

    private void joinGame(RoutingContext ctx) {
        var body = ctx.body().asJsonObject();
        try {
            gameService.joinGame(body.getString("gameId"), body.getString("userId"), GameSymbolType.fromString(body.getString("symbol")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ctx.json(new io.vertx.core.json.JsonObject().put("result", "accepted"));
    }

    private void makeMove(RoutingContext ctx) {
        var b = ctx.body().asJsonObject();
        try {
            gameService.makeMove(b.getString("gameId"), b.getString("userId"), GameSymbolType.fromString(b.getString("symbol")),
                    b.getInteger("x"), b.getInteger("y"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ctx.json(new io.vertx.core.json.JsonObject().put("result", "ok"));
    }

    private void registerUser(RoutingContext ctx) {
        var body = ctx.body().asJsonObject();
        var name = body.getString("userName");
        var user = userService.registerUser(name);
        ctx.json(new io.vertx.core.json.JsonObject()
                .put("userId", user.id())
                .put("userName", user.name()));
    }
}
