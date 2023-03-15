package gitlet;

import gitlet.model.Blob;
import gitlet.model.Constant;
import gitlet.model.FileTree;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @package: gitlet
 * @className: FileTreeUtil
 * @author: zyb
 * @description: TODO
 * @date: 2023/3/6 13:44
 * @version: 1.0
 */
public class FileTreeUtil {

    public static String createFileTree(File file) throws IOException {
        FileTree tree = new FileTree(file);
        if (Objects.nonNull(file.listFiles())) {
            for (File subFile : file.listFiles()) {
                tree.getChildren().add(createFileTree(subFile));
            }
        }

        return Utils.persistObject(tree);
    }
}