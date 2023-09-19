package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.domain.model;

import lombok.Builder;
import lombok.Data;

import java.text.SimpleDateFormat;

@Builder
@Data
public class Player{
    private Integer id;
    private String name;
    private String surname;
    private String registerDate;
    private String email;
    private String password;

    private int amountOfGames = 0;
    private int wonGames = 0;
    private double averageMark = 0;
    private double successRate = 0;
    private int sumMark = 0;

    private Role role;

    public Player(String name){
        this.name = name;
        registerDate = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
                .format(new java.util.Date());
    }

    public Player(Integer id, String name){
        this.id = id;
        this.name = name;
        registerDate = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
                .format(new java.util.Date());
    }

    public Player(Integer id, String name, String surname, String registerDate, String email, String password, int amountOfGames, int wonGames, double averageMark, double successRate, int sumMark, Role role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.registerDate = registerDate;
        this.email = email;
        this.password = password;
        this.amountOfGames = amountOfGames;
        this.wonGames = wonGames;
        this.averageMark = averageMark;
        this.successRate = successRate;
        this.sumMark = sumMark;
        this.role = role;
    }

    public void addAmountGames(){
        amountOfGames++;
    }
    public void addWonGames(){
        wonGames++;
    }

    public Player autoSetNewGamesRates(int newGameMark){
        this.addAmountGames();
        this.setSumMark(this.sumMark + newGameMark);
        if(newGameMark > 7) this.addWonGames();

        double newSuccessRate = (double) Math.round(((double) this.wonGames / this.amountOfGames) * 10000) /100;
        this.setSuccessRate(newSuccessRate);

        double newAverageMark =
                (double) Math.round(((double)(sumMark)/amountOfGames)
                        *100.00)/100.00;
        this.setAverageMark(newAverageMark);
        return this;
    }

    public void resetAllGamesRate(){
        this.setAmountOfGames(0);
        this.setWonGames(0);
        this.setAverageMark(0);
        this.setSuccessRate(0);
        this.setSumMark(0);
    }


}
