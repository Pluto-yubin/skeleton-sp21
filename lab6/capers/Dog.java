package capers;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static capers.Utils.*;

/** Represents a dog that can be serialized.
 * @author zyb
*/
public class Dog implements Serializable {

    private static final transient String DOGS_FOLDER_NAME = "dogs";
    /** Folder that dogs live in. */
    transient static final File DOG_FOLDER = Utils.join(CapersRepository.CAPERS_FOLDER, DOGS_FOLDER_NAME);

    static {
        if (!DOG_FOLDER.exists()) {
            DOG_FOLDER.mkdir();
        }
    }

    /** Age of dog. */
    private int age;
    /** Breed of dog. */
    private String breed;
    /** Name of dog. */
    private String name;

    /**
     * Creates a dog object with the specified parameters.
     * @param name Name of dog
     * @param breed Breed of dog
     * @param age Age of dog
     */
    public Dog(String name, String breed, int age) {
        this.age = age;
        this.breed = breed;
        this.name = name;
    }

    /**
     * Reads in and deserializes a dog from a file with name NAME in DOG_FOLDER.
     *
     * @param name Name of dog to load
     * @return Dog read from file
     */
    public static Dog fromFile(String name) {
        return Utils.readObject(Utils.join(DOG_FOLDER, name), Dog.class);
    }



    /**
     * Increases a dog's age and celebrates!
     */
    public void haveBirthday() {
        age += 1;
        System.out.println(toString());
        System.out.println("Happy birthday! Woof! Woof!");
    }

    /**
     * Saves a dog to a file for future use.
     */

    public void saveDog() throws IOException {
        File dog = Utils.join(DOG_FOLDER, name);
        if (!dog.exists()) {
            dog.createNewFile();
        }
        Utils.writeObject(dog, this);
    }

    @Override
    public String toString() {
        return String.format(
            "Woof! My name is %s and I am a %s! I am %d years old! Woof!",
            name, breed, age);
    }

}
