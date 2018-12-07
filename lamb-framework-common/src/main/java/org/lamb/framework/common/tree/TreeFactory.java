
package org.lamb.framework.common.tree;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TreeFactory{
    /** 树根*/
    private Tree root;

    public TreeFactory()
    {
        TreeNode node = new TreeNode();
        node.setNodeId("root");

        root = new Tree();
        root.setNode(node);
        root.setChildNodes(new ArrayList<Tree>());
    }

    public TreeFactory createTree(List<TreeNode> treeNodes){
        if(treeNodes == null || treeNodes.size() < 0)
            return null;

        TreeFactory factory =  new TreeFactory();

        //将所有节点添加到多叉树中
        for(TreeNode treeNode : treeNodes) {
            if(treeNode.getParentId().equals("root")) {
                //向根添加一个节点
                Tree tree = new Tree();
                tree.setNode(treeNode);
                tree.setChildNodes(new ArrayList<Tree>());
                factory.getRoot().getChildNodes().add(tree);
            }
            else {
                addChild(factory.getRoot(), treeNode);
            }
        }

        return factory;
    }

    /**
     * 向指定多叉树节点添加子节点
     */
    public void addChild(Tree tree, TreeNode child) {
        for(Tree item : tree.getChildNodes()) {
            if(item.getNode().getNodeId().equals(child.getParentId())) {
                //找到对应的父亲
                Tree childTree = new Tree();
                childTree.setNode(child);
                childTree.setChildNodes(new ArrayList<Tree>());
                item.getChildNodes().add(childTree);
                break;
            }
            else {
                if(item.getChildNodes() != null && item.getChildNodes().size() > 0) {
                    addChild(item, child);
                }
            }
        }
    }
    /**
     * 遍历多叉树
     */
    public String iteratorTree(Tree tree) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("\n");

        if(tree != null) {
            for (Tree index : tree.getChildNodes()) {
                buffer.append(index.getNode().getNodeId()+ ",");

                if (index.getChildNodes() != null && index.getChildNodes().size() > 0 ) {
                    buffer.append(iteratorTree(index));
                }
            }
        }
        buffer.append("\n");

        return buffer.toString();
    }
}
