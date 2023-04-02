package gitlet.model;

import gitlet.Utils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @package: gitlet
 * @className: FileTree
 * @author: zyb
 * @description: Tree structures that store file
 * @date: 2023/3/6 13:35
 * @version: 1.0
 */
public class FileTree implements Serializable {

    private Node node;

    private List<String> children;

    public List<String> getChildren() {
        return children;
    }

    public String getFileContentSha1Code() {
        return node.getSha1Code();
    }

    public void setChildren(List<String> children) {
        this.children = children;
    }

    public String getFileName() {
        if (Objects.isNull(node)) {
            return null;
        }
        return node.getName();
    }

    public boolean isFile() {
        return Constant.BLOBS.equals(node.getType());
    }

    public boolean isNotFile() {
        return !isFile();
    }

    @Override
    public String toString() {
        return "FileTree{" +
                "node=" + node +
                ", children=" + children +
                '}';
    }

    public FileTree(File file) throws IOException {
        Node node = new Node();
        node.setName(file.getName());
        if (file.isFile()) {
            node.setType(Constant.BLOBS);
            node.setSha1Code(Utils.persistObject(Utils.readContentsAsString(file)));
        } else {
            node.setType(Constant.TREE);
        }
        this.node = node;
        children = new LinkedList<>();
    }


    public FileTree() {
        children = new LinkedList<>();
        node = new Node();
    }

    private static class Node implements Serializable {
        /** filename or dir name */
        String name;

        String sha1Code;

        /** blob or tree */
        String type;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSha1Code() {
            return sha1Code;
        }

        public void setSha1Code(String sha1Code) {
            this.sha1Code = sha1Code;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "name='" + name + '\'' +
                    ", sha1Code='" + sha1Code + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }
}
