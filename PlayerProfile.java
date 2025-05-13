// (Project)Angel Grajeda-Cervantes: PlayerProfile class for storing player stats and chips.
public class PlayerProfile {
    public String name;
    public int wins;
    public int losses;
    public int chips;

    public PlayerProfile(String name) {
        this.name = name;
        this.wins = 0;
        this.losses = 0;
        this.chips = 100; // Starting chips
    }
}