package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.ports.in;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.dto.GameDto;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.request.RegisterRequest;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.dto.PlayerGameDto;

import java.util.List;

public interface PlayerGameUsesCasesPort {

    List<PlayerGameDto> getAllPlayers();
    List<PlayerGameDto> getAllPlayersRanking();
    PlayerGameDto updatePlayer(RegisterRequest updatedPlayer, int idUpdatePlayer);
    GameDto saveGame(int id);
    List<GameDto> deleteGamesByPlayerId(int id);
    PlayerGameDto findPlayerById(int id);
    List<GameDto> findGamesByPlayerId(int id);
    PlayerGameDto getWorstPlayer();
    PlayerGameDto getBestPlayer();
    Double averageTotalMarks();
}
