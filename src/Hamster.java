public class Hamster extends Pet {
    
    public Hamster() {
        super("Hamster", 60, 10, 2); // Low health, weak attack, fast speed
    }
    
    @Override
    public void performAction(GameBoard board, Player player) {
        // Hamster will eat plants if it's in the same position
        Plant plant = board.getPlantAt(row, col);
        if (plant != null) {
            // Attack the plant
            board.attackPlant(row, col, this.attackPower);
        } else if (col == 0) {
            // If hamster reaches the leftmost column (player's house), end game
            player.setGameOver(true);
            player.setWon(false);
        }
    }
}