package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.rest.controller;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.request.LoginRequest;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.request.RegisterRequest;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.ExceptionHandler.BaseDescriptionException;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.security.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "IT-Academy - Authentication")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/mysql/auth")
public class AuthenticationController {
    //http://localhost:9005/swagger-ui/index.html


    private final AuthenticationService service;

    @Operation(
            summary = "Register endpoint",
            description = "Description: This method is to register a new player",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = BaseDescriptionException.EMPTY_DATABASE,
                            content = @Content),
                    @ApiResponse(
                            responseCode = "500",
                            description = BaseDescriptionException.E500_INTERNAL_ERROR,
                            content = @Content)
            }
    )
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(service.register(request));
    }


    @Operation(
            summary = "Authentication endpoint",
            description = "Description: This method is to authenticate a new player",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = BaseDescriptionException.EMPTY_DATABASE,
                            content = @Content),
                    @ApiResponse(
                            responseCode = "500",
                            description = BaseDescriptionException.E500_INTERNAL_ERROR,
                            content = @Content)
            }
    )
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody LoginRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));
    }



}
