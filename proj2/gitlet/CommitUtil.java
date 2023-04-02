package gitlet;

import gitlet.model.Commit;
import gitlet.model.FileTree;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

/**
 * @package: gitlet
 * @className: CommitUtil
 * @author: zyb
 * @description: Operate commit
 * @date: 2023/3/5 19:11
 * @version: 1.0
 */
public class CommitUtil {

    public static Commit commit(String message) throws IOException {
        Commit commit = new Commit(message, new Date());
        FileTree tree = Utils.readObject(Repository.INDEX_FILE, FileTree.class);
        commit.setTree(Utils.persistObject(tree));
        Repository.INDEX_FILE.deleteOnExit();
        return commit;
    }

    public static String initCommit() throws IOException {
        Commit commit = new Commit("initial commit", new Date(0));
        return Utils.persistObject(commit);
    }

    public static boolean fileIsCommitted(File file, Commit commit) {
        if (commit.getTree() == null || file.isDirectory()) {
            return false;
        }

        FileTree root = Utils.readObject(commit.getTree(), FileTree.class);
        FileTree tree = FileTreeUtil.findFileTree(file.getName(), root);
        String sha1 = Utils.sha1(Utils.readContentsAsString(file));
        return sha1.equals(tree.getFileContentSha1Code());
    }

    public static boolean fileIsNotCommit(File file, Commit commit) {
        return !fileIsCommitted(file, commit);
    }
}