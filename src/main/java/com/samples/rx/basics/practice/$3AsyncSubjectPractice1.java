package com.samples.rx.basics.practice;

import com.samples.rx.basics.AbstractObservers;
import com.samples.rx.basics.domain.User;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.PublishSubject;

@SuppressWarnings("ALL")
public class $3AsyncSubjectPractice1 extends AbstractObservers {


    public static Observer<User> fetchUsersForMe() {
        return new Observer<User>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("For Me: Subscribed to FetchUsers");
            }

            @Override
            public void onNext(User user) {
                System.out.println("\tFor Me : (" + user.getFirstName() + " " + user.getLastName() + ") emitted");
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
                System.out.println("For Me: completed");
            }
        };
    }

    public static Observer<User> fetchUsersForYou() {
        return new Observer<User>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("For You: Subscribed to FetchUsers");
            }

            @Override
            public void onNext(User user) {
                System.out.println("\tFor You : (" + user.getFirstName() + " " + user.getLastName() + ") emitted");
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
                System.out.println("For You: completed");
            }
        };
    }

    public static Observer<User> fetchUsersForSpy() {
        return new Observer<User>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("For Spy: Subscribed to FetchUsers");
            }

            @Override
            public void onNext(User user) {
                System.out.println("\tFor Spy : (" + user.getFirstName() + " " + user.getLastName() + ") emitted");
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
                System.out.println("For Spy: completed");
            }
        };
    }


    public static void main(String[] args) {

        /**
         * 1- Place subscriptions in between onNext(s) that after the execution the result will look like
         *
         * For Me: Subscribed to FetchUsers
         * For You: Subscribed to FetchUsers
         *      For Me : (Meghan Tesla) emitted
         * For Me: completed
         *      For You : (Meghan Tesla) emitted
         * For You: completed
         *
         *
         *
         * 2- Place subscriptions in between onNext(s) that after the execution the result will look like
         *
         * For You: Subscribed to FetchUsers
         * For Me: Subscribed to FetchUsers
         *      For You : (Meghan Tesla) emitted
         * For You: completed
         *      For Me : (Meghan Tesla) emitted
         * For Me: completed
         *
         */


        AsyncSubject<User> asyncSubjectSubject = AsyncSubject.create();

        asyncSubjectSubject.onNext(new User("Marine", "Cisco"));
        asyncSubjectSubject.onNext(new User("Cecilia", "Mac"));
        asyncSubjectSubject.onNext(new User("Matilda", "Linux"));
        asyncSubjectSubject.onNext(new User("Elena", "Android"));
        asyncSubjectSubject.onNext(new User("Tanya", "Pepsi"));
        asyncSubjectSubject.onNext(new User("Elizabeth", "Oracle"));
        asyncSubjectSubject.onNext(new User("Meghan", "Tesla"));


    }

}
