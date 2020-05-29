package com.jesse.personal.scheduleweaver.random;

import java.util.List;

// TODOs: Eventually make string generic
// This is opinionated
public interface Randomizer {

    String extractRandomItem(List<String> listToRandomize);
}
