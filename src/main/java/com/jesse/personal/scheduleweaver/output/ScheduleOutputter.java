package com.jesse.personal.scheduleweaver.output;

import com.jesse.personal.scheduleweaver.random.RandomizerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// TODO: Gather lists with meta data where folder group = group type
// TODO: Rename functions better
// TODO: Important - make sure its the right level, else it will grab more folders

/***
 // Gather all resource lists to print
 // Gather all resource lists to print
 // Order that you "onboard" is the order it is printed out
 // Load into memory or output into file as needed
 // Print via Template (Weekday Template)
 // Has day of today


 // STEPS:
 // Load all files into memory as a structure
 // Print into one output file after altering what is needed
 // Design for a db system too
 // Right now -> Can be file-based (for backup purposes)
 // Tests for corruption? should never be written

 // Order that you "onboard" is the order it is printed out
 // Load into memory or output into file as needed
 // Print via Template (Weekday Template)
 // Has day of today
 // STEPS:
 // Load all files into memory as a structure
 // Print into one output file after altering what is needed
 // Design for a db system too
 // Right now -> Can be file-based (for backup purposes)
 // Tests for corruption? should never be written
 */


@Component
@Slf4j
public class ScheduleOutputter implements CommandLineRunner {

    private static final String PREBORDER = "================================================================================";
    private static final String POSTBORDER = "================================================================================";

    @Autowired
    private FileConfigurer fileConfigurer;

    @Autowired
    private RandomizerService randomizerService;

    @Autowired
    SchedulePrinter schedulePrinter;

    @Override
    public void run(String... args) throws Exception {

        try {

            File file = new File(fileConfigurer.getPath());
            String absolutePath = file.getAbsolutePath();

            try (Stream<Path> paths = Files.walk(Paths.get(absolutePath))) {
                List<ActionList> actionLists = paths
                        .filter(Files::isRegularFile)
                        .map((path) -> {

                            String parentFolderName = path.getParent().getFileName().toString();
                            String fileName = path.getFileName().toString();
                            log.info("Loading file/folder: {}/{}", parentFolderName, path.getFileName());
                            return new ActionList(
                                    parentFolderName,
                                    fileName,
                                    readAndReturnStructuredActionItemList(path));
                        })
                        .collect(Collectors.toList());

                // MAIN LINE
                printRandomizedDataToFile(actionLists, "output.txt"); // TODO: set in environment variabl
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO: Handle bettek
    // TODO: // Code Review Dominic
    private static List<String> readAndReturnStructuredActionItemList(Path path) {
        String filePath = path.toString();
        String folderName = path.getParent().getFileName().toString();

        List<String> actionItemList = new ArrayList();

        try {
            File file = ResourceUtils.getFile(filePath);

            Scanner myReader = new Scanner(file);

            // Absorb all lines into memory
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                actionItemList.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            actionItemList = new ArrayList<>();
            actionItemList.add("FILE NOT FOUND: " + filePath);
            e.printStackTrace();
        } finally {
            return actionItemList;
        }
    }

    // TODO: Should actually just absorb data into a template, and then print out the combined
    // TODO: Close more efficiently
    // template and output
    private void printRandomizedDataToFile(List<ActionList> groups, String outputFilePath) throws IOException {

        // Secure output file
        File outputFile = new File(outputFilePath);
        if (!outputFile.exists()) {
            outputFile.createNewFile();
        }
        PrintWriter fileWriter = new PrintWriter(outputFile);


        // TODO: Extract this/ use scheduleprinter to interact with this
        // TODO: Generate exact template in list of lines + starter lines
        // TODO: Incorporate w/ META data
        List finalTemplate = generateTemplateFromData(groups);
        finalTemplate.forEach(g -> fileWriter.println(g));
        fileWriter.close();
    }

    private List<ActionList> generateTemplateFromData(List<ActionList> groups) {
        // Default
        List finalTemplate = new ArrayList();
        finalTemplate.add("🚀");
        finalTemplate.add((new Date()).toString());
        finalTemplate.add("\n\n");

        groups.forEach(actionList -> finalTemplate.add(

                PREBORDER +
                        String.format("GROUP: %s\nNAME: %s\n ACTION: %s\n\n",
                                actionList.getGroup(),
                                actionList.getName(),
                                randomizerService.getRandomItem(actionList.getContent())
                        )
                        +
                        POSTBORDER
                )
        );
        finalTemplate.add("Good Luck!");

        return finalTemplate;
    }

}
