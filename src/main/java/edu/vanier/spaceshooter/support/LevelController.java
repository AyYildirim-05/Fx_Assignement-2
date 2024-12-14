package edu.vanier.spaceshooter.support;

import edu.vanier.spaceshooter.models.SpaceShip;
import javafx.scene.media.MediaView;

/**
 * Class that contains all the values for the game/
 */
public class LevelController {
    /* Speed controller */
    public double speedSpaceShip = 1;
    public double speedMissiles = 3;

    public double getSpeedMissiles() {
        return speedMissiles;
    }

    public double smallInvaderShooting = 0.8;
    public double mediumShooting = 0.6;
    public double bigInvaderShooting = 0.4;
    public double bossInvaderShooting = 0.5;

    public void setSmallInvaderShooting() {
        if (this.smallInvaderShooting <= 1) {
            this.smallInvaderShooting += 0.2;
        }
    }

    public void setMediumShooting() {
        if (this.mediumShooting <= 1) {
            this.mediumShooting += 0.2;
        }
    }

    public void setBigInvaderShooting() {
        if (this.bigInvaderShooting <= 1) {
            this.bigInvaderShooting += 0.2;
        }
    }

    public void setBossInvaderShooting() {
        if (this.bossInvaderShooting <= 1) {
            this.bossInvaderShooting += 0.2;
        }
    }

    public void increaseShooting() {
        setSmallInvaderShooting();
        setMediumShooting();
        setBigInvaderShooting();
        setBossInvaderShooting();
    }

    public double speedInvader = 1;

    public void setSpeedInvader(double speedInvader) {
        this.speedInvader += speedInvader;
    }
    public int animationDuration = 500;

    public double getSpeedSpaceShip() {
        return speedSpaceShip;
    }

    public void setSpeedSpaceShip(double speedSpaceShip) {
        this.speedSpaceShip += speedSpaceShip;
    }

    public int getAnimationDuration() {
        return animationDuration;
    }

    /* Score */
    public int score = 0;

    public int getScore() {
        return score;
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

    public int getHealth_medium_Invader() {
        return health_medium_Invader;
    }

    public int getHealth_big_Invader() {
        return health_big_Invader;
    }

    /* Missile values */
    public final String redMissile_1 = "/missile/laserRed06.png";
    public final String redMissile_2 = "/missile/laserRed16.png";
    public final String blueMissile_1 = "/missile/laserBlue06.png";
    public final String greenMissile_1 = "/missile/laserGreen12.png";
    public final String greenMissile_2 = "/missile/laserGreen13.png";
    public int health_missile = 1;
    public int numberOfGuns = 7;
    public int currentGun = -1;
    public int getCurrentGun() {
        return currentGun;
    }

    public void setCurrentGun(int currentGun) {
        this.currentGun += currentGun;
    }

    public int getNumberOfGuns() {
        return numberOfGuns;
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

    public int getHealth_missile() {
        return health_missile;
    }


    /* Spaceship values */
    public final String player_spaceShip1 = "/player/playerShip1_red.png";
    public final String player_spaceShip2 = "/player/playerShip2_red.png";
    public final String player_spaceShip3 = "/player/playerShip3_red.png";

    public String getPlayer_spaceShip1() {
        return player_spaceShip1;
    }


    /**
     * Method that changes the sprite of the spaceship as the game stages progress.
     * @param stage the current stage number
     * @return the sprite string that the player object needs to use.
     */
    public String setPlayerSprite(int stage) {
        if (stage <= 1 ) {
            return player_spaceShip1;
        } else if (stage == 2) {
            return player_spaceShip2;
        } else {
            return player_spaceShip3;
        }
    }

    public int getHealth_player1() {
        return health_player;
    }

    public int health_player = 3;

    /*
    Visual Effects
     */
    public String nextLevel = "/sound_effects/next_level.mp3";
    private MediaView next;
    private PlayingSound soundClass;

    /**
     * Method that plays the next level audio.
     * @param spaceShip the sprite instance that plays the audio
     */
    public void nextLevel(SpaceShip spaceShip) {
        if (soundClass == null) {
            soundClass = new PlayingSound();
        }
        soundClass.playSound(next, nextLevel, 0.8);
    }

}




