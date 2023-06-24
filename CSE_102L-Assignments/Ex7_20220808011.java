import java.util.ArrayList;
import java.util.Collections;

public class Ex7_20220808011 {
    public static void main(String[] args) {

    }
}

interface Damageable {
    void takeDamage(int damage);

    void takeHealing(int healing);

    boolean isAlive();
}

interface Caster {
    void castSpell(Damageable target);

    void learnSpell(Spell spell);
}

interface Combat extends Damageable {
    void attack(Damageable target);

    void lootWeapon(Weapon weapon);

}

interface Useable {
    int use();
}

class Spell implements Useable {
    private int minHeal;
    private int maxHeal;
    private String name;

    Spell(String name, int minHeal, int maxHeal) {
        this.maxHeal = maxHeal;
        this.minHeal = minHeal;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int heal() {
        return (int) (Math.floor(Math.random() * (maxHeal - minHeal + 1) + minHeal));
    }

    @Override
    public int use() {
        return heal();
    }
}

class Weapon implements Useable {
    private int minDamage;
    private int maxDamage;
    private String name;

    Weapon(String name, int minDamage, int maxDamage) {
        this.name = name;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int attack() {
        return (int) Math.floor(Math.random() * (maxDamage - minDamage + 1) + minDamage);
    }

    @Override
    public int use() {
        return attack();
    }
}

class Attributes {
    private int strength;
    private int intelligence;

    Attributes() {
        strength = 3;
        intelligence = 3;
    }

    Attributes(int strength, int intelligence) {
        this.strength = strength;
        this.intelligence = intelligence;
    }

    public void increaseIntelligence(int amount) {
        intelligence += amount;
    }

    public void increaseStrength(int amount) {
        strength += amount;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getStrength() {
        return strength;
    }

    @Override
    public String toString() {
        return "Attributes [Strength= " + strength + ", intelligence= " + intelligence + "]";
    }
}

abstract class Character implements Comparable<Character> {
    private String name;
    protected int level;
    protected Attributes attributes;
    protected int health;

    Character(String name, Attributes attributes) {
        this.name = name;
        this.attributes = attributes;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    abstract public void levelUp();

    @Override
    public String toString() {
        return name + "LvL: " + level + "–" + attributes;
    }

    @Override
    public int compareTo(Character o) {
        return Integer.compare(this.level, o.level);
    }
}

abstract class PlayableCharacter extends Character implements Damageable {
    private boolean inParty;
    private Party party;

    PlayableCharacter(String name, Attributes attributes) {
        super(name, attributes);
    }

    public boolean isInParty() {
        return inParty;
    }

    public void joinParty(Party party) {
        try {
            if (!isInParty()) {
                party.addCharacter(this);
                inParty = true;
                party = this.party;
            }
        } catch (PartyLimitReachedException | AlreadyInPartyException ex) {
            System.out.println(ex);
        }
    }

    public void quitParty() {
        try {
            if (isInParty()) {
                party.removeCharacter(this);
                inParty = false;
                party = null;
            }
        } catch (CharacterIsNotInpartyException ex) {
            System.out.println(ex);
        }
    }
}

abstract class nonPlayableCharacter extends Character {
    nonPlayableCharacter(String name, Attributes attributes) {
        super(name, attributes);
    }
}

class Merchant extends nonPlayableCharacter {

    Merchant(String name) {
        super(name, new Attributes(0, 0));
    }

    @Override
    public void levelUp() {

    }
}

class Skeleton extends nonPlayableCharacter implements Combat {

    Skeleton(String name, Attributes attributes) {
        super(name, attributes);
    }

    public void lootWeapon(Weapon weapon) {

    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
    }

    @Override
    public void takeHealing(int healing) {
        health -= healing;
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

    @Override
    public void attack(Damageable target) {
        int totalStats = attributes.getIntelligence() + attributes.getStrength();
        target.takeDamage(totalStats);
    }

    @Override
    public void levelUp() {
        level += 1;
        attributes.increaseStrength(1);
        attributes.increaseIntelligence(1);
    }
}

class Warrior extends nonPlayableCharacter implements Combat {

    private Useable weapon;

    Warrior(String name) {
        super(name, new Attributes(4, 2));
        this.health = 35;
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
    }

    @Override
    public void takeHealing(int healing) {
        health += healing;
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

    @Override
    public void attack(Damageable target) {
        target.takeDamage(attributes.getStrength() + weapon.use());
    }

    @Override
    public void lootWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    @Override
    public void levelUp() {
        attributes.increaseStrength(2);
        attributes.increaseIntelligence(1);
        level += 1;
    }
}

class Cleric extends PlayableCharacter implements Caster {

    private Useable spell;

    Cleric(String name) {
        super(name, new Attributes(2, 4));
        health = 25;
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
    }

    @Override
    public void takeHealing(int healing) {
        health += healing;
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

    @Override
    public void castSpell(Damageable target) {
        int total = attributes.getIntelligence() + spell.use();
        target.takeHealing(total);
    }

    @Override
    public void learnSpell(Spell spell) {
        this.spell = spell;
    }

    @Override
    public void levelUp() {
        level += 1;
        attributes.increaseIntelligence(2);
        attributes.increaseStrength(1);
    }
}

class Paladin extends PlayableCharacter implements Combat, Caster {

    private Useable weapon;
    private Useable spell;

    Paladin(String name) {
        super(name, new Attributes());
        health = 30;
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
    }

    @Override
    public void takeHealing(int healing) {
        health += healing;
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

    @Override
    public void castSpell(Damageable target) {
        int total = attributes.getIntelligence() + spell.use();
        target.takeHealing(total);
    }

    @Override
    public void learnSpell(Spell spell) {
        this.spell = spell;
    }

    @Override
    public void attack(Damageable target) {
        int totalStats = attributes.getStrength() + weapon.use();
        target.takeDamage(totalStats);
    }

    @Override
    public void lootWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    @Override
    public void levelUp() {
        if (level % 2 == 0) {
            attributes.increaseStrength(2);
            attributes.increaseIntelligence(1);
            level += 1;
        } else {
            attributes.increaseStrength(1);
            attributes.increaseIntelligence(2);
            level += 1;
        }
    }
}

class Party {
    private final int partyLimit = 8;
    private ArrayList<PlayableCharacter> fighters = new ArrayList<>();
    private ArrayList<PlayableCharacter> healers = new ArrayList<>();

    private int mixedCount = 0;

    public void addCharacter(PlayableCharacter character) throws PartyLimitReachedException, AlreadyInPartyException {
        if ((fighters.size() + healers.size() - mixedCount) >= partyLimit) {
            throw new PartyLimitReachedException(character.getName());
        }
        if (fighters.contains(character) || healers.contains(character)) {
            throw new AlreadyInPartyException(character.getName());
        }
        if (character instanceof Combat) {
            fighters.add(character);
        }
        if (character instanceof Caster) {
            healers.add(character);
        }
        if (character instanceof Paladin) {
            mixedCount++;
        }
    }

    public void removeCharacter(PlayableCharacter character) throws CharacterIsNotInpartyException {
        if (!fighters.contains(character) && !healers.contains(character)) {
            throw new CharacterIsNotInpartyException(character.getName());
        }
        if (character instanceof Paladin) {
            mixedCount--;
        }
        if (character instanceof Combat) {
            fighters.remove(character);
        }
        if (character instanceof Caster) {
            healers.remove(character);
        }
    }

    public void partyLevelUp() {
        for (int i = 0; i < fighters.size(); i++) {
            fighters.get(i).levelUp();
        }

        for (int i = 0; i < healers.size(); i++) {
            if (!(healers.get(i) instanceof Paladin)) {
                healers.get(i).levelUp();
            }
        }
    }

    @Override
    public String toString() {
        String s = "";
        ArrayList<Integer> levels = new ArrayList<>();
        for (int i = 0; i < healers.size(); i++) {
            levels.add(healers.get(i).level);
        }
        for (int i = 0; i < fighters.size(); i++) {
            levels.add(fighters.get(i).level);
        }
        Collections.sort(levels);
        for (int i = 0; i < levels.size(); i++) {
            for (int j = 0; j < fighters.size(); j++) {
                if (levels.get(i) == fighters.get(j).level) {
                    s += fighters.get(j).toString();
                } else if (levels.get(i) == healers.get(j).level) {
                    s += healers.get(j).toString();
                }
            }
        }

        return s;
    }

}


class Barrel implements Damageable {
    private int health = 30;
    private int capacity = 10;

    public void explode() {
        System.out.println("Explodes");
    }

    public void repair() {
        System.out.println("Repairing");
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            explode();
        }
    }

    @Override
    public void takeHealing(int healing) {
        health += healing;
        repair();
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }
}

class TrainingDummy implements Damageable {

    private int health = 25;

    @Override
    public void takeDamage(int damage) {
        health -= damage;
    }

    @Override
    public void takeHealing(int healing) {
        health += healing;
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }
}

class PartyLimitReachedException extends Exception {
    PartyLimitReachedException(String message) {
        super(message);
    }
}

class AlreadyInPartyException extends Exception {
    AlreadyInPartyException(String message) {
        super(message);
    }
}

class CharacterIsNotInpartyException extends Exception {
    CharacterIsNotInpartyException(String message) {
        super(message);
    }
}
