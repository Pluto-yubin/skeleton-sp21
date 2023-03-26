package gitlet;

import gitlet.model.Commit;

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
        // TODO: add staging file into commit
        return Utils.persistObject(commit);
    }

    public static String initCommit() throws IOException {
        Commit commit = new Commit("initial commit", new Date(0));
        return Utils.persistObject(commit);
    }
}