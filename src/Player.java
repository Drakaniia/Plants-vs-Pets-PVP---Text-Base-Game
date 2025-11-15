import java.util.ArrayList;
import java.util.List;

public class Player {
    private int suns;
    private List<Plant> plantsInHand;
    private boolean gameOver;
    private boolean won;
    private int gameTurn;
    
    public Player() {
        this.suns = 100; // Start with 100 suns
        this.plantsInHand = new ArrayList<>();
        this.gameOver = false;
        this.won = false;
        this.gameTurn = 0;
        
        // Add initial plants to hand
        plantsInHand.add(new Sunflower());
        plantsInHand.add(new Peashooter());
        plantsInHand.add(new Wallnut());
        plantsInHand.add(new SplashyFern());
    }
    
    public int getSuns() {
        return suns;
    }
    
    public void addSuns(int amount) {
        this.suns += amount;
    }
    
    public void spendSuns(int amount) {
        if (suns >= amount) {
            this.suns -= amount;
        }
    }
    
    public List<Plant> getPlantsInHand() {
        return plantsInHand;
    }
    
    public boolean canAfford(Plant plant) {
        return suns >= plant.getCost();
    }
    
    public boolean isGameOver() {
        return gameOver;
    }
    
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
    
    public boolean hasWon() {
        return won;
    }
    
    public void setWon(boolean won) {
        this.won = won;
    }
    
    public int getGameTurn() {
        return gameTurn;
    }
    
    public void incrementTurn() {
        this.gameTurn++;
    }
    
    public Plant getPlantByName(String name) {
        for (Plant plant : plantsInHand) {
            if (plant.getName().equalsIgnoreCase(name)) {
                return plant;
            }
        }
        return null;
    }
}