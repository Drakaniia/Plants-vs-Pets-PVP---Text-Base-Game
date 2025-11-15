public class Peashooter extends Plant {
    
    public Peashooter() {
        super("Peashooter", 100, 25, 100); // Costs 100 suns, attacks for 25
    }
    
    @Override
    public void performAction(GameBoard board, int row, int col) {
        // Peashooter attacks the first pet in its row
        if (board.hasPetInRow(row, col)) {
            // Find the first pet in the row and attack it
            int petCol = board.getFirstPetColumn(row, col);
            if (petCol != -1) {
                board.attackPet(row, petCol, this.attackPower);
            }
        }
    }
}