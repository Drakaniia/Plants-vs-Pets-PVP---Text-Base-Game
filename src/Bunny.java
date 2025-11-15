public class Bunny extends Pet {
    
    public Bunny() {
        super("Bunny", 80, 15, 3); // Low health, medium attack, medium speed
    }
    
    @Override
    public void performAction(GameBoard board, Player player) {
        // Bunny will eat plants if it's in the same position
        Plant plant = board.getPlantAt(row, col);
        if (plant != null) {
            // Attack the plant
            board.attackPlant(row, col, this.attackPower);
        } else if (col == 0) {
            // If bunny reaches the leftmost column (player's house), end game
            player.setGameOver(true);
            player.setWon(false);
        }
    }
}