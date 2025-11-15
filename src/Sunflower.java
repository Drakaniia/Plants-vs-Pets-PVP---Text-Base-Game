public class Sunflower extends Plant {
    private int sunProduction;
    
    public Sunflower() {
        super("Sunflower", 100, 0, 50); // Costs 50 suns, produces suns
        this.sunProduction = 25;
    }
    
    public int getSunProduction() {
        return sunProduction;
    }
    
    @Override
    public void performAction(GameBoard board, int row, int col) {
        // Sunflowers generate suns each turn
        // This will be handled in the main game loop
    }
}