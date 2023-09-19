package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.rest.mapperDto;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.response.GameDTO;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.response.PlayerGameDTO;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.model.Game;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.model.Player;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.entity.GameMongoDB;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.entity.PlayerMySQL;

public class MapperDto {
    public static PlayerGameDTO playerDTOfromPlayer(Player player){
        return new PlayerGameDTO(player.getId(), player.getName(), player.getAverageMark(), (player.getSuccessRate() + " %"));
    }
    public static PlayerGameDTO playerDTOfromPlayer(PlayerMySQL player){
        return new PlayerGameDTO(player.getId(), player.getName(), player.getAverageMark(), (player.getSuccessRate() + " %"));
    }
    public static GameDTO gameDTOfromGame(Game game){
        return new GameDTO(game.getMark());
    }
    public static GameDTO gameDTOfromGame(GameMongoDB game){
        return new GameDTO(game.getMark());
    }




}
