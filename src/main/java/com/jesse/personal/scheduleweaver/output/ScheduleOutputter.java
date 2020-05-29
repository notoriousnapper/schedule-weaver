package com.jesse.personal.scheduleweaver.output;

import com.jesse.personal.scheduleweaver.random.RandomizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@Component
public class ScheduleOutputterRunner implements CommandLineRunner {

    @Autowired
    RandomizerService randomizerService;

    @Autowired
    SchedulePrinter schedulePrinter;

    public static void printList(List<List<String>> output) {
        System.out.println(String.format("Date: %s: ", new Date().getDay()));
    }

    @Override
    public void run(String... args) throws Exception {

        // Gather all resource lists to print
        // Order that you "onboard" is the order it is printed out
        // Load into memory or output into file as needed
        // Print via Template (Weekday Template)
        // Has day of today


        try {

            File file = ResourceUtils.getFile("classpath:groups/gtd_actions.txt");
//            file.getParentFile().
//            System.out.println("File Found : " + file.exists());

            List<String> gtdGroup = new ArrayList();

            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                gtdGroup.add(data);
            }
            myReader.close();


            // WRITE TO A FILE
            File outputFile = new File("output.txt");
            if (!file.exists()) {
                file.createNewFile();
            }

            PrintWriter fileWriter = new PrintWriter(outputFile);


            // For ALL GROUPS
            // Extract item and print
            // TODO: Extract ALL "text files"---> eventually make it by onboarding
            List<List<String>> groups = new ArrayList();
            groups.add(gtdGroup);

            // MAIN LINE
            groups.forEach(g -> fileWriter.println(randomizerService.getRandomItem(g)));

            fileWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }
}
