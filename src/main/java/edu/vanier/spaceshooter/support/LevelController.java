package edu.vanier.spaceshooter.support;

public class LevelController {
    /* Speed controller */
    public int speedSpaceShip = 1;

    public double smallInvaderShooting = 0.8;
    public double mediumShooting = 0.6;
    public double bigInvaderShooting = 0.4;
    public double bossInvaderShooting = 0.5;

    public double getSmallInvaderShooting() {
        return smallInvaderShooting;
    }

    public void setSmallInvaderShooting() {
        if (this.smallInvaderShooting <= 1) {
            this.smallInvaderShooting += 0.05;
        }
    }

    public double getMediumShooting() {
        return mediumShooting;
    }

    public void setMediumShooting() {
        if (this.mediumShooting <= 1) {
            this.mediumShooting += 0.05;
        }
    }

    public double getBigInvaderShooting() {
        return bigInvaderShooting;
    }

    public void setBigInvaderShooting() {
        if (this.bigInvaderShooting <= 1) {
            this.bigInvaderShooting += 0.05;
        }
    }

    public double getBossInvaderShooting() {
        return bossInvaderShooting;
    }

    public void setBossInvaderShooting() {
        if (this.bossInvaderShooting <= 1) {
            this.bossInvaderShooting += 0.05;
        }
    }

    public void increaseShooting() {
        setSmallInvaderShooting();
        setMediumShooting();
        setBigInvaderShooting();
        setBossInvaderShooting();
    }

    public int speedInvader = 0;

    public int getSpeedInvader() {
        return speedInvader;
    }

    public void setSpeedInvader(int speedInvader) {
        this.speedInvader += speedInvader;
    }

    public long lastShot = 0;
    public int animationDuration = 500;

    public int getSpeedSpaceShip() {
        return speedSpaceShip;
    }

    public long getLastShot() {
        return lastShot;
    }

    public void setLastShot(long lastShot) {
        this.lastShot = lastShot;
    }


    public void setSpeedSpaceShip(int speedSpaceShip) {
        this.speedSpaceShip += speedSpaceShip;
    }

    public int getAnimationDuration() {
        return animationDuration;
    }

    public void setAnimationDuration(int animationDuration) {
        this.animationDuration = animationDuration;
    }

    /* Score */
    public int score = 0;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    /* Enemy number */
    public int numberEnemies = 5;

    public int getNumberEnemies() {
        return numberEnemies;
    }

    public void setNumberEnemies(int numberEnemies) {
        this.numberEnemies += numberEnemies;
    }

    /* Invader values */
    public final String small_Enemy = "/invaders/enemyBlue2.png";
    public final String medium_Enemy = "/invaders/enemyBlue3.png";
    public final String big_Enemy = "/invaders/enemyBlue4.png";
    public final String boss_Enemy = "/invaders/ufoBlue.png";

    public int health_small_Invader = 1;
    public int health_medium_Invader = 2;
    public int health_big_Invader = 3;
    public int health_boss_Invader = 5;

    public String getBoss_Enemy() {
        return boss_Enemy;
    }

    public int getHealth_boss_Invader() {
        return health_boss_Invader;
    }

    public void setHealth_boss_Invader(int health_boss_Invader) {
        this.health_boss_Invader = health_boss_Invader;
    }

    public String getSmall_Enemy() {
        return small_Enemy;
    }

    public String getMedium_Enemy() {
        return medium_Enemy;
    }

    public String getBig_Enemy() {
        return big_Enemy;
    }


    public int getHealth_small_Invader() {
        return health_small_Invader;
    }

    public void setHealth_small_Invader(int health_small_Invader) {
        this.health_small_Invader = health_small_Invader;
    }

    public int getHealth_medium_Invader() {
        return health_medium_Invader;
    }

    public void setHealth_medium_Invader(int health_medium_Invader) {
        this.health_medium_Invader = health_medium_Invader;
    }

    public int getHealth_big_Invader() {
        return health_big_Invader;
    }

    public void setHealth_big_Invader(int health_big_Invader) {
        this.health_big_Invader = health_big_Invader;
    }

    /* Missile values */
    public final String redMissile_1 = "/missile/laserRed06.png";
    public final String redMissile_2 = "/missile/laserRed16.png";
    public final String blueMissile_1 = "/missile/laserBlue06.png";
    public final String blueMissile_2 = "/missile/laserBlue16.png";
    public final String greenMissile_1 = "/missile/laserGreen12.png";
    public final String greenMissile_2 = "/missile/laserGreen13.png";
    public int health_missile = 1;
    public int numberOfGuns = 6;
    public int currentGun = -1;
    public int getCurrentGun() {
        return currentGun;
    }

    public void setCurrentGun(int currentGun) {
        this.currentGun += currentGun;
    }

    public int numOfMissile = 1;

    public int getNumOfMissile() {
        return numOfMissile;
    }

    public void setNumOfMissile() {
        if (this.numOfMissile <= 20) {
            this.numOfMissile += 1;
        }
    }

    public int getNumberOfGuns() {
        return numberOfGuns;
    }

    public void setNumberOfGuns(int numberOfGuns) {
        this.numberOfGuns = numberOfGuns;
    }

    public String getRedMissile_1() {
        return redMissile_1;
    }

    public String getRedMissile_2() {
        return redMissile_2;
    }

    public String getBlueMissile_1() {
        return blueMissile_1;
    }

    public String getBlueMissile_2() {
        return blueMissile_2;
    }

    public String getGreenMissile_1() {
        return greenMissile_1;
    }

    public String getGreenMissile_2() {
        return greenMissile_2;
    }

    public int getHealth_missile() {
        return health_missile;
    }

    public void setHealth_missile(int health_missile) {
        this.health_missile = health_missile;
    }

    /* Spaceship values */
    public final String player_spaceShip = "/player/playerShip1_red.png";
    public int health_player = 3;

    public String getPlayer_spaceShip() {
        return player_spaceShip;
    }

    public int getHealth_player() {
        return health_player;
    }

    public void setHealth_player(int health_player) {
        this.health_player = health_player;
    }

    /*
    Visual Effects
     */

    public String explosionSound = "/sound_effects/explosion.mp3";
    public String nextLevel = "/sound_effects/next_level.mp3";
    public String firingSound = "/sound_effects/15640-laser_gun_shot_3.wav";
    public String explosionGif = "";

    public String getExplosionSound() {
        return explosionSound;
    }

    public String getNextLevel() {
        return nextLevel;
    }

    public String getFiringSound() {
        return firingSound;
    }

    public String getExplosionGif() {
        return explosionGif;
    }
}




