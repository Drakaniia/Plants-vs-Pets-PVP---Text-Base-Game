public class Dog extends Pet {
    
    public Dog() {
        super("Dog", 150, 25, 4); // Medium health, strong attack, medium speed
    }
    
    @Override
    public void performAction(GameBoard board, Player player) {
        // Dog will eat plants if it's in the same position
        Plant plant = board.getPlantAt(row, col);
        if (plant != null) {
            // Attack the plant
            board.attackPlant(row, col, this.attackPower);
        } else if (col == 0) {
            // If dog reaches the leftmost column (player's house), end game
            player.setGameOver(true);
            player.setWon(false);
        }
    }
}