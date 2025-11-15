public class Wallnut extends Plant {
    
    public Wallnut() {
        super("Wallnut", 300, 0, 50); // Costs 50 suns, high health, no attack
    }
    
    @Override
    public void performAction(GameBoard board, int row, int col) {
        // Wallnut doesn't perform any action - it just absorbs damage
    }
}