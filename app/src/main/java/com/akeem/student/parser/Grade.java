package com.akeem.student.parser;

import java.util.HashMap;
import java.util.List;

public class Grade {
    private HashMap<String, List<Integer>> total_scores;

    public HashMap<String, List<Integer>> getTotal_scores() {
        return total_scores;
    }

    public void setTotal_scores(HashMap<String, List<Integer>> total_scores) {
        this.total_scores = total_scores;
    }
}
