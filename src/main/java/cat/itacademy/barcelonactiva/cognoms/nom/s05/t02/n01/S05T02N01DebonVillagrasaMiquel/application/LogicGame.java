package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.application;

public class LogicGame {
    public static int PLAY(){
        int range = 12;
        int min = 2;
        return (int)(Math.random() * range) + min;
    }
}
