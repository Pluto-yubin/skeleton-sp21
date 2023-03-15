package gitlet.model;

/**
 * @package: gitlet.model
 * @className: Blob
 * @author: zyb
 * @description: TODO
 * @date: 2023/3/7 13:39
 * @version: 1.0
 */
public class Blob {
    String text;

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
