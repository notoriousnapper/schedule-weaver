package com.jesse.personal.scheduleweaver.random;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SimpleRandomizerTest {

    // In 3 given seeds (until we get better tests)
    // Should this be in the interface level tests? Can you test interfaces?
    //

    @Test
    public void testReturnsRandomItem() {

        // Advanced Algorithm will try to balance across list.size of item!
        SimpleRandomizer randomizer = new SimpleRandomizer();
        List<String> names = Arrays.asList("Jesse", "Ren", "Pear", "Fruit");
        List<String> randomlyExtractedNames = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            randomlyExtractedNames.add(randomizer.extractRandomItem(
                    names
            ));
        }

        // Every output is a subset (item) of the original set
        randomlyExtractedNames.forEach(
                name -> Assert.assertTrue(names.contains(name))
        );

        // Unique test to this randomizer - should not repeat more than 2x in a row
        // i = first
        // j = second in a row
        // k = current item
        HashMap hashMap = new HashMap();
        names.forEach(name -> hashMap.put(name, 0));
        String i = null;
        String j = null;
        for (String name : randomlyExtractedNames) {
            String k = name; // current

            // Skip first two cases
            if ((i != null) && (j != null)) {
                if (j.equals(i) && j.equals(k)) {
                    Assert.fail();
                }
            }

            // Shift left so the previous's previous are aligned
            i = j;
            j = k;

            System.out.println(String.format("i:%s j:%s k:%s", i,j,k));

            Assert.assertTrue(names.contains(name));
        }
    }

//    @Test
    // Advanced test for advanced randomizer
    public void testDoesNotRepeatItems_ThreeTimesConsecutively() {

        // Advanced Algorithm will try to balance across list.size of item!
        SimpleRandomizer randomizer = new SimpleRandomizer();
        List<String> names = Arrays.asList("Jesse", "Ren", "Pear", "Fruit");
        List<String> randomlyExtractedNames = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            randomlyExtractedNames.add(randomizer.extractRandomItem(
                    names
            ));
        }

        // Unique test to this randomizer - should not repeat more than 2x in a row
        // i = first
        // j = second in a row
        // k = current item
        HashMap hashMap = new HashMap();
        names.forEach(name -> hashMap.put(name, 0));
        String i = null;
        String j = null;
        for (String name : randomlyExtractedNames) {
            String k = name; // current

            // Skip first two cases
            if ((i != null) && (j != null)) {
                if (j.equals(i) && j.equals(k)) {
                    Assert.fail();
                }
            }

            // Shift left so the previous's previous are aligned
            i = j;
            j = k;

            System.out.println(String.format("i:%s j:%s k:%s", i,j,k));

            Assert.assertTrue(names.contains(name));
        }
    }

    @Test
    public void testHandlesNullOrEmptyItems() {

        SimpleRandomizer randomizer = new SimpleRandomizer();
        List<String> names = Arrays.asList(null, null);
        List<String> randomlyExtractedNames = new ArrayList<>();
        List<String> nullList = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            randomlyExtractedNames.add(randomizer.extractRandomItem(
                    names
            ));
        }

        // Every output is a subset (item) of the original set
        randomlyExtractedNames.forEach(
                name -> Assert.assertTrue(names.contains(name))
        );

    }

}