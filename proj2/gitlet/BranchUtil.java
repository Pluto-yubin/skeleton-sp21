package gitlet;

import gitlet.model.Constant;

import java.io.File;
import java.io.IOException;

/**
 * @package: gitlet
 * @className: BranchUtil
 * @author: zyb
 * @description: jobs about branch
 * @date: 2023/3/5 19:42
 * @version: 1.0
 */
public class BranchUtil {
    private static File refs;

    private static String HEADS = "heads";

    static {
        if (Repository.GITLET_DIR.exists()) {
            refs = Utils.join(Repository.GITLET_DIR, Constant.REFS);
            if (!refs.exists()) {
                refs.mkdir();
            }
        }
    }

    /**
     * persist branch info into file
     *
     * @param name       branch name
     * @param commitCode the hashcode of the latest commit
     * @throws IOException
     */
    public static void persistBranch(String name, String commitCode) throws IOException {
        File heads = Utils.join(refs, HEADS);
        if (!heads.exists()) {
            heads.mkdir();
        }

        File branchFile = Utils.join(heads, name);
        if (!branchFile.exists()) {
            branchFile.createNewFile();
            Utils.writeContents(branchFile, commitCode);
        }


    }


}