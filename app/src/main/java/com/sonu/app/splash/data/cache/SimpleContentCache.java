package com.sonu.app.splash.data.cache;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Created by amanshuraikwar on 20/12/17.
 */

public abstract class SimpleContentCache<DataModel> implements ContentCache<DataModel> {

    private List<DataModel> cachedContent;
    private int curPage = 1;

    // volatile to give thread safety
    private volatile STATE state;

    private enum STATE {NORMAL, FETCHING}

    SimpleContentCache() {
        cachedContent = new ArrayList<>();
        setState(STATE.NORMAL);
    }

    // synchronized to give thread safety
    @Override
    public synchronized List<DataModel> getMoreContent() {
        if (state == STATE.FETCHING) {
            return Collections.emptyList();
        }

        return getMoreContentAct();
    }

    @Override
    public synchronized List<DataModel> getCachedContent() {
        return new ArrayList<>(cachedContent);
    }

    @Override
    public synchronized boolean isCacheEmpty() {
        return cachedContent.size() == 0;
    }

    @Override
    public synchronized void resetCache() {

        cachedContent = new ArrayList<>();
        curPage = 1;
        state = STATE.NORMAL;
    }

    abstract String getTag();
    protected abstract List<DataModel> fetchPage(int page);

    private List<DataModel> getMoreContentAct() {
        Log.d(getTag(), "getMoreContentAct():called");

        setState(STATE.FETCHING);

        List<DataModel> contentList;

        try {

            contentList = fetchPage(curPage);
            Log.i(getTag(), "getMoreContentAct():items=" + contentList.size());

            // updating cache
            updateCache(contentList);
        } catch (RuntimeException e) {
            setState(STATE.NORMAL);
            throw e;
        }

        setState(STATE.NORMAL);

        return contentList;
    }

    private synchronized void setState(STATE state) {
        this.state = state;
    }

    private synchronized void updateCache(List<DataModel> contentList) {
        cachedContent.addAll(contentList);
        curPage += 1;
    }
}
