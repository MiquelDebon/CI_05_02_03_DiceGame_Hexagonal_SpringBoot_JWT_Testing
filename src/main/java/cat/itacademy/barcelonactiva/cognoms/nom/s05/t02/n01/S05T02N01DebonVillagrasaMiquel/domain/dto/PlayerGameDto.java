package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Summary PLayer + AVG(Games) DTO Information")
public class PlayerGameDto {
    @Schema(defaultValue = "Name", description = "Player Name")
    @JsonProperty(value = "Name")
    private String name;

    @Schema(defaultValue = "0", description = "Number of games")
    @JsonProperty(value = "Amount of games")
    private int amountOfGames;

    @Schema(defaultValue = "0", description = "Average mark")
    @JsonProperty(value = "Average mark")
    private double averageMark;

    @Schema(defaultValue = "0", description = "Success rate")
    @JsonProperty(value = "Success rate")
    private String successRate;

}
