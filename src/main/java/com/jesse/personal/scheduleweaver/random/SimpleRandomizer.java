package com.jesse.personal.scheduleweaver.random;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

//
//https://www.baeldung.com/java-random-list-element
// Make sure all items are covered in a week (or 2 weeks)
// And then repeat -> So ongoing cache of what has been touched
// Anything that is worth repeating will require repeats of items ok? hrm....
// Or a map that counts how many times the item has been selected! then pops off list
//

@Component
public class SimpleRandomizer implements Randomizer {

    Random random = new Random();
//        random.setSeed(0); // TODO: think? // Might need to set for testing (internal)

    // Needs a Cache of all the items it uses
    // TODO: do this cache test


    @Override
    public String extractRandomItem(List<String> listToRandomize) {
        Integer randomIndex = random.nextInt(listToRandomize.size());
        return listToRandomize.get(randomIndex);
    }


}
