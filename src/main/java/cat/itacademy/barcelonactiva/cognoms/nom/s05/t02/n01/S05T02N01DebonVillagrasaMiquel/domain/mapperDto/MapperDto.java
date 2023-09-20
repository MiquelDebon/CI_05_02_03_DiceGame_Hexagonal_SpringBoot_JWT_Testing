package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.mapperDto;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.dto.GameDto;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.dto.PlayerGameDto;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.model.Game;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.model.Player;

public class MapperDto {
    public static PlayerGameDto playerDTOfromPlayer(Player player){
        return new PlayerGameDto(player.getName(), player.getAmountOfGames(), player.getAverageMark(), (player.getSuccessRate() + " %"));
    }
    public static GameDto gameDTOfromGame(Game game){
        return new GameDto(game.getMark());
    }


}
