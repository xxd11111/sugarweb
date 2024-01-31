package com.sugarweb.framework.common;

import java.util.List;

/**
 * TODO
 *
 * @author 许向东
 * @version 1.0
 */
public class TreeNode<T> {

    private String id;

    private String pid;

    private T data;

    private List<T> children;

    public List<T> getChildren(){
        return null;
    }

    public List<T> getData(){

        return null;
    }

    public static <T> TreeNode<T> build(List<T> data){
        return null;
    }

}
