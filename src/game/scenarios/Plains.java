package game.scenarios;

import game.combat.Combat;
import game.creature.Enemy;
import game.generators.EnemyGenerator;
import game.creature.Player;
import game.state.GameWorld;

public class Plains {

    GameWorld gameworld;

    public Plains(GameWorld gameworld) {
        this.gameworld = gameworld;
    }

    Player player = gameworld.player;
    Enemy enemy = gameworld.currentEnemy;

    public void go() {

        enemy = gameworld.currentEnemy;

        if (!gameworld.fromInventory){
            enemy = EnemyGenerator.plainsEnemy();
            gameworld.currentEnemy = enemy;
        }

        gameworld.ui.mainTextArea.setText("You are now in the PLAINS. It is [WEATHER]. You see a " +
                enemy.getName().toUpperCase() + "." +
                "\nYou decide to:");

        gameworld.ui.choice1.setText("GET CLOSER to this creature");
        gameworld.ui.choice2.setText("SEARCH for other enemies");
        gameworld.ui.choice3.setText("GET BACK on the road");
        gameworld.ui.choice4.setText("");

        gameworld.nextPosition1 = "PLAINS_FIGHT_CHOOSE";
        gameworld.nextPosition2 = "PLAINS";
        gameworld.nextPosition3 = "FIGHT_CHOOSE";
        gameworld.nextPosition4 = "";
    }

    public void fightChoose(){

        enemy = GameWorld.currentEnemy;
        gameworld.ui.mainTextArea.setText("The " + enemy.getRace().toUpperCase() + " looks nervous.\n" +
                "What do you do now?");

        gameworld.ui.choice1.setText("ATTACK");
        gameworld.ui.choice2.setText("LEAVE it be");
        gameworld.ui.choice3.setText("");
        gameworld.ui.choice4.setText("");

        gameworld.nextPosition1 = "PLAINS_FIGHT";
        gameworld.nextPosition2 = "PLAINS"; //FIGHT CHOOSE ? szukanie nowego przeciwnika
        gameworld.nextPosition3 = "";
        gameworld.nextPosition4 = "";
    }

    public void fight(){

        int attack1 = 0;
        int attack2 = 0;

        if(!gameworld.fromInventory){
            enemy = GameWorld.currentEnemy;
            attack1 = Combat.attack(player, enemy);
            attack2 = Combat.attack(enemy, player);

            gameworld.prevDmgDealt = attack1;
            gameworld.prevDmgTaken = attack2;
        }
        else {
            attack1 = gameworld.prevDmgDealt;
            attack2 = gameworld.prevDmgTaken;
        }

        if (player.getHp() < 1) {

            gameworld.ui.mainTextArea.setText("The "+ enemy.getName().toUpperCase() + " hurt you for "
                    + attack2 + " DMG.\n" +
                    "You DIED.\n" +
                    "GAME OVER");

            gameworld.ui.choice1.setText("Start a NEW GAME");
            gameworld.ui.choice2.setText("");
            gameworld.ui.choice3.setText("");
            gameworld.ui.choice4.setText("");

            gameworld.nextPosition1 = "BEGIN";
            gameworld.nextPosition2 = "";
            gameworld.nextPosition3 = "";
            gameworld.nextPosition4 = "";
        }
        else if (enemy.getHp() < 1) {    // DEAD ENEMY
            String lvlUp = "";
            float receivedExp = enemy.getExp()/20;
            if (receivedExp + player.expWithoutLevel()>=1) lvlUp = "You feel more confident. You're pretty sure your THREAT RATING just went up!";
            gameworld.ui.mainTextArea.setText("You dealt " + attack1 + " DMG.\n" +
                    "The " + enemy.getRace().toUpperCase() + " is DEAD.\n" +
                    "You WON the fight!\n" +
                    "\nYou think you can sell parts from the DEAD " + enemy.getRace().toUpperCase() +
                    " for about " + enemy.getMoney() + " GOLD COINS.\n\n" +
                    lvlUp);
            player.addExp(receivedExp);
            player.addMoney(enemy.getMoney());

            if (!gameworld.fromInventory){
                // dead enemy does not deal dmg
                player.addHp(attack2);
            }
            if (gameworld.fromInventory){
                //no double rewards
                player.lowerExp(receivedExp);
                player.lowerMoney(enemy.getMoney());
            }

            gameworld.ui.choice1.setText("LOOK for MORE");
            gameworld.ui.choice2.setText("Get BACK on the ROAD");
            gameworld.ui.choice3.setText("");
            gameworld.ui.choice4.setText("");

            gameworld.nextPosition1 = "PLAINS";
            gameworld.nextPosition2 = "FIGHT_CHOOSE";
            gameworld.nextPosition3 = "";
            gameworld.nextPosition4 = "";
        }                                   //END DEAD ENEMY
        else {
            gameworld.ui.mainTextArea.setText("You dealt " + attack1 + " DMG.\n" +
                    "The "+ enemy.getName().toUpperCase() + " hurt you for " + attack2 + " DMG.\n" +
                    "This " + enemy.getRace().toUpperCase() + " has " + enemy.getHp() + " HP now.");

            gameworld.ui.choice1.setText("ATTACK");
            gameworld.ui.choice2.setText("RUN for your life");
            gameworld.ui.choice3.setText("");
            gameworld.ui.choice4.setText("");

            gameworld.nextPosition1 = "PLAINS_FIGHT";
            gameworld.nextPosition2 = "PLAINS";
            gameworld.nextPosition3 = "";
            gameworld.nextPosition4 = "";
        }
        gameworld.vm.updateCurrentHPLabel(player.getHp()); //UPDATE HP
        player.updateMaxHp();
    }
}

