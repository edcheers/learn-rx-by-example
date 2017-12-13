package com.samples.rx.$1basics;

import com.samples.rx.$1basics.domain.User;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import java.util.List;
import java.util.stream.Collectors;

public class AbstractObservers {


    public static Observer<User> userObserver() {
        return new Observer<User>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Subscribed to " + d.toString());
            }

            @Override
            public void onNext(User user) {
                System.out.println("(" + user.getFirstName() + " " + user.getLastName() + ") emitted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Error happened");
            }

            @Override
            public void onComplete() {
                System.out.println("Subscription completed");
            }
        };
    }

    public static Observer<List<User>> userListObserver() {

        return new Observer<List<User>>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Subscribed to " + d.toString());
            }

            @Override
            public void onNext(List<User> users) {
                users.stream().peek(user -> System.out.println("(" + user.getFirstName() + " " + user.getLastName() + ") emitted")).collect(Collectors.toList());

            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Error happened");
            }

            @Override
            public void onComplete() {
                System.out.println("Subscription completed");
            }
        };
    }
}
