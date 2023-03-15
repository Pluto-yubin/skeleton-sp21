package gitlet.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Represents a gitlet commit object.
 * does at a high level.
 * ------------------------------------------------------------
 * tree aa35961ecc027bfb04f761c2c4d849c95b76d080
 * parent 51cb7f468dc89926ed0d40a19d806b43cb24130b
 * author Pluto-yubin <1113940328@qq.com> 1676122332 +0800
 * committer Pluto-yubin <1113940328@qq.com> 1676122332 +0800
 * ------------------------------------------------------------
 *
 * @author zyb
 */
public class Commit implements Serializable {

    private static final String TIMESTAMP_FORMAT = "00:00:00 UTC, Thursday, 1 January 1970";

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("HH:mm:ss UTC, H, dd MM yy", Locale.ENGLISH);

    /**
     * The message of this Commit.
     */
    private String message;

    /**
     * Code commit time
     */
    private Date commitTime;

    /**
     * The hashcode of last commit
     */
    private String parent;

    /**
     * The hashcode of file-tree
     */
    private String tree;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getTree() {
        return tree;
    }

    public void setTree(String tree) {
        this.tree = tree;
    }

    public Commit() {}

    public Commit(String message, Date commitTime) {
        this.message = message;
        this.commitTime = commitTime;
    }
}
