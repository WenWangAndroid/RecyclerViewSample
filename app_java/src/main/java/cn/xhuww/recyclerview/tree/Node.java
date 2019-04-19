package cn.xhuww.recyclerview.tree;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private String id;
    private String name;
    private Node parent;
    private String parentId;
    private List<Node> children = new ArrayList<>();

    private int level; //层级
    private boolean expand;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    //父节点 是否展开
    public boolean isParentExpand() {
        if (parent == null) {
            return false;
        }
        return parent.isExpand();
    }

    //是否为根结点
    public boolean isRoot() {
        return parent == null;
    }

    //是否为叶子结点
    public boolean isLeaf() {
        return children.size() == 0;
    }

}
