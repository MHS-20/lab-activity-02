package hexagonalArchitecture.ports;

import hexagonalArchitecture.domain.GameSymbolType;
import ttt_backend.Game;
import ttt_backend.User;

public interface GameService {
    String createGame();
    void joinGame(String gameId, String userId, GameSymbolType symbol) throws Exception;
    void makeMove(String gameId, String userId, GameSymbolType symbol, int x, int y) throws Exception;
}
