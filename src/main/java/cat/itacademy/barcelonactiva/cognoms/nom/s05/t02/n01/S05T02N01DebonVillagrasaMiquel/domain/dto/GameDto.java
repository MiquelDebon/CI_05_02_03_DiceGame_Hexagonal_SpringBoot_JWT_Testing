package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Game DTO Information")
public class GameDto {
    @Schema(defaultValue = "7", description = "Here you have the mark")
    @JsonProperty(value = "Mark")
    private int mark;

    @Schema(defaultValue = "WIN/LOSE", description = "Here you know if you have won or lost")
    @JsonProperty(value = "Result")
    private String message;

    public GameDto(int mark) {
        this.mark = mark;
        message = mark > 7 ? "WIN" : "LOSE";
    }
}
