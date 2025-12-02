import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// --- 1. Item Classes ---

class Weapon {
    private String name;
    private int attackModifier;
    private double specialEffectChance; 
    private String specialEffect; 

    public Weapon(String name, int modifier, double chance) {
        this.name = name;
        this.attackModifier = modifier;
        this.specialEffectChance = chance;
        this.specialEffect = chance > 0 ? (Math.random() > 0.5 ? "Stun" : "Freeze") : "None";
    }

    public String getName() { return name; }
    public int getAttackModifier() { return attackModifier; }
    public double getSpecialEffectChance() { return specialEffectChance; }
    public String getSpecialEffect() { return specialEffect; }

    @Override
    public String toString() {
        String effectStr = specialEffectChance > 0 ? String.format(" (%.0f%% chance to %s)", specialEffectChance * 100, specialEffect) : "";
        return String.format("%s (+%d ATK)%s", name, attackModifier, effectStr);
    }
}

class Armor {
    private String name;
    private int defenseModifier;

    public Armor(String name, int modifier) {
        this.name = name;
        this.defenseModifier = modifier;
    }

    public String getName() { return name; }
    public int getDefenseModifier() { return defenseModifier; }

    @Override
    public String toString() {
        return String.format("%s (+%d DEF)", name, defenseModifier);
    }
}

// --- 2. Abstract Base Class (Abstraction) ---
abstract class AbstractCharacter {
    private String name;
    private int health;
    private int maxHealth;
    private int baseAttack;
    private int baseDefense;
    private boolean isDefending;
    private boolean isInvulnerable;
    private int specialCooldown;
    private int healCooldown;
    private int specialCooldownTime;

    public AbstractCharacter(String name, int health, int baseAttack, int specialCooldownTime) {
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.baseAttack = baseAttack;
        this.baseDefense = 0;
        this.isDefending = false;
        this.isInvulnerable = false;
        this.specialCooldown = 0;
        this.healCooldown = 0; 
        this.specialCooldownTime = specialCooldownTime;
    }

    // Getters and Setters (Encapsulation)
    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getBaseAttack() { return baseAttack; }
    public int getBaseDefense() { return baseDefense; }
    public int getSpecialCooldown() { return specialCooldown; }
    public int getHealCooldown() { return healCooldown; }
    public int getSpecialCooldownTime() { return specialCooldownTime; }
    public boolean isInvulnerable() { return isInvulnerable; }
    public boolean isDefeated() { return health <= 0; }

    public void setHealth(int health) { 
        this.health = Math.min(health, maxHealth); 
        this.health = Math.max(this.health, 0);
    }
    public void setMaxHealth(int maxHealth) { this.maxHealth = maxHealth; }
    public void setSpecialCooldown(int specialCooldown) { this.specialCooldown = specialCooldown; }
    public void setHealCooldown(int healCooldown) { this.healCooldown = healCooldown; }
    public void setBaseDefense(int baseDefense) { this.baseDefense = baseDefense; }

    // Core Actions
    public int basicAttack() {
        return baseAttack;
    }
    
    public int getTotalDefense() {
        return baseDefense;
    }

    public void heal() {
        if (healCooldown > 0) {
            System.out.println("-> Heal is on cooldown (" + healCooldown + " turns remaining).");
            return;
        }
        int healAmount = (int) (maxHealth * 0.15); 
        setHealth(this.health + healAmount);
        this.isInvulnerable = true; 
        this.healCooldown = 1; // 1-turn cooldown
        System.out.println("-> " + name + " heals for " + healAmount + " HP and becomes **invulnerable** for the next turn!");
    }

    public void defend() {
        this.isDefending = true;
        System.out.println("-> " + name + " takes a **defensive stance**.");
    }
    
    public abstract void specialAttack(AbstractCharacter target);

    // Damage handler
    public void takeDamage(int damage) {
        if (isInvulnerable) {
            System.out.println("-> " + name + " shrugs off the attack (Invulnerable)!");
            return;
        }
        
        int totalDefense = getTotalDefense();
        double reductionFactor = 1.0;
        if (isDefending) {
            reductionFactor *= 0.5;
        }
        
        int effectiveDamage = (int) (damage * reductionFactor) - totalDefense;
        if (effectiveDamage < 1) effectiveDamage = 1;

        this.health -= effectiveDamage;
        if (this.health < 0) this.health = 0;
        System.out.println("-> " + name + " takes **" + effectiveDamage + "** damage. Remaining HP: " + this.health);
    }
    
    // Reset status effects
    public void endTurn() {
        this.isDefending = false;
        this.isInvulnerable = false;
        if (specialCooldown > 0) {
            specialCooldown--;
        }
        if (healCooldown > 0) {
            healCooldown--;
        }
    }
}

// --- 3. Player Character (Abstract Base) ---
abstract class PlayerCharacter extends AbstractCharacter {
    private String charType;
    private Weapon equippedWeapon;
    private Armor equippedArmor; 

    public PlayerCharacter(String name, String charType, int baseHealth, int baseAttack, int specialCooldownTime) {
        super(name, baseHealth, baseAttack, specialCooldownTime);
        this.charType = charType;
        this.equippedWeapon = new Weapon("Fists", 0, 0); 
        this.equippedArmor = new Armor("Clothes", 0); 
    }

    @Override
    public int getTotalDefense() {
        return super.getBaseDefense() + equippedArmor.getDefenseModifier();
    }

    @Override
    public int basicAttack() {
        int totalAttack = super.basicAttack() + equippedWeapon.getAttackModifier();
        System.out.println(getName() + " uses Basic Attack with " + equippedWeapon.getName() + ".");
        return totalAttack;
    }
    
    public String getCharType() { return charType; }
    public Weapon getEquippedWeapon() { return equippedWeapon; }
    public Armor getEquippedArmor() { return equippedArmor; }
    public void setEquippedWeapon(Weapon equippedWeapon) { this.equippedWeapon = equippedWeapon; }
    public void setEquippedArmor(Armor equippedArmor) { this.equippedArmor = equippedArmor; }
}

// --- 4. Concrete Subclasses (Inheritance & Polymorphism) ---

class Knight extends PlayerCharacter {
    public Knight(String name) {
        super(name, "Knight", 1200, 150, 2); 
        setBaseDefense(50);
    }

    @Override
    public void specialAttack(AbstractCharacter target) {
        if (getSpecialCooldown() == 0) {
            int damage = (int) (basicAttack() * 1.5); 
            System.out.println("\n**KNIGHT'S SHIELD BASH!** " + getName() + " slams their shield into " + target.getName() + ".");
            target.takeDamage(damage);
            setSpecialCooldown(getSpecialCooldownTime()); 
        } else {
            System.out.println("-> Special Attack is on cooldown (" + getSpecialCooldown() + " turns remaining).");
        }
    }
}

class Mage extends PlayerCharacter {
    public Mage(String name) {
        super(name, "Mage", 900, 250, 3);
    }

    @Override
    public void specialAttack(AbstractCharacter target) {
        if (getSpecialCooldown() == 0) {
            int damage = (int) (basicAttack() * 2.5); 
            System.out.println("\n**MAGE'S FIREBALL!** " + getName() + " casts a massive spell on " + target.getName() + ".");
            target.takeDamage(damage);
            setSpecialCooldown(getSpecialCooldownTime()); 
        } else {
            System.out.println("-> Special Attack is on cooldown (" + getSpecialCooldown() + " turns remaining).");
        }
    }
}

class Archer extends PlayerCharacter {
    public Archer(String name) {
        super(name, "Archer", 1000, 200, 3);
    }

    @Override
    public void specialAttack(AbstractCharacter target) {
        if (getSpecialCooldown() == 0) {
            int damage = (int) (basicAttack() * 0.8); 
            System.out.println("\n**ARCHER'S BARRAGE!** " + getName() + " fires a volley of arrows.");
            target.takeDamage(damage);
            target.takeDamage(damage);
            setSpecialCooldown(getSpecialCooldownTime()); 
        } else {
            System.out.println("-> Special Attack is on cooldown (" + getSpecialCooldown() + " turns remaining).");
        }
    }
}

class Assassin extends PlayerCharacter {
    public Assassin(String name) {
        super(name, "Assassin", 800, 280, 2);
    }

    @Override
    public void specialAttack(AbstractCharacter target) {
        if (getSpecialCooldown() == 0) {
            int damage = (int) (basicAttack() * 3.0); 
            System.out.println("\n**ASSASSIN'S BACKSTAB!** " + getName() + " delivers a critical strike.");
            target.takeDamage(damage);
            setSpecialCooldown(getSpecialCooldownTime()); 
        } else {
            System.out.println("-> Special Attack is on cooldown (" + getSpecialCooldown() + " turns remaining).");
        }
    }
}

class Dwarf extends PlayerCharacter {
    public Dwarf(String name) {
        super(name, "Dwarf", 1500, 100, 3);
        setBaseDefense(75);
    }

    @Override
    public void specialAttack(AbstractCharacter target) {
        if (getSpecialCooldown() == 0) {
            int damage = (int) (basicAttack() * 1.0); 
            System.out.println("\n**DWARF'S STONE SKIN!** " + getName() + " heals and increases defense!");
            heal();
            setBaseDefense(getBaseDefense() + 50);
            setSpecialCooldown(getSpecialCooldownTime()); 
        } else {
            System.out.println("-> Special Attack is on cooldown (" + getSpecialCooldown() + " turns remaining).");
        }
    }
}


// --- 5. Monster Class (Inheritance) ---
class Monster extends AbstractCharacter {
    public Monster(String name, int health, int baseAttack) {
        super(name, health, baseAttack, 3);
        setBaseDefense(20);
    }

    @Override
    public void specialAttack(AbstractCharacter target) {
        if (getSpecialCooldown() == 0) {
            int damage = (int) (basicAttack() * 1.8); 
            System.out.println("-> **MONSTER RAGE!** " + getName() + " unleashes a massive blow.");
            target.takeDamage(damage);
            setSpecialCooldown(getSpecialCooldownTime()); 
        } else {
            System.out.println("-> " + getName() + " uses Basic Attack.");
            target.takeDamage(basicAttack());
        }
    }
}

// --- 6. Main Game Engine (FIX APPLIED HERE) ---
class FinalProjectGame { // Removed 'public' to fix file naming error
    private PlayerCharacter player;
    private Scanner scanner;
    private final int MAX_LEVELS = 15;
    private long startTime;
    private Random random = new Random();

    // Arrays updated with more names
    private final String[] WEAPON_PREFIXES = {"Rusty", "Iron", "Sharp", "Ancient", "Obsidian", "Mythic", "Vicious", "Grim", "Shadow", "Holy"};
    private final String[] WEAPON_SUFFIXES = {"Blade", "Dagger", "Axe", "Saber", "Reaver", "Longsword", "Cutter", "Katana"};
    private final String[] ARMOR_PREFIXES = {"Leather", "Plate", "Scaled", "Mythril", "Steel", "Draconic", "Worn", "Reinforced"};
    private final String[] ARMOR_SUFFIXES = {"Vest", "Cuirass", "Chestplate", "Tunic", "Mail", "Robe"};

    public FinalProjectGame() {
        this.scanner = new Scanner(System.in);
    }

    public void startGame() {
        System.out.println("👑 Welcome to The Lost Crown! 👑");
        
        boolean keepPlaying = true;
        while (keepPlaying) {
            try {
                player = selectCharacter();
                System.out.println("\nWelcome, " + player.getName() + " the " + player.getCharType() + "! Your quest begins now.");
                
                startTime = System.currentTimeMillis(); 
                
                if (runLevels()) {
                    // End Game Scoring
                    long endTime = System.currentTimeMillis();
                    double timeTaken = (endTime - startTime) / 1000.0;
                    System.out.printf("\n*** THE LOST CROWN RETRIEVED! ***\n");
                    System.out.printf("You finished the game in %.2f seconds! A true hero!\n", timeTaken);
                    System.out.println("The lost crown: \n\n" + getCrownArt());
                }
                
                // Retry Option
                System.out.print("\nDo you wish to start a new adventure? (Y/N): ");
                String retry = scanner.nextLine().toUpperCase();
                if (!retry.equals("Y")) {
                    keepPlaying = false;
                }
            } catch (Exception e) {
                System.out.println("\n🚨 A critical error occurred: " + e.getMessage() + "\nRestarting game setup.");
            }
        }
        System.out.println("Thank you for playing!");
    }
    
    private PlayerCharacter selectCharacter() {
        while (true) {
            System.out.println("\nSelect your character class:");
            System.out.println("1. Knight (Balanced, High Def, CD: 2)");
            System.out.println("2. Mage (High ATK, Low Def, CD: 3)");
            System.out.println("3. Archer (Multi-hit, Balanced, CD: 3)");
            System.out.println("4. Assassin (Very High ATK, Lowest HP, CD: 2)");
            System.out.println("5. Dwarf (Highest HP & Def, Lowest ATK, CD: 3)");
            System.out.print("Enter choice (1-5): ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); 
                System.out.print("Enter your character's name: ");
                String name = scanner.nextLine();

                switch (choice) {
                    case 1: return new Knight(name);
                    case 2: return new Mage(name);
                    case 3: return new Archer(name);
                    case 4: return new Assassin(name);
                    case 5: return new Dwarf(name);
                    default:
                        System.out.println("-> Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("🚨 Invalid input. Please enter a number (try-catch block used).");
                scanner.nextLine(); 
            }
        }
    }
    
    private boolean runLevels() {
        for (int level = 1; level <= MAX_LEVELS; level++) {
            System.out.println("\n========================================");
            System.out.println("--- Entering Level " + level + " of " + MAX_LEVELS + " ---");
            System.out.println("========================================");
            
            // 1. Story Introduction
            System.out.println(getLevelStory(level)); 

            // 2. Battle
            Monster[] monsters;
            if (level == MAX_LEVELS) {
                monsters = new Monster[]{new Monster("King Chaus", 3500, 300)}; 
            } else {
                monsters = generateMonsters(level); 
            }
            
            if (!startBattle(monsters)) {
                System.out.println("GAME OVER! You were defeated at Level " + level + ".");
                return false; 
            }
            
            // 3. Post-Battle Rewards and Quiz (If not final level)
            if (level < MAX_LEVELS) {
                Weapon newWeapon = generateWeapon(level);
                Armor newArmor = generateArmor(level);
                
                // Weapon choice
                System.out.println("\n🎉 You found a new weapon: **" + newWeapon.toString() + "**");
                System.out.println("Currently equipped: " + player.getEquippedWeapon().toString());
                System.out.print("Equip weapon? (Y/N): ");
                if (scanner.nextLine().toUpperCase().equals("Y")) {
                    player.setEquippedWeapon(newWeapon);
                    System.out.println("-> Weapon equipped.");
                } else {
                    System.out.println("-> Weapon discarded.");
                }

                // Armor choice
                System.out.println("\n🎉 You found new armor: **" + newArmor.toString() + "**");
                System.out.println("Currently equipped: " + player.getEquippedArmor().toString());
                System.out.print("Equip armor? (Y/N): ");
                 if (scanner.nextLine().toUpperCase().equals("Y")) {
                    player.setEquippedArmor(newArmor);
                    player.setMaxHealth(player.getMaxHealth() + 50); 
                    player.setHealth(player.getHealth() + 50);
                    System.out.println("-> Armor equipped. Max Health increased by 50!");
                } else {
                    System.out.println("-> Armor discarded.");
                }
                
                runQuiz(level);
            }
        }
        return true;
    }

    private boolean startBattle(Monster[] monsters) {
        System.out.println("\n*** BATTLE STARTED against " + monsters.length + " monster(s)! ***");
        
        while (!player.isDefeated() && Arrays.stream(monsters).anyMatch(m -> !m.isDefeated())) {
            
            // 1. Player Turn
            System.out.println("\n--- Player Turn: " + player.getName() + " (HP: " + player.getHealth() + "/" + player.getMaxHealth() + ") ---");
            System.out.println("Current Defense: " + player.getTotalDefense() + " | Weapon: " + player.getEquippedWeapon().getName());
            
            List<Monster> liveMonsters = Arrays.stream(monsters).filter(m -> !m.isDefeated()).collect(Collectors.toList());
            if (liveMonsters.isEmpty()) break;
            
            Monster target = liveMonsters.get(0);

            System.out.println("Target: " + target.getName() + " (HP: " + target.getHealth() + ")");
            System.out.print("Choose move (1:Attack, 2:Heal [CD: " + player.getHealCooldown() + "], 3:Defend, 4:Special [CD: " + player.getSpecialCooldown() + "]): ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); 
                
                switch (choice) {
                    case 1: 
                        target.takeDamage(player.basicAttack()); 
                        break;
                    case 2: 
                        player.heal(); 
                        break; 
                    case 3: 
                        player.defend(); 
                        break;
                    case 4: 
                        player.specialAttack(target); 
                        break; 
                    default:
                        System.out.println("-> Invalid move, attacking instead.");
                        target.takeDamage(player.basicAttack());
                }
                
                // Check for weapon special effects
                if (player.getEquippedWeapon().getSpecialEffectChance() > random.nextDouble() && !target.isDefeated()) {
                    System.out.println("✨ " + target.getName() + " is " + player.getEquippedWeapon().getSpecialEffect() + "ed! They skip their next turn.");
                    target.setSpecialCooldown(target.getSpecialCooldownTime() + 1); 
                }
                
            } catch (InputMismatchException e) {
                System.out.println("🚨 Invalid input. Forcing Attack.");
                scanner.nextLine();
                target.takeDamage(player.basicAttack());
            }

            if (player.isDefeated()) return false;
            
            // 2. Monster Turn
            for (Monster monster : monsters) {
                if (monster.isDefeated()) continue;
                
                System.out.println("\n--- Monster Turn: " + monster.getName() + " (HP: " + monster.getHealth() + ") ---");
                
                // Simple Monster AI
                if (monster.getSpecialCooldown() == 0 && monster.getHealth() > (monster.getMaxHealth() * 0.5)) {
                    monster.specialAttack(player);
                } else {
                    monster.defend();
                }
                
                monster.endTurn();
                if (player.isDefeated()) return false;
            }
            
            // 3. End Turn Cleanup
            player.endTurn();
        }
        
        System.out.println("\n*** BATTLE WON! ***");
        return true; 
    }

    private void runQuiz(int level) {
        System.out.println("\n🧠 A Quiz Master appears! Answer correctly for a reward!");
        
        String question;
        String answer;

        // Unique Quiz Questions for each level (1-14)
        switch (level) {
            case 1: 
                question = "Which OOP principle hides the internal implementation details from the user? (A: Abstraction, B: Inheritance): ";
                answer = "A";
                break;
            case 2:
                question = "What keyword is necessary to create a custom exception class in Java? (A: throws, B: extends): ";
                answer = "B";
                break;
            case 3:
                question = "If a parent class reference holds an object of a subclass (e.g., AbstractCharacter c = new Knight()), what is this dynamic behavior called? (A: Polymorphism, B: Encapsulation): ";
                answer = "A";
                break;
            case 4:
                question = "What is the standard naming convention for Java variables? (A: PascalCase, B: camelCase): ";
                answer = "B";
                break;
            case 5:
                question = "Which access modifier allows access only within the same class? (A: default, B: private): ";
                answer = "B";
                break;
            case 6:
                question = "Which block is used to ensure a cleanup code runs whether an exception occurs or not? (A: finally, B: catch): ";
                answer = "A";
                break;
            case 7:
                question = "Can a class implement multiple interfaces in Java? (A: Yes, B: No): ";
                answer = "A";
                break;
            case 8:
                question = "What does the 'super' keyword refer to in a constructor? (A: The subclass, B: The superclass): ";
                answer = "B";
                break;
            case 9:
                question = "Which type of polymorphism is achieved at runtime through method overriding? (A: Compile-time, B: Run-time): ";
                answer = "B";
                break;
            case 10:
                question = "What must a concrete subclass do if it extends an abstract class? (A: Define all abstract methods, B: Define only abstract classes): ";
                answer = "A";
                break;
            case 11:
                question = "In Java, arrays are objects. (A: True, B: False): ";
                answer = "A";
                break;
            case 12:
                question = "Which OOP principle ensures that fields are kept private and accessed only through public methods (getters/setters)? (A: Encapsulation, B: Abstraction): ";
                answer = "A";
                break;
            case 13:
                question = "What kind of block is used to handle checked exceptions in Java? (A: try-catch, B: if-else): ";
                answer = "A";
                break;
            case 14:
                question = "Is a Java interface required to define constructors? (A: Yes, B: No): ";
                answer = "B";
                break;
            default:
                // Safety net fallback
                question = "Who prepared the CS 211 Final Project Guidelines? (A: Ms. Fatima Marie P. Agdon, B: Demon King Chaus): ";
                answer = "A";
                break;
        }

        System.out.print(question);
        
        // FIX APPLIED: Reading input only once and trimming whitespace
        String response = scanner.nextLine().toUpperCase().trim();
        
        if (response.equals(answer)) {
            System.out.println("✅ Correct! You gain 100 HP!");
            player.setHealth(player.getHealth() + 100);
        } else {
            System.out.println("❌ Incorrect! You lose 50 HP for your mistake.");
            player.setHealth(player.getHealth() - 50);
            if (player.isDefeated()) {
                 System.out.println("-> " + player.getName() + " has been defeated by the Quiz Master's penalty!");
            }
        }
    }
    
    // --- Dummy/Generation Methods ---
    private Monster[] generateMonsters(int level) { 
        int baseHealth = 500 + (level * 150);
        int baseAttack = 100 + (level * 10);
        int numMonsters = level < 5 ? 1 : (level < 10 ? 2 : 3); 
        Monster[] monsters = new Monster[numMonsters];
        for (int i = 0; i < numMonsters; i++) {
            monsters[i] = new Monster("Ghoul " + (i + 1), baseHealth, baseAttack);
        }
        return monsters; 
    }
    
    private String getLevelStory(int level) { 
        if (level == MAX_LEVELS) {
            return "The final chamber opens. Before you stands **King Chaus**, guardian of the lost crown!";
        }
        return "The air is thick with old magic. Level " + level + " is guarded by ancient forces."; 
    }
    
    private Weapon generateWeapon(int level) {
        int attack = random.nextInt(level * 20) + 10;
        double chance = random.nextDouble() * 0.15; 
        String prefix = WEAPON_PREFIXES[random.nextInt(WEAPON_PREFIXES.length)];
        String suffix = WEAPON_SUFFIXES[random.nextInt(WEAPON_SUFFIXES.length)];
        return new Weapon(prefix + " " + suffix, attack, chance);
    }

    private Armor generateArmor(int level) {
        int defense = random.nextInt(level * 10) + 5;
        String prefix = ARMOR_PREFIXES[random.nextInt(ARMOR_PREFIXES.length)];
        String suffix = ARMOR_SUFFIXES[random.nextInt(ARMOR_SUFFIXES.length)];
        return new Armor(prefix + " " + suffix, defense);
    }
    
    private String getCrownArt() {
        return "       .:::.       \n"+
               "      / \\|/ \\      \n"+
               "     |@|:::|@|     \n"+
               "     \\ \\`\"'/ /     \n"+
               "======\\_v_//======\n"+
               "      / _ \\       \n"+
               "     | / \\ |      \n"+
               "     `-- --'";
    }

    public static void main(String[] args) {
        new FinalProjectGame().startGame();
    }
}