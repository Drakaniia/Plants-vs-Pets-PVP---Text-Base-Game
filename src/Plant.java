public abstract class Plant {
    protected int health;
    protected int maxHealth;
    protected int attackPower;
    protected int cost;
    protected String name;
    protected boolean isPlanted;
    
    public Plant(String name, int health, int attackPower, int cost) {
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.attackPower = attackPower;
        this.cost = cost;
        this.isPlanted = false;
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
    
    public int getCost() {
        return cost;
    }
    
    public String getName() {
        return name;
    }
    
    public boolean isPlanted() {
        return isPlanted;
    }
    
    // Setters
    public void setPlanted(boolean planted) {
        this.isPlanted = planted;
    }
    
    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }
    
    public boolean isAlive() {
        return health > 0;
    }
    
    public abstract void performAction(GameBoard board, int row, int col);
    
    @Override
    public String toString() {
        return name + " [HP: " + health + "/" + maxHealth + "]";
    }
}