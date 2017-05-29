package com.example.divergent.fabflixapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Divergent on 5/27/2017.
 */

public class Paginator {
    public static int TOTAL_NUM_MOVIES;
    public static int MOVIES_PER_PAGE = 15;
    public static int MOVIES_REMAINING = TOTAL_NUM_MOVIES % MOVIES_PER_PAGE;
    public static int LAST_PAGE = TOTAL_NUM_MOVIES/MOVIES_PER_PAGE;
    private List<String> mList;

    public Paginator(List<String> moveList)
    {
        mList = new ArrayList<String>();
        mList = moveList;
        TOTAL_NUM_MOVIES = mList.size();
    }
    public List<String> generatePage(int currentPage)
    {
        int startItem = currentPage * MOVIES_PER_PAGE;
        int numOfData= MOVIES_PER_PAGE;

        //ArrayList<String> pageData = new ArrayList<String>();
        List<String> pageData = mList;
        pageData = mList.subList((currentPage)*MOVIES_PER_PAGE, Math.min(mList.size(), (currentPage+ 1)*MOVIES_PER_PAGE));
//        if (currentPage == LAST_PAGE && MOVIES_REMAINING > 0)
//        {
//            for (int i = startItem; i < startItem + MOVIES_PER_PAGE;i++)
//                pageData.add(mList.get(i));
//        }
//        else
//        {
//            for (int i = startItem; i <startItem + numOfData; i++)
//                pageData.add(mList.get(i));
//        }
        return pageData;
    }
}
