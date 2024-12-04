package edu.vanier.spaceshooter.support;

public class LevelController {
    /* Speed controller */
    public int speedSpaceShip = 2;

    public int invaderShootingFrequency = 1;

    public int getInvaderShootingFrequency() {
        return invaderShootingFrequency;
    }

    public void setInvaderShootingFrequency() {
        this.invaderShootingFrequency -= 0.1;
    }

    public int speedInvader = 1;

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
    public final String boss_Enemy = "/invaders/enemyBlue5.png";

    public int health_small_Invader = 1;
    public int health_medium_Invader = 1;
    public int health_big_Invader = 1;
    public int health_boss_Invader = 1;

    public String getSmall_Enemy() {
        return small_Enemy;
    }

    public String getMedium_Enemy() {
        return medium_Enemy;
    }

    public String getBig_Enemy() {
        return big_Enemy;
    }

    public String getBoss_Enemy() {
        return boss_Enemy;
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

    public int getHealth_boss_Invader() {
        return health_boss_Invader;
    }

    public void setHealth_boss_Invader(int health_boss_Invader) {
        this.health_boss_Invader = health_boss_Invader;
    }

    /* Missile values */
    public final String redMissile_1 = "/missile/laserRed06.png";
    public final String redMissile_2 = "/missile/laserRed16.png";
    public final String blueMissile_1 = "/missile/laserBlue06.png";
    public final String blueMissile_2 = "/missile/laserBlue16.png";
    public final String greenMissile_1 = "/missile/laserGreen12.png";
    public final String greenMissile_2 = "/missile/laserGreen13.png";
    public int health_missile = 1;
    public int numberOfGuns = 2;

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

    /* Obstacle values */
    public final String obstacle_1 = "/obstacles/meteorGrey_big1.png";
    public final String obstacle_2 = "/obstacles/meteorGrey_big2.png";
    public final String obstacle_3 = "/obstacles/meteorGrey_big3.png";
    public final String obstacle_4 = "/obstacles/meteorGrey_big4.png";
    public int obstacle_health = 2;

    public String getObstacle_1() {
        return obstacle_1;
    }

    public String getObstacle_2() {
        return obstacle_2;
    }

    public String getObstacle_3() {
        return obstacle_3;
    }

    public String getObstacle_4() {
        return obstacle_4;
    }

    public int getObstacle_health() {
        return obstacle_health;
    }

    public void setObstacle_health(int obstacle_health) {
        this.obstacle_health = obstacle_health;
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
}




