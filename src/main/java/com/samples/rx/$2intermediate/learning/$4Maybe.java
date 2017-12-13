package com.samples.rx.$2intermediate.learning;


import com.samples.rx.$1basics.domain.Account;
import io.reactivex.Maybe;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.subjects.BehaviorSubject;

import java.net.HttpRetryException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("Duplicates")
public class $4Maybe {

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

        getAccountIds()
                .flatMap(accountId -> getAccount(accountId).toObservable())
                .subscribe(subscribe());

    }


    public static Observable<Integer> getAccountIds() {
        return Observable.range(1, 10);
    }


    public static Maybe<Account> getAccount(Integer accountId) {

        AtomicInteger retries = new AtomicInteger(0);

        return Maybe.create((MaybeOnSubscribe<Account>) e -> {
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
            e.onSuccess(account);
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


    public static Maybe<Account> getAccountFromOldSystem(Integer accountId) {
        return Maybe.create(e -> {
            Account account = new Account("OLD: Account " + accountId, accountId * 100, 5);
            e.onSuccess(account);
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


    public static <T> Function<Throwable, Maybe<T>> whenExceptionIsThenLogAndIgnore(Class... what) {
        return t -> Arrays.stream(what).anyMatch(e -> e.isInstance(t)) ? fireErrorLog(t) : breakTheChain(t);
    }

    public static <T> Maybe<T> breakTheChain(Throwable t) {
        System.out.println("\t\tBreaking the chain : " + t);
        return Maybe.error(t);
    }

    public static <T> Maybe<T> fireErrorLog(Throwable t) {
        System.out.println("\t\tFiring error log event");
        // you can do bunch of stuff here when an error happens
        return logError(t);
    }

    public static <T> Maybe<T> logError(Throwable throwable) {
        // imagine this is connecting to an external system
        System.out.println("\t\tError: " + throwable.getMessage());
        return Maybe.empty();
    }


}
