package org.Tests;

public class Main {
    public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {
            boolean innerLoopBreak = false; // Flag to control the inner loop
            for (int j = 0; j < 5; j++) {
                System.out.println(i * j);
                if (i * j == 1) {
                    innerLoopBreak = true;
                    break; // Break out of the inner loop
                }
            }
            if (innerLoopBreak) {
                // Break the inner loop but continue the outer loop
                continue;
            }
        }
    }
}
