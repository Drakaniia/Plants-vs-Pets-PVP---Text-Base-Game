import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    protected Player player;
    protected GameBoard board;
    private Scanner scanner;
    private Random random;
    protected boolean gameRunning;
    
    public Game() {
        this.player = new Player();
        this.board = new GameBoard();
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        this.gameRunning = true;
    }
    
    public void startGame() {
        System.out.println("Welcome to Plants vs Pets!");
        System.out.println("Defend your garden from pesky pets!");
        System.out.println("Collect suns to buy more plants.");
        System.out.print("Press Enter to begin...");
        try {
            scanner.nextLine();
        } catch (Exception e) {
            // If there's an issue with input, just continue
        }
        
        // Main game loop
        while (!player.isGameOver() && gameRunning) {
            // Display current state
            displayGameState();
            
            // Handle user input
            handleUserInput();
            
            // Generate suns from Sunflowers
            generateSuns();
            
            // Pets take actions (move and attack)
            movePets();
            
            // Plants take actions (attack)
            plantsTakeAction();
            
            // Check win condition
            if (board.hasNoPets() && player.getGameTurn() > 5) {
                player.setGameOver(true);
                player.setWon(true);
            }
            
            // Increment turn
            player.incrementTurn();
            
            // Add a small delay to make the game readable
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // Handle interruption
            }
        }
        
        // Game over logic
        if (player.hasWon()) {
            System.out.println("\nCongratulations! You defended your garden!");
            System.out.println("You won on turn " + player.getGameTurn());
        } else {
            System.out.println("\nOh no! The pets reached your house!");
            System.out.println("Game Over!");
        }
        
        System.out.println("Thanks for playing Plants vs Pets!");
    }
    
    protected void displayGameState() {
        System.out.println("\n=== Turn " + player.getGameTurn() + " ===");
        System.out.println("Suns: " + player.getSuns());
        board.displayBoard();
        System.out.println("\nAvailable Plants:");
        for (Plant plant : player.getPlantsInHand()) {
            System.out.println("- " + plant.getName() + " (Cost: " + plant.getCost() + ")");
        }
        System.out.println("\nCommands: 'place', 'info', 'quit'");
    }
    
    protected void handleUserInput() {
        System.out.print("\nEnter command: ");
        String input = "";
        try {
            input = scanner.nextLine().trim().toLowerCase();
        } catch (Exception e) {
            System.out.println("\nInput error. Type 'info' for help or 'quit' to exit.");
            return;
        }

        switch (input) {
            case "place":
                placePlant();
                break;
            case "info":
                showHelp();
                break;
            case "quit":
                gameRunning = false;
                System.out.println("Thanks for playing!");
                break;
            default:
                System.out.println("Unknown command. Type 'info' for help.");
                break;
        }
    }
    
    protected void placePlant() {
        System.out.println("\nPlant options:");
        System.out.println("1. Sunflower (Cost: 50)");
        System.out.println("2. Peashooter (Cost: 100)");
        System.out.println("3. Wallnut (Cost: 50)");
        System.out.println("4. Splashy Fern (Cost: 150)");
        System.out.print("Select plant (1-4): ");

        String plantChoice = "";
        try {
            plantChoice = scanner.nextLine().trim();
        } catch (Exception e) {
            System.out.println("Error reading plant selection.");
            return;
        }

        Plant selectedPlant = null;

        switch (plantChoice) {
            case "1":
                selectedPlant = new Sunflower();
                break;
            case "2":
                selectedPlant = new Peashooter();
                break;
            case "3":
                selectedPlant = new Wallnut();
                break;
            case "4":
                selectedPlant = new SplashyFern();
                break;
            default:
                System.out.println("Invalid choice!");
                return;
        }

        if (!player.canAfford(selectedPlant)) {
            System.out.println("Not enough suns! You need " + selectedPlant.getCost() + " suns.");
            return;
        }

        // Ask for position
        System.out.print("Enter row (0-" + (board.getRows()-1) + "): ");
        int row = -1;
        try {
            row = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid row number!");
            return;
        }

        System.out.print("Enter column (0-" + (board.getCols()-1) + "): ");
        int col = -1;
        try {
            col = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid column number!");
            return;
        }

        if (row < 0 || row >= board.getRows() || col < 0 || col >= board.getCols()) {
            System.out.println("Invalid position!");
            return;
        }

        if (board.getPlantAt(row, col) != null) {
            System.out.println("There's already a plant in that position!");
            return;
        }

        // Place the plant
        board.setPlantAt(row, col, selectedPlant);
        player.spendSuns(selectedPlant.getCost());
        System.out.println("Placed " + selectedPlant.getName() + " at (" + row + "," + col + ")");
    }
    
    protected void generateSuns() {
        // Go through each cell and check for sunflowers
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                Plant plant = board.getPlantAt(row, col);
                if (plant instanceof Sunflower) {
                    player.addSuns(((Sunflower) plant).getSunProduction());
                }
            }
        }
    }
    
    protected void movePets() {
        // Create a temporary list to avoid concurrent modification
        List<Pet> petsToMove = new ArrayList<>();
        int[][] petPositions = new int[board.getRows()][board.getCols()];
        
        // Collect all pets and their positions
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                if (board.getPetAt(row, col) != null) {
                    petsToMove.add(board.getPetAt(row, col));
                    petPositions[row][col] = 1; // Mark position
                }
            }
        }
        
        // Move pets from right to left to avoid position conflicts
        for (int col = board.getCols() - 1; col >= 0; col--) {
            for (int row = 0; row < board.getRows(); row++) {
                Pet pet = board.getPetAt(row, col);
                if (pet != null && pet.isAlive()) {
                    // Decrement move cooldown
                    pet.decrementMoveCooldown();
                    
                    if (pet.canMove()) {
                        // Try to move left (towards column 0)
                        if (col > 0) {
                            // Check if there's a plant in the next position
                            if (board.getPlantAt(row, col - 1) != null) {
                                // Attack the plant instead of moving
                                pet.performAction(board, player);
                            } else {
                                // Move to the left
                                board.setPetAt(row, col - 1, pet);
                                board.setPetAt(row, col, null); // Clear old position
                                pet.setPosition(row, col - 1);
                            }
                        } else {
                            // Pet reached the house (leftmost column)
                            pet.performAction(board, player);
                        }
                        
                        // Reset move cooldown after moving
                        pet.resetMoveCooldown();
                    } else {
                        // Pet can't move this turn, still perform action if on plant
                        if (board.getPlantAt(row, col) != null) {
                            pet.performAction(board, player);
                        }
                    }
                } else if (pet != null && !pet.isAlive()) {
                    // Remove dead pet
                    board.setPetAt(row, col, null);
                }
            }
        }
        
        // Add new pets randomly
        if (random.nextInt(100) < 20) { // 20% chance each turn
            int randomRow = random.nextInt(board.getRows());
            int randomPetType = random.nextInt(3);
            
            Pet newPet;
            switch (randomPetType) {
                case 0:
                    newPet = new Bunny();
                    break;
                case 1:
                    newPet = new Dog();
                    break;
                case 2:
                    newPet = new Hamster();
                    break;
                default:
                    newPet = new Bunny();
                    break;
            }
            
            // Place the pet in the rightmost column of the selected row
            if (board.getPetAt(randomRow, board.getCols() - 1) == null) {
                board.setPetAt(randomRow, board.getCols() - 1, newPet);
            }
        }
    }
    
    protected void plantsTakeAction() {
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                Plant plant = board.getPlantAt(row, col);
                if (plant != null && plant.isAlive()) {
                    plant.performAction(board, row, col);
                }
            }
        }
    }
    
    protected void showHelp() {
        System.out.println("\n=== Plants vs Pets Help ===");
        System.out.println("Objective: Stop pets from reaching your house (left side)!");
        System.out.println("Collect suns to buy plants.");
        System.out.println("Plants:");
        System.out.println("- Sunflower: Produces suns (50 cost)");
        System.out.println("- Peashooter: Shoots pets in its row (100 cost)");
        System.out.println("- Wallnut: Strong defense (50 cost)");
        System.out.println("- Splashy Fern: Attacks area around it (150 cost)");
        System.out.println("Pets:");
        System.out.println("- Bunny: Fast but weak");
        System.out.println("- Dog: Strong and tough");
        System.out.println("- Hamster: Very fast but weak");
        System.out.println("\nCommands: 'place', 'info', 'quit'");
    }
    
    public static void main(String[] args) {
        Game game = new Game();
        game.startGame();
    }
}