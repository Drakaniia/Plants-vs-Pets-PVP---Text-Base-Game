public class SplashyFern extends Plant {
    
    public SplashyFern() {
        super("Splashy Fern", 80, 30, 150); // Costs 150 suns, area attack
    }
    
    @Override
    public void performAction(GameBoard board, int row, int col) {
        // Splashy Fern attacks all pets in adjacent columns (current, left, right)
        board.attackPetsInArea(row, col, this.attackPower);
    }
}