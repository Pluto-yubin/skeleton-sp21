package gitlet;

import gitlet.model.Commit;
import gitlet.model.Constant;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static gitlet.Utils.join;

/**
 * Represents a gitlet repository.
 * does at a high level.
 *
 * @author zyb
 */
public class Repository {

    /**
     * The current working directory.
     */
    public static final File CWD = new File(System.getProperty("user.dir"));

    /**
     * The .gitlet directory.
     */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    /**
     * The HEAD_FILE
     */
    public static final File HEAD_FILE = join(GITLET_DIR, Constant.HEAD);

    public static final File REFS_FILE = join(GITLET_DIR, Constant.REFS);

    /**
     * The head commit, for another word, the latest commit that user gets
     */
    private static Commit head;

    private static Commit branch;


    /**
     * The objects directory.
     */
    public static final File OBJECT_DIR = join(GITLET_DIR, "objects");

    static {
        if (HEAD_FILE.exists()) {
            String branchPath = Utils.readContentsAsString(HEAD_FILE);
            String commitCode = Utils.readContentFromFilePath(branchPath);
            File commit = Utils.getFileByHashcode(commitCode);
            head = Utils.readObject(commit, Commit.class);
            branch = head;
        }
    }

    public static void init() throws IOException {
        if (!GITLET_DIR.exists()) {
            GITLET_DIR.mkdir();
            OBJECT_DIR.mkdir();
            HEAD_FILE.createNewFile();
            Utils.writeContents(HEAD_FILE, "refs/heads/master");
            String initCommitCode = CommitUtil.initCommit();
            BranchUtil.persistBranch("master", initCommitCode);
        } else {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
        }
    }

    public static void add(String ...filenames) {
        List<File> files = new LinkedList<>();
        String path = System.getProperty("user.dir");
        try {
            for (String filename : filenames) {
                if (Constant.STAR.equals(filename)) {
                    files.addAll(List.of(Objects.requireNonNull(join(path).listFiles())));
                    break;
                } else {
                    files.add(Utils.join(path, filename));
                }
            }
        } catch (NullPointerException ignored) {}
    }

    private static void addToStaging(List<File> files) {

    }

}
