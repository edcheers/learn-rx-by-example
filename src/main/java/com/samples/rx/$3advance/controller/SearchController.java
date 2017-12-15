package com.samples.rx.$3advance.controller;

import com.samples.rx.$3advance.domain.SearchModel;
import com.samples.rx.$3advance.srv.AccountSearchService;
import io.reactivex.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class SearchController {

    @Autowired
    private AccountSearchService searchService;

    @SubscribeMapping(value = "/topic/search")
    public Observable<SearchModel> search(SearchModel searchModel) {
        return searchService.search(searchModel);
    }

    @SubscribeMapping(value = "/topic/suggestion")
    public Observable<SearchModel> suggestion(SearchModel searchModel) {
        return searchService.searchSuggestion(searchModel);
    }


}
