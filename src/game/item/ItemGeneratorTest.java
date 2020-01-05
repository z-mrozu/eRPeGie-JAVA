package game.item;

import java.util.ArrayList;
import java.util.Random;


public class ItemGeneratorTest {
    public static void main (String[]args) {
        Random rand = new Random();
        ArrayList testItems = ItemGenerator.items();
        Item testitem = ItemGenerator.items().get(rand.nextInt(ItemGenerator.items().size()));
        System.out.println( testitem.toString() );
        testitem = ItemGenerator.descriptor(testitem);
        System.out.println(testitem + "\n\n");

        //fin
        Item testItem1 = ItemGenerator.newItem();
        System.out.println( testItem1 );

        //priceRange
        Item testItem2 = ItemGenerator.newItemPriceRange(0, 10);
        System.out.println( testItem2 );

        //priceRange + has sword in its name
        Item testItem3 = ItemGenerator.newItemPriceRangeAndName(0, 10, "sword");
        System.out.println( testItem3 );

        //priceRange Armor
        Item testItem4 = ItemGenerator.newItemPriceRangeArmor(0, 100);
        System.out.println( testItem4 );


    }
}