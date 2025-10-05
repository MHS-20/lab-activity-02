package hexagonalArchitecture.adapters;

import hexagonalArchitecture.domain.Game;
import hexagonalArchitecture.domain.GameSymbolType;
import hexagonalArchitecture.domain.User;
import hexagonalArchitecture.ports.EventPublisher;
import hexagonalArchitecture.ports.GameRepository;
import hexagonalArchitecture.ports.GameService;

import io.vertx.core.json.JsonObject;

public class GameServiceImpl implements GameService {

    private final GameRepository games;
    private final EventPublisher events;

    public GameServiceImpl(GameRepository games, EventPublisher events) {
        this.games = games;
        this.events = events;
    }

    @Override
    public String createGame() {
        String id = "game-" + System.currentTimeMillis();
        Game game = new Game(id);
        games.save(game);
        return id;
    }

    @Override
    public void joinGame(String gameId, String userId, GameSymbolType symbol) throws Exception {
        Game game = games.findById(gameId).orElseThrow();
        game.joinGame(new User(userId, "anon"), symbol);
        games.save(game);
    }

    @Override
    public void makeMove(String gameId, String userId, GameSymbolType symbol, int x, int y) throws Exception {
        Game game = games.findById(gameId).orElseThrow();
        game.makeAmove(new User(userId, "anon"), symbol, x, y);
        games.save(game);

        JsonObject event = new JsonObject()
                .put("event", "new-move")
                .put("gameId", gameId)
                .put("symbol", String.valueOf(symbol))
                .put("x", x)
                .put("y", y);
        events.publish("ttt-events-" + gameId, event);

        if (game.isGameEnd()) {
            JsonObject end = new JsonObject()
                    .put("event", "game-ended");
            game.getWinner().ifPresentOrElse(
                    w -> end.put("winner", w.toString()),
                    () -> end.put("result", "tie")
            );
            events.publish("ttt-events-" + gameId, end);
        }
    }
}
