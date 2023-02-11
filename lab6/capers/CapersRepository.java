package capers;

import java.io.File;
import java.io.IOException;

/**
 * A repository for Capers
 *
 * @author zyb
 * The structure of a Capers Repository is as follows:
 * <p>
 * .capers/ -- top level folder for all persistent data in your lab12 folder
 * - dogs/ -- folder containing all of the persistent data for dogs
 * - story -- file containing the current story
 * <p>
 */
public class CapersRepository {
    /**
     * Current Working Directory.
     */
    static final File CWD = new File(System.getProperty("user.dir"));

    private static final String CAPERS_FILE_NAME = ".capers";
    private static final String STORY_FILE_NAME = "story";

    /**
     * Main metadata folder.
     */
    static final File CAPERS_FOLDER = Utils.join(CWD, CAPERS_FILE_NAME);
    private static File STORY_FILE;
    /**
     * key -> dog's name, value -> pointer to dog
     */

    /**
     * Does required filesystem operations to allow for persistence.
     * (creates any necessary folders or files)
     * Remember: recommended structure (you do not have to follow):
     * <p>
     * .capers/ -- top level folder for all persistent data in your lab12 folder
     * - dogs/ -- folder containing all of the persistent data for dogs
     * - story -- file containing the current story
     */
    public static void setupPersistence() throws IOException {
        if (CAPERS_FOLDER.mkdir()) {
            STORY_FILE = Utils.join(CAPERS_FOLDER, STORY_FILE_NAME);
            STORY_FILE.createNewFile();
        } else {
            STORY_FILE = Utils.join(CAPERS_FOLDER.getAbsolutePath(), STORY_FILE_NAME);
        }
    }

    /**
     * Appends the first non-command argument in args
     * to a file called `story` in the .capers directory.
     *
     * @param text String of the text to be appended to the story
     */
    public static void writeStory(String text) {
        String story = Utils.readContentsAsString(STORY_FILE);
        if (story.isEmpty()) {
            Utils.writeContents(STORY_FILE, text);
        } else {
            String context =String.format("%s\n%s", story, text);
            text = story + "\n" + text;
            Utils.writeContents(STORY_FILE, text);
        }
        System.out.println(text);
    }

    /**
     * Creates and persistently saves a dog using the first
     * three non-command arguments of args (name, breed, age).
     * Also prints out the dog's information using toString().
     */
    public static void makeDog(String name, String breed, int age) throws IOException {
        Dog dog = new Dog(name, breed, age);
        dog.saveDog();
        System.out.println(dog);
    }

    /**
     * Advances a dog's age persistently and prints out a celebratory message.
     * Also prints out the dog's information using toString().
     * Chooses dog to advance based on the first non-command argument of args.
     *
     * @param name String name of the Dog whose birthday we're celebrating.
     */
    public static void celebrateBirthday(String name) throws IOException {
        Dog dog = Dog.fromFile(name);
        dog.haveBirthday();
        dog.saveDog();

    }
}
