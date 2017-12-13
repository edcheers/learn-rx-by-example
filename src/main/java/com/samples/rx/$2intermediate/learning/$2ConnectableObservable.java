package com.samples.rx.$2intermediate.learning;


import com.samples.rx.$1basics.domain.Account;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;

@SuppressWarnings("Duplicates")
public class $2ConnectableObservable {

    public static void main(String[] args) {

        ConnectableObservable<Account> accountsObservable = getAccounts().publish();

        accountsObservable.subscribe(getObserver("Subscription 1"));
        accountsObservable.subscribe(getObserver("Subscription 2"));
        accountsObservable.subscribe(getObserver("Subscription 3"));

        accountsObservable.connect();

    }

    private static Observer<Account> getObserver(String s) {
        return new Observer<Account>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Account account) {
                System.out.println(s + ": " + account);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }


    static Observable<Account> getAccounts() {
        return Observable.range(0, 5)
                .flatMap(accountId -> {
                    Account account = new Account("Account " + accountId, accountId * 100, 0);
                    return Observable.just(account);
                });
    }


}
