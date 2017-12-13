package com.samples.rx.$2intermediate.practice.search;

import com.samples.rx.$1basics.domain.Account;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class SearchService {

    private static SearchService instance;

    private SearchService() {
        accountSet.add(new Account("Alex Dorand", 100, 3));
        accountSet.add(new Account("Alex Alphonso", 150, 3));
        accountSet.add(new Account("Scott Alexander", 1200, 3));
        accountSet.add(new Account("Scott Mackenzie", 1500, 3));
        accountSet.add(new Account("David Gilmore", 2000, 3));
        accountSet.add(new Account("David Philip", 2200, 3));
    }

    public static SearchService getInstance() {
        return (instance == null ? new SearchService() : instance);
    }

    private List<Account> accountSet = new ArrayList<>();

    public Observable<Account> search(String s) {
        return Observable.create(e -> {
            accountSet.stream()
                    .filter(accounts -> accounts.getName().toLowerCase().contains(s.toLowerCase()))
                    .peek(account -> {
                        e.onNext(account);
                    }).collect(Collectors.toList());
            e.onComplete();

        });
    }

    public static void main(String[] args) {

        SearchService.getInstance().search("alex").subscribe(new Observer<Account>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Account account) {
                System.out.println(account);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

}
