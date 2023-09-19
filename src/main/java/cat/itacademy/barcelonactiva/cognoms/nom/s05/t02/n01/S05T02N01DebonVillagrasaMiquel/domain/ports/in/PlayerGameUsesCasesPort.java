package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.ports.in;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.application.request.RegisterRequest;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.model.Game;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.model.Player;

import java.util.List;

public interface PlayerGameUsesCasesPort {

    List<Player> getAllPlayers();
    List<Player> getAllPlayersRanking();
    Player updatePlayer(RegisterRequest updatedPlayer, int idUpdatePlayer);
    Game saveGame(int id);
    List<Game> deleteGamesByPlayerId(int id);
    Player findPlayerById(int id);
    List<Game> findGamesByPlayerId(int id);
    Player getWorstPlayer();
    Player getBestPlayer();
    Double averageTotalMarks();
}
