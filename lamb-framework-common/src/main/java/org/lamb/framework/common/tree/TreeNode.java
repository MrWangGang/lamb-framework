package org.lamb.framework.common.tree;

import lombok.Data;

@Data
public class TreeNode {
    /** 节点Id*/
    private String nodeId;
    /** 父节点Id*/
    private String parentId;
}
