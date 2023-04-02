package gitlet;

import java.io.IOException;
import java.util.Arrays;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) throws IOException {
        // TODO: what if args is empty?
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                Repository.init();
                break;
            case "add":
                Repository.add(Arrays.copyOfRange(args, 1, args.length));
                break;
            case "Commit":
                if (args.length == 1) {
                    throw new GitletException("Please enter a commit message.");
                }
                Repository.commit(args[1]);
                break;
            // TODO: FILL THE REST IN
            default:
                throw new GitletException();

        }
    }
}
