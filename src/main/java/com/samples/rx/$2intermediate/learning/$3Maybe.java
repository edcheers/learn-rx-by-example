package com.samples.rx.$2intermediate.learning;


import com.samples.rx.$1basics.domain.Account;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;

@SuppressWarnings("Duplicates")
public class $3Maybe {


    public static void main(String[] args) {

        getAccount()
                .defaultIfEmpty(new Account("Default Account", 120, 0))
                .subscribe(getObserver());

        getAccountWithValue()
                .subscribe(getObserver());


        ensureGetAccount()
                .subscribe(getObserver());

    }

    private static MaybeObserver<Account> getObserver() {
        return new MaybeObserver<Account>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Account account) {
                System.out.println(account);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    // this may or may not return account
    private static Maybe<Account> getAccount() {
        // this is not returning account
        return Maybe.create(e -> e.onComplete());
    }


    // this may or may not return account
    private static Maybe<Account> getAccountWithValue() {
        // this is not returning account
        return Maybe.create(e -> {
            e.onSuccess(new Account("Hello Account", 100, 0));
            e.onComplete();
        });
    }


    // this returns value
    private static Maybe<Account> ensureGetAccount() {
        // this is not returning account
        return Maybe.create((MaybeEmitter<Account> e) -> {
            e.onComplete();
        }).defaultIfEmpty(new Account("Ensured accoubt", 100, 0));
    }


}
