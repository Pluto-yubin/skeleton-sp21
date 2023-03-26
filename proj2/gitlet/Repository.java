package gitlet;

import gitlet.model.Commit;
import gitlet.model.Constant;
import gitlet.model.FileTree;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public static final File INDEX_FILE = join(GITLET_DIR, Constant.INDEX);
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

    private static List<File> getFileListFromArgs(String ...filenames) {
        List<File> files = new LinkedList<>();
        for (String filename : filenames) {
            if (Constant.STAR.equals(filename)) {
                File[] fileList = join(CWD).listFiles();
                if (Objects.nonNull(fileList)) {
                    files.addAll(Arrays.stream(fileList).collect(Collectors.toList()));
                }
                break;
            } else {
                files.add(Utils.join(CWD, filename));
            }
        }
        return files;
    }
    /**
     * Add files to the staging area.
     */
    public static void add(String ...filenames) throws IOException {
        try {
            List<File> files = getFileListFromArgs(filenames);
            if (INDEX_FILE.exists()) {
                addToStaging(files, Utils.readObject(INDEX_FILE, FileTree.class));
            } else {
                INDEX_FILE.createNewFile();
                addToStaging(files, null);
            }
        } catch (NullPointerException ignored) {}
    }

    private static void addToStaging(List<File> files, FileTree root) throws IOException {
        if (Objects.nonNull(root)) {
            List<String> children = new LinkedList<>();
            for (File file : files) {
                FileTree fileTree = FileTreeUtil.findFileTree(file.getName(), root);

                if (Objects.isNull(fileTree)) {
                    // 新文件
                    String treeCode = FileTreeUtil.createFileTreeRecursively(file);
                    children.add(treeCode);
                    root.setChildren(children);
                } else if (fileTree.isNotFile() && Objects.nonNull(file.listFiles())) {
                    // 如果是文件夹，添加其子文件
                    addToStaging(Arrays.asList(file.listFiles()), fileTree);
                } else {
                    // 先判断文件内容是否变化再判断是否更新
                    String stagingFileSha1Code = Utils.sha1(Utils.readContentsAsString(file));
                    if (!fileTree.getFileContentSha1Code().equals(stagingFileSha1Code)) {
                        FileTreeUtil.removeFileTree(Utils.sha1(fileTree), root);
                        root.getChildren().add(FileTreeUtil.createFileTreeRecursively(file));
                    }
                }
            }
        } else {
            root = new FileTree();
            for (File file : files) {
                root.getChildren().add(FileTreeUtil.createFileTreeRecursively(file));
            }
        }
        Utils.writeObject(INDEX_FILE, root);
    }

}
