package gitlet;

import gitlet.model.Commit;
import gitlet.model.FileTree;

import java.io.IOException;
import java.util.Date;

/**
 * @package: gitlet
 * @className: CommitUtil
 * @author: zyb
 * @description: Operate commit
 * @date: 2023/3/5 19:11
 * @version: 1.0
 */
public class CommitUtil {

    public static String commit(String message) throws IOException {
        Commit commit = new Commit(message, new Date());
        FileTree tree = Utils.readObject(Repository.INDEX_FILE, FileTree.class);
        commit.setTree(Utils.persistObject(tree));
        Repository.INDEX_FILE.deleteOnExit();
        return Utils.persistObject(commit);
    }

    public static String initCommit() throws IOException {
        Commit commit = new Commit("initial commit", new Date(0));
        return Utils.persistObject(commit);
    }
}