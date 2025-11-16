import java.util.Random;

public class Demonstration extends Game {
    private Random random;
    
    public Demonstration() {
        super();
        this.random = new Random();
        // Set initial suns to make demonstration more interesting
        this.player.addSuns(50); // Start with some extra suns for the demo
    }

    public void startDemonstration() {
        System.out.println("Welcome to Plants vs Pets Demonstration!");
        System.out.println("Watch an automated demonstration of strategic gameplay.");
        System.out.println("The computer will make intelligent moves to show you how to play effectively.");
        System.out.println("Observe how pets approach plants and battles unfold!");
        System.out.println("Press Enter to begin the demo...");
        try {
            System.in.read();
        } catch (Exception e) {
            // If there's an issue with input, just continue
        }

        // Main game loop - continue for at least 20 turns to make game longer
        while (!player.isGameOver() && gameRunning && player.getGameTurn() < 30) {
            // Display current state
            displayGameState();

            // Make intelligent decisions (automated gameplay)
            makeIntelligentDecisions();

            // Generate suns from Sunflowers
            generateSuns();

            // Pets take actions (move and attack)
            movePets();

            // Plants take actions (attack)
            plantsTakeAction();

            // Continue game until player loses
            // Don't end the game just because there are no pets on the board temporarily
            // Win condition is removed for demonstration purposes - let the game run longer
            // Only end if pets reach the house

            // Increment turn
            player.incrementTurn();

            // Add a small delay to make the game readable in demo mode
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                // Handle interruption
            }
        }

        // Game over logic
        if (player.hasWon()) {
            System.out.println("\nDemonstration finished! The garden was successfully defended!");
            System.out.println("Game won on turn " + player.getGameTurn());
        } else if (!gameRunning) {
            System.out.println("\nDemonstration finished! The game was quit.");
        } else if (player.getGameTurn() >= 30) {
            System.out.println("\nDemonstration finished! This was a long strategic battle.");
            System.out.println("Player survived " + player.getGameTurn() + " turns!");
        } else {
            System.out.println("\nDemonstration finished! The pets reached the house in this scenario.");
            System.out.println("Game Over!");
        }

        System.out.println("Thanks for watching the Plants vs Pets demonstration!");
    }

    private void makeIntelligentDecisions() {
        // First, check for immediate threats (pets in column 0)
        boolean immediateThreat = false;
        for (int row = 0; row < board.getRows(); row++) {
            if (board.getPetAt(row, 0) != null) {
                immediateThreat = true;
                break;
            }
        }

        // If there's an immediate threat, prioritize defense
        if (immediateThreat && player.canAfford(new Wallnut())) {
            // Try to place a Wallnut to block the immediate threat
            for (int row = 0; row < board.getRows(); row++) {
                if (board.getPetAt(row, 0) != null && board.getPlantAt(row, 0) == null) {
                    if (player.canAfford(new Wallnut())) {
                        board.setPlantAt(row, 0, new Wallnut());
                        player.spendSuns(new Wallnut().getCost());
                        System.out.println("Demonstration: Placed Wallnut at (" + row + ",0) to defend immediate threat");
                        return; // Place one plant per turn
                    }
                }
            }
        }

        // Place plants in strategic positions to create a defensive line
        // Start by placing some basic defense in early game
        if (player.getGameTurn() < 8) {
            // Early game: place sunflowers to generate income
            // Look for empty spots in the back columns (cols 6-8)
            for (int row = 0; row < board.getRows(); row++) {
                for (int col = 6; col < board.getCols(); col++) {
                    if (board.getPlantAt(row, col) == null && player.canAfford(new Sunflower())) {
                        board.setPlantAt(row, col, new Sunflower());
                        player.spendSuns(new Sunflower().getCost());
                        System.out.println("Demonstration: Early game - Placed Sunflower at (" + row + "," + col + ")");
                        return; // Place one plant per turn
                    }
                }
            }
        }

        // Mid-game strategy: build defensive lines
        if (player.getGameTurn() >= 5) {
            // Look for rows with pets and place defensive plants in front of them
            for (int row = 0; row < board.getRows(); row++) {
                // Check how many pets are in this row
                int petsInRow = 0;
                int rightmostPetCol = -1;

                for (int col = 0; col < board.getCols(); col++) {
                    if (board.getPetAt(row, col) != null) {
                        petsInRow++;
                        rightmostPetCol = col;
                    }
                }

                if (petsInRow > 0 && rightmostPetCol != -1) {
                    // Place a defensive plant in front of the rightmost pet
                    int plantCol = Math.max(0, rightmostPetCol - 1);

                    if (board.getPlantAt(row, plantCol) == null) {
                        // If we can afford a Wallnut, use it
                        if (player.canAfford(new Wallnut())) {
                            board.setPlantAt(row, plantCol, new Wallnut());
                            player.spendSuns(new Wallnut().getCost());
                            System.out.println("Demonstration: Placed Wallnut at (" + row + "," + plantCol + ") as defense in row " + row);
                            return; // Place one plant per turn
                        }
                        // Otherwise try a Peashooter if there's a pet in the same row
                        else if (player.canAfford(new Peashooter())) {
                            board.setPlantAt(row, plantCol, new Peashooter());
                            player.spendSuns(new Peashooter().getCost());
                            System.out.println("Demonstration: Placed Peashooter at (" + row + "," + plantCol + ") to attack pets in row " + row);
                            return; // Place one plant per turn
                        }
                    }
                }
            }
        }

        // Continue generating income
        // Look for open spots that are safe (not in the same row as immediate pets)
        for (int row = 0; row < board.getRows(); row++) {
            // Check if it's safe to place a sunflower in this row
            boolean safeToPlaceSunflower = true;
            for (int col = 0; col < 5; col++) { // Only check the first half of the board
                if (board.getPetAt(row, col) != null) {
                    safeToPlaceSunflower = false;
                    break;
                }
            }

            // Place sunflowers in safe rows, in back columns
            for (int col = 6; col < board.getCols(); col++) {
                if (safeToPlaceSunflower && board.getPlantAt(row, col) == null && player.canAfford(new Sunflower())) {
                    board.setPlantAt(row, col, new Sunflower());
                    player.spendSuns(new Sunflower().getCost());
                    System.out.println("Demonstration: Placed Sunflower at (" + row + "," + col + ") for income");
                    return; // Place one plant per turn
                }
            }
        }

        // Place offensive plants like Peashooters where they can be effective
        for (int row = 0; row < board.getRows(); row++) {
            // Check if there are pets in this row that can be attacked
            boolean petsInRow = false;
            int firstPetCol = -1;
            for (int col = 0; col < board.getCols(); col++) {
                if (board.getPetAt(row, col) != null) {
                    petsInRow = true;
                    firstPetCol = col;
                    break;
                }
            }

            if (petsInRow) {
                // Try to place a Peashooter before the first pet (so it can attack)
                for (int col = 0; col < firstPetCol; col++) {
                    if (board.getPlantAt(row, col) == null && player.canAfford(new Peashooter())) {
                        board.setPlantAt(row, col, new Peashooter());
                        player.spendSuns(new Peashooter().getCost());
                        System.out.println("Demonstration: Placed Peashooter at (" + row + "," + col + ") to attack pets in row " + row);
                        return; // Place one plant per turn
                    }
                }
            }
        }

        // Use Splashy Ferns strategically when multiple pets are close together
        if (player.canAfford(new SplashyFern())) {
            for (int row = 0; row < board.getRows(); row++) {
                for (int col = 0; col < board.getCols(); col++) {
                    // Count how many pets would be affected by a Splashy Fern at this position
                    int petsInArea = 0;
                    for (int r = Math.max(0, row - 1); r <= Math.min(board.getRows() - 1, row + 1); r++) {
                        for (int c = Math.max(0, col - 1); c <= Math.min(board.getCols() - 1, col + 1); c++) {
                            if (board.getPetAt(r, c) != null) {
                                petsInArea++;
                            }
                        }
                    }

                    if (petsInArea >= 2 && board.getPlantAt(row, col) == null) {
                        board.setPlantAt(row, col, new SplashyFern());
                        player.spendSuns(new SplashyFern().getCost());
                        System.out.println("Demonstration: Placed Splashy Fern at (" + row + "," + col + ") to attack multiple pets (" + petsInArea + " in area)");
                        return; // Place one plant per turn
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Demonstration demo = new Demonstration();
        demo.startDemonstration();
    }
}