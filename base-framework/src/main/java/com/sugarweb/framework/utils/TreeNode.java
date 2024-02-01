package com.sugarweb.framework.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.function.Function;

/**
 * 树构造器
 *
 * @author 许向东
 * @version 1.0
 */
@Getter
@Setter
public class TreeNode<T, R> {

    private R id;

    private R pid;

    private T parent;

    private T data;

    private final TreeSet<TreeNode<T, R>> children;

    public TreeNode(Comparator<TreeNode<T, R>> comparator) {
        this.children = new TreeSet<>(comparator);
    }

    public void add(TreeNode<T, R> child) {
        children.add(child);
    }

    public static <T, R> List<TreeNode<T, R>> build(List<T> data, Function<T, R> functionGetId, Function<T, R> functionGetPid, Comparator<T> comparator) {
        Comparator<TreeNode<T, R>> treeNodeComparator = getComparator(comparator);
        List<TreeNode<T, R>> treeNodeList = data.stream()
                .map(t -> {
                            TreeNode<T, R> trTreeNode = new TreeNode<>(treeNodeComparator);
                            trTreeNode.setId(functionGetId.apply(t));
                            trTreeNode.setPid(functionGetPid.apply(t));
                            trTreeNode.setData(t);
                            return trTreeNode;
                        }
                ).toList();
        for (int i = 0; i < treeNodeList.size(); i++) {
            TreeNode<T, R> t = treeNodeList.get(i);
            for (int j = i + 1; j < treeNodeList.size(); j++) {
                TreeNode<T, R> search = treeNodeList.get(j);
                if (Objects.equals(search.getId(), t.getPid())) {
                    t.setParent(search.getParent());
                    search.add(t);
                }
            }
        }
        return treeNodeList.stream()
                .filter(t -> t.getParent() != null)
                .sorted(treeNodeComparator)
                .toList();
    }

    private static <T, R> Comparator<TreeNode<T, R>> getComparator(Comparator<T> comparator) {
        return (o1, o2) -> comparator.compare(o1.getData(), o2.getData());
    }

}
