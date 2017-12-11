package com.samples.rx.intermediate.learning;


import com.samples.rx.basics.domain.Account;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;

import java.net.HttpRetryException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("Duplicates")
public class $3ErrorHandling {

    public static BehaviorSubject<Throwable> errorLine;

    public static void main(String[] args) {

        initErrorHandler();
        errorLine.subscribe(throwable -> {
            System.out.println("------> Error happened in another line: " + throwable.getMessage());
        });

        errorHandling();


    }


    public static void initErrorHandler() {
        errorLine = BehaviorSubject.create();
    }

    public static void errorHandling() {

        AtomicInteger retries = new AtomicInteger(0);
        getAccountIds()
                .flatMap(accountId ->
                        getAccount(accountId)
                                // tries three times, it is nicer that the retry to be inside the getAccount method
                                .retry(e -> retries.incrementAndGet() < 3)

                )
                .onErrorResumeNext(Observable.empty()) // if after three ties it fails it emits nothing
                .subscribe(subscribe());

    }


    public static Observable<Integer> getAccountIds() {
        return Observable.range(1, 10);
    }


    public static Observable<Account> getAccount(Integer accountId) {

        AtomicInteger retries = new AtomicInteger(0);

        return Observable.create((ObservableOnSubscribe<Account>) e -> {

            if (accountId == 6) { // assume this failed and we need to rely on old system
                e.onComplete();
            }
            if (accountId == 7) {
                System.out.println("\tSecurity error happened. Retry");
                e.onError(new IllegalAccessError("Security failed"));
            }
            if (accountId == 8) {
                System.out.println("\tNetwork error happened. Retry");
                e.onError(new HttpRetryException("Network error", 1));
            }
            Account account = new Account("NEW: Account " + accountId, accountId * 100, 5);
            e.onNext(account);
            e.onComplete();

        })
                .retry(e -> retries.incrementAndGet() < 3)
                // publish to error stream
                .doOnError(throwable -> errorLine.onNext(throwable))
                // if expected exception happens, log them, then return and empty
                .onErrorResumeNext(whenExceptionIsThenLogAndIgnore(HttpRetryException.class, IllegalAccessError.class))
                // when an empty is returned, try to get the result from the old system
                .switchIfEmpty(getAccountFromOldSystem(accountId));
    }


    public static Observable<Account> getAccountFromOldSystem(Integer accountId) {
        return Observable.create(e -> {

            Account account = new Account("OLD: Account " + accountId, accountId * 100, 5);
            e.onNext(account);
            e.onComplete();
        });
    }


    public static Observer<Account> subscribe() {
        return new Observer<Account>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Subscribed");
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
                System.out.println("Completed");
            }
        };
    }


    public static <T> Function<Throwable, Observable<T>> whenExceptionIsThenLogAndIgnore(Class... what) {
        return t -> Arrays.stream(what).anyMatch(e -> e.isInstance(t)) ? fireErrorLog(t) : breakTheChain(t);
    }

    public static <T> Observable<T> breakTheChain(Throwable t) {
        System.out.println("\t\tBreaking the chain : " + t);
        return Observable.error(t);
    }

    public static <T> Observable<T> fireErrorLog(Throwable t) {
        System.out.println("\t\tFiring error log event");
        // you can do bunch of stuff here when an error happens
        return logError(t);
    }

    public static <T> Observable<T> logError(Throwable throwable) {
        // imagine this is connecting to an external system
        System.out.println("\t\tError: " + throwable.getMessage());
        return Observable.empty();
    }


}
