package com.samples.rx.intermediate.learning;


import com.samples.rx.basics.domain.Account;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class $1ExceptionsEmptyErrors {

    public static void main(String[] args) {

        //switchEmpty();
        errorAndRetry();

    }


    public static void switchEmpty() {

        getAccountIds()
                .flatMap(accountId -> getAccount(accountId))
                .subscribe(new Observer<Account>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Account account) {
                        System.out.println(account.getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    public static void errorAndRetry() {

        AtomicInteger retryCounter = new AtomicInteger(0);

//        getAccountIds()
//                .flatMap(accountId -> getAccountWithRetry(accountId)
//                        .retryWhen(errors ->
//                                errors.delay(500, TimeUnit.MILLISECONDS).retry(3)
//
//                        ))
//                        )
//                .subscribe()
//        ;
    }


    public static Observable<Integer> getAccountIds() {
        return Observable.range(1, 10);
    }

    public static Observable<Account> getAccount(Integer accountId) {
        return Observable.create((ObservableOnSubscribe<Account>) e -> {

            if (accountId == 6) { // assume this failed and we need to rely on old system
                e.onComplete();
            }
            Account account = new Account("NEW: Account " + accountId, accountId * 100, 5);
            e.onNext(account);
            e.onComplete();

        }).switchIfEmpty(getAccountFromOldSystem(accountId));
    }


    public static Observable<Account> getAccountFromOldSystem(Integer accountId) {
        return Observable.create((ObservableOnSubscribe<Account>) e -> {

            Account account = new Account("OLD: Account " + accountId, accountId * 100, 5);
            e.onNext(account);
            e.onComplete();
        });
    }


    public static Observable<Account> getAccountWithRetry(Integer accountId) {
        System.out.println("h");
        return Observable.error(new RuntimeException("Unable to load account"));
    }


}
