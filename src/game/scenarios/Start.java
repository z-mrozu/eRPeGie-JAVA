package game.scenarios;

import game.creature.Player;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Start {

    public void begin(Player player) throws InterruptedException {
        Scanner input = new Scanner(System.in);

        System.out.print("\nHi there. What's your NAME?\n> ");
        String savename = input.nextLine();
        savename = savename.toUpperCase();
        System.out.print("\n" + savename + ", right? [Y/N]\n> ");
        String answer = input.nextLine();
        answer = answer.toLowerCase();
        while (!answer.toLowerCase().startsWith("y")) {
            System.out.print("\nWell then, what is it?\n> ");
            savename = input.nextLine();
            savename = savename.toUpperCase();
            System.out.print("\n" + savename + "? [Y/N]\n> ");
            answer = input.nextLine();
            answer = answer.toLowerCase();
        }  // savename - do wczytywania save'ow DO ZAIMPLEMENTOWANIA

        player.setSavename(savename);
        System.out.println("\nThat's not your NAME.");

        TimeUnit.MILLISECONDS.sleep(2000); // czeka 2 sekundy
        player.setSTATE("TOWN");
        System.out.println(new String(new char[70]).replace("\0", "\r\n"));
    }

    public void description(Player player) {

        System.out.print("\nYour NAME is [NAME]. It is currently [DAY/WEATHER]. You are a [TRAIT], [TRAIT] [AGE] [GENDER].\n" +
                "You have a fondness for [HOBBY] and are an ASPIRING [JOB]. You like to [HOBBY] but are NOT VERY GOOD AT IT. \n" +
                "You also enjoy KILLING THINGS sometimes.\n"); // generator postaci DO ZAIMPLEMENTOWANIA
    }
}