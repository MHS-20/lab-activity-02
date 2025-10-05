package hexagonalArchitecture.ports;

import hexagonalArchitecture.domain.Game;

import java.util.Optional;

public interface GameRepository {
    void save(Game game);
    Optional<Game> findById(String id);
}
