package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

class GildedRoseTest {

    @Test
    void foo() {
        Item[] items = new Item[] { new Item("foo", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("foo", app.items[0].name);
    }
    
    @Test
    void SulfurasTest() {
        Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 0, 80) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(80, app.items[0].quality);
        assertEquals(0, app.items[0].sellIn);
    }
    
    @RepeatedTest(value = 9, name = "{displayName} {currentRepetition}/{totalRepetitions}")
    void AllTest(RepetitionInfo repetitionInfo) {
        Item[] items = new Item[] {
                new Item("+5 Dexterity Vest", 10, 20), //
                new Item("Aged Brie", 2, 0), //
                new Item("Elixir of the Mongoose", 5, 7), //
                new Item("Sulfuras, Hand of Ragnaros", 0, 80), //
                new Item("Sulfuras, Hand of Ragnaros", -1, 80),
                new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
                new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
                new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
                // this conjured item does not work properly yet
                new Item("Conjured Mana Cake", 3, 6), 
                
                new Item("Elixir2", 0, 7),
                new Item("Elixir3", 5, 0),
                new Item("Aged Brie", -1, 2),
                new Item("Aged Brie", -1, 50),
                new Item("Backstage passes to a TAFKAL80ETC concert", 5, 47),
                new Item("Backstage passes to a TAFKAL80ETC concert", -1, 48)
                };

        GildedRose app = new GildedRose(items);
        app.updateQuality();
//        assertEquals(80, app.items[0].quality);
//        assertEquals(0, app.items[0].sellIn);
        assertAll("",
    			() -> assertEquals(80, app.items[repetitionInfo.getCurrentRepetition() - 1].quality, "quality"),
    			() -> assertEquals(0, app.items[repetitionInfo.getCurrentRepetition() - 1].sellIn, "sellIn")
    		);
    }

}
