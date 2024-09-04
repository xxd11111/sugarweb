package com.sugarweb;

import java.io.File;
import java.io.IOException;
import java.util.List;

import info.debatty.java.stringsimilarity.Cosine;
import info.debatty.java.stringsimilarity.Jaccard;
import info.debatty.java.stringsimilarity.RatcliffObershelp;
import org.apache.commons.io.FileUtils;
import org.apache.commons.text.similarity.JaccardDistance;

/**
 * Simhash测试
 *
 * @author litaoxiao
 */
public class Main {
    public static void main(String[] args) throws IOException {
        RatcliffObershelp ro = new RatcliffObershelp();
        // substitution of s and t
        // "dict_name dict_type dict_code"
        // "cata_name cata_type cata_code"
        System.out.println(ro.similarity("dict_name,dict_type,dict_code", "dict_name,dict_type,dict_code"));
        System.out.println(ro.distance("dict_name,dict_type,dict_code", "dict_name,dict_type,dict_code"));

        // substitution of s and n
        System.out.println(ro.similarity("My string", "My ntrisg"));
        System.out.println(ro.distance("My string", "My ntrisg"));
    }
}