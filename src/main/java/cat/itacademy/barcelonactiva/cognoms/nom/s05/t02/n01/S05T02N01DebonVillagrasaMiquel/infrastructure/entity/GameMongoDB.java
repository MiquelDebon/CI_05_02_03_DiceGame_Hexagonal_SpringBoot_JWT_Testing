package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.entity;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.model.Game;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "game")
public class GameMongoDB {
    @Id
    @NotBlank
    private String id;

    @NotBlank
    @Size(min = 2, max = 12)
    private int mark;

    @NotBlank
    private int playerId;

    public GameMongoDB(int mark, int player_id){
        this.mark = mark;
        this.playerId = player_id;
    }


    public Game toDomainModel(){
        return new Game(id, mark, playerId);
    }
    public static GameMongoDB fromDomainModel(Game game){
        return new GameMongoDB(game.getId(), game.getMark(), game.getPlayerId());
    }


}
