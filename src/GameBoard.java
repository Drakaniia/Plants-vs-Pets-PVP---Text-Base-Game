public class GameBoard {
    private static final int ROWS = 5;
    private static final int COLS = 9;
    
    private Plant[][] plants;
    private Pet[][] pets;
    
    public GameBoard() {
        this.plants = new Plant[ROWS][COLS];
        this.pets = new Pet[ROWS][COLS];
    }
    
    public int getRows() {
        return ROWS;
    }
    
    public int getCols() {
        return COLS;
    }
    
    public Plant getPlantAt(int row, int col) {
        if (isValidPosition(row, col)) {
            return plants[row][col];
        }
        return null;
    }
    
    public void setPlantAt(int row, int col, Plant plant) {
        if (isValidPosition(row, col)) {
            plants[row][col] = plant;
            plant.setPlanted(true);
        }
    }
    
    public Pet getPetAt(int row, int col) {
        if (isValidPosition(row, col)) {
            return pets[row][col];
        }
        return null;
    }
    
    public void setPetAt(int row, int col, Pet pet) {
        if (isValidPosition(row, col)) {
            pets[row][col] = pet;
            pet.setPosition(row, col);
        }
    }
    
    public boolean hasPetInRow(int row, int startCol) {
        for (int col = startCol; col < COLS; col++) {
            if (pets[row][col] != null) {
                return true;
            }
        }
        return false;
    }
    
    public int getFirstPetColumn(int row, int startCol) {
        for (int col = startCol; col < COLS; col++) {
            if (pets[row][col] != null) {
                return col;
            }
        }
        return -1; // No pet found
    }
    
    public void attackPet(int row, int col, int damage) {
        if (isValidPosition(row, col) && pets[row][col] != null) {
            pets[row][col].takeDamage(damage);
            if (!pets[row][col].isAlive()) {
                pets[row][col] = null;
            }
        }
    }
    
    public void attackPlant(int row, int col, int damage) {
        if (isValidPosition(row, col) && plants[row][col] != null) {
            plants[row][col].takeDamage(damage);
            if (!plants[row][col].isAlive()) {
                plants[row][col] = null;
            }
        }
    }
    
    public void attackPetsInArea(int row, int col, int damage) {
        // Attack pets in current column and adjacent columns
        for (int r = Math.max(0, row - 1); r <= Math.min(ROWS - 1, row + 1); r++) {
            for (int c = Math.max(0, col - 1); c <= Math.min(COLS - 1, col + 1); c++) {
                if (pets[r][c] != null) {
                    pets[r][c].takeDamage(damage);
                    if (!pets[r][c].isAlive()) {
                        pets[r][c] = null;
                    }
                }
            }
        }
    }
    
    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS;
    }
    
    public void displayBoard() {
        System.out.println("  0 1 2 3 4 5 6 7 8");
        for (int row = 0; row < ROWS; row++) {
            System.out.print(row + " ");
            for (int col = 0; col < COLS; col++) {
                if (plants[row][col] != null) {
                    // Display plant (first letter of name)
                    System.out.print(plants[row][col].getName().charAt(0) + " ");
                } else if (pets[row][col] != null) {
                    // Display pet (first letter of name)
                    System.out.print(pets[row][col].getName().charAt(0) + " ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    
    public boolean hasNoPets() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (pets[row][col] != null) {
                    return false;
                }
            }
        }
        return true;
    }
}