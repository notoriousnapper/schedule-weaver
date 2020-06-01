package com.jesse.personal.scheduleweaver.random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

// TODO: Add randomizer check for rules or sth here
// TODO: Get appropriate randomizer, then fix this
// HARDCODED ATM
@Component
public class RandomizerService {

    @Autowired
    List<Randomizer> randomizers; // Use appropriate one by rule

    public String getRandomItem(List itemList) {
        return randomizers.get(0).extractRandomItem(itemList);
    }
}
