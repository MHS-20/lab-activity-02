package hexagonalArchitecture.adapters;

import hexagonalArchitecture.domain.Game;
import hexagonalArchitecture.ports.GameRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FileGameRepository implements GameRepository {
    private final Map<String, Game> storage = new HashMap<>();

    @Override
    public void save(Game game) { storage.put(game.getId(), game); }

    @Override
    public Optional<Game> findById(String id) { return Optional.ofNullable(storage.get(id)); }
}