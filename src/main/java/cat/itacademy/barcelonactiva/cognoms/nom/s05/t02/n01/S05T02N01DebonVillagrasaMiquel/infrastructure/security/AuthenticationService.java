package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.security;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.application.request.LoginRequest;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.model.Player;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.ports.out.PlayerRepositoryPort;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.response.AuthenticationResponse;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.application.request.RegisterRequest;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.application.customExceptions.DuplicateUserEmailException;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.application.customExceptions.DuplicateUserNameException;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.application.customExceptions.UserNotFoundException;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.SimpleDateFormat;

@RequiredArgsConstructor
public class AuthenticationService{

    private final PlayerRepositoryPort repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public Player register(RegisterRequest request){
        checkDuplicatedEmail(request.getEmail());
        checkDuplicatedName(request.getFirstname());

        //Just for fun if you register with an admin email you are an admin ROLE
        Player user;
        if(!request.getEmail().contains("@admin.com")){
            user = buildPlayer(request, Role.USER);
        }else{
            user = buildPlayer(request, Role.ADMIN);
        }
        repository.save(user);

        return user;
    }

    public AuthenticationResponse authenticate (LoginRequest request){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword()));
        }catch (RuntimeException e){
            throw new UserNotFoundException();
        }
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(UserNotFoundException::new);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     *
     * Support methods
     */

    public Player buildPlayer(RegisterRequest request, Role role){
        return Player.builder()
                .name(request.getFirstname())
                .surname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .registerDate(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
                        .format(new java.util.Date()))
                .build();
    }

    public void checkDuplicatedEmail(String email){
        if(repository.findAll()
                .stream().map(Player::getEmail)
                .anyMatch((n)-> n.equalsIgnoreCase(email))
        ){
            throw new DuplicateUserEmailException("Duplicated Email");
        }
    }

    public void checkDuplicatedName(String name){
        if (
            !name.equalsIgnoreCase("ANONYMOUS")
            &&
            repository.findAll()
                    .stream().map(Player::getName)
                    .anyMatch((n)-> n.equalsIgnoreCase(name))
        ){
            throw new DuplicateUserNameException("Duplicated name");
        }
    }





}
