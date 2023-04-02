package gitlet;

import gitlet.model.FileTree;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @package: gitlet
 * @className: FileTreeUtil
 * @author: zyb
 * @description: FileTree工具类
 * @date: 2023/3/6 13:44
 * @version: 1.0
 */
public class FileTreeUtil {

    public static String createFileTreeRecursively(File file) throws IOException {
        FileTree tree = new FileTree(file);
        if (Objects.nonNull(file.listFiles())) {
            for (File subFile : file.listFiles()) {
                tree.getChildren().add(createFileTreeRecursively(subFile));
            }
        }

        return Utils.persistObject(tree);
    }

    public static FileTree findFileTree(String fileName, FileTree root) {
        if (root == null || fileName.equals(root.getFileName())) {
            return root;
        }

        for (String child : root.getChildren()) {
            FileTree tree = Utils.readObject(Utils.getFileByHashcode(child), FileTree.class);
            FileTree fileTree = findFileTree(fileName, tree);
            if (Objects.nonNull(fileTree)) {
                return fileTree;
            }
        }
        return null;
    }

    /**
     * 只删除当前的FileTree，不会递归删除子文件
     * @param treeCode
     * @param root
     * @return
     */
    public static boolean removeFileTree(String treeCode, FileTree root) {
        if (root.getChildren().contains(treeCode)) {
            FileTree tree = Utils.readObject(treeCode, FileTree.class);
            root.getChildren().remove(treeCode);
            Utils.removeObject(treeCode);
            Utils.removeObject(tree.getFileContentSha1Code());
            return true;
        }

        for (String child : root.getChildren()) {
            FileTree tree = Utils.readObject(Utils.getFileByHashcode(child), FileTree.class);
            if (removeFileTree(treeCode, tree)) {
                return true;
            }
        }
        return false;
    }

}