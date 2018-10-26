package org.lamb.framework.core.tree;

import lombok.Data;

import java.util.List;

@Data
public class Tree {

    /** 树节点*/
    private TreeNode node;
    /** 子树集合*/
    private List<Tree> childNodes;
}
