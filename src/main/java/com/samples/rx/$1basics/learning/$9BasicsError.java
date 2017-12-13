package com.samples.rx.$1basics.learning;

import com.samples.rx.$1basics.domain.Account;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("ALL")
public class $9BasicsError {

    public static void main(String[] args) {

        error();
        errorAndErrorHandling();

    }


    private static void error() {
        /**
         * error
         * -----
         * The error operator takes as a parameter the Throwable with which you want the Observable to terminate.
         *
         * Sometimes and Observable is a result of connectivity to another system and may return and error. In such scenarios
         * we need to make sure the chain is either broken or errors are logged then chain is broker.
         */

        List<Account> accounts = Arrays.asList(
                new Account("Checking", 100f, 3),
                new Account("Saving", 100000f, 5),
                new Account("Investment", 1000f, 4),
                new Account(), // imagine call to get this account failed
                new Account("Business Checking", 10000f, 4)
        );

        Observable.fromIterable(accounts)
                .flatMap($9BasicsError::loadAccount) // this has an error in it
                .subscribe(new Observer<Account>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("Loading accounts");
                        System.out.println("------------------");
                    }

                    @Override
                    public void onNext(Account account) {
                        System.out.println(account);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Terminating the operation due to:");
                        System.out.println(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Accounts loaded successfully");
                    }
                });


    }


    private static void errorAndErrorHandling() {
        /**
         * The following example handles the error but allows further observables to be emitted
         */

        List<Account> accounts = Arrays.asList(
                new Account("Checking", 100f, 3),
                new Account("Saving", 100000f, 5),
                new Account("Investment", 1000f, 4),
                new Account(), // imagine call to get this account failed
                new Account("Business Checking", 10000f, 4)
        );

        Observable.fromIterable(accounts)
                .flatMap($9BasicsError::loadAccount) // ignore the error and continue
                .onErrorResumeNext(whenExceptionIsThenLogAndIgnore(RuntimeException.class))
                .subscribe(new Observer<Account>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("Loading accounts");
                        System.out.println("------------------");
                    }

                    @Override
                    public void onNext(Account account) {
                        System.out.println(account);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Terminating the operation due to:");
                        System.out.println(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Accounts loaded successfully");
                    }
                });


    }

    private static Observable<? extends Account> loadAccount(Account account) {
        if (account.getName() == null) { // imagine call to get this account failed
            return Observable.error(new RuntimeException("Connection lost. Account not loaded"));
        }
        return Observable.just(account);
    }

    public static <T> Function<Throwable, Observable<T>> whenExceptionIsThenLogAndIgnore(Class what) {
        return t -> what.isInstance(t) ? fireErrorLog(t) : breakTheChain(t);
    }

    public  static <T> Observable<T> breakTheChain(Throwable t) {
        System.out.println("\t\tBreaking the chain");
        return Observable.error(t);
    }

    public static  <T> Observable<T> fireErrorLog(Throwable t) {
        System.out.println("\t\tMaking sure chain is resilient");
        return log(t.getMessage());
    }

    public static  <T> Observable<T> log(String message) {
        System.out.println("\t\tLog Error: " + message);
        return Observable.empty();
    }
}
