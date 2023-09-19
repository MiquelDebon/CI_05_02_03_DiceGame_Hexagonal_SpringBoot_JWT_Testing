package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.ports.out;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.model.Game;

import java.util.List;

public interface GameRepositoryPort {
    List<Game> findAll();
    List<Game> findByPlayerId(int id);
    Game save(Game game);
    List<Game> deleteByPlayerId(int id);


}
