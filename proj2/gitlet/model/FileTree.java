package gitlet.model;

import gitlet.Utils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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

    public boolean isFile() {
        return Constant.BLOBS.equals(node.getType());
    }

    public FileTree(File file) throws IOException {
        Node node = new Node();
        node.setName(file.getName());
        if (file.isFile()) {
            node.setType(Constant.BLOBS);
            Blob blob = new Blob();
            blob.setText(Utils.readContentsAsString(file));
            node.setHashCode(Utils.persistObject(blob));
        } else {
            node.setType(Constant.TREE);
        }
        this.node = node;
        children = new LinkedList<>();
    }

    public FileTree() {}

    private static class Node {
        /** filename or dir name */
        String name;

        String hashCode;

        /** blob or tree */
        String type;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHashCode() {
            return hashCode;
        }

        public void setHashCode(String hashCode) {
            this.hashCode = hashCode;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}