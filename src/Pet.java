public abstract class Pet {
    protected int health;
    protected int maxHealth;
    protected int attackPower;
    protected int speed; // How many turns it takes to move one space
    protected int row;
    protected int col;
    protected String name;
    protected int moveCooldown; // Countdown for movement
    protected boolean isAlive;
    
    public Pet(String name, int health, int attackPower, int speed) {
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.attackPower = attackPower;
        this.speed = speed;
        this.moveCooldown = speed; // Start with a full cooldown
        this.isAlive = true;
    }
    
    // Getters
    public int getHealth() {
        return health;
    }
    
    public int getMaxHealth() {
        return maxHealth;
    }
    
    public int getAttackPower() {
        return attackPower;
    }
    
    public int getSpeed() {
        return speed;
    }
    
    public int getRow() {
        return row;
    }
    
    public int getCol() {
        return col;
    }
    
    public String getName() {
        return name;
    }
    
    public boolean isAlive() {
        return isAlive;
    }
    
    // Setters
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }
    
    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
        if (this.health <= 0) {
            this.isAlive = false;
        }
    }
    
    public boolean canMove() {
        return moveCooldown <= 0;
    }
    
    public void decrementMoveCooldown() {
        if (moveCooldown > 0) {
            moveCooldown--;
        }
    }
    
    public void resetMoveCooldown() {
        moveCooldown = speed;
    }
    
    public abstract void performAction(GameBoard board, Player player);
    
    @Override
    public String toString() {
        return name + " [HP: " + health + "/" + maxHealth + "]";
    }
}