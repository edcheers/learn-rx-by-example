package com.samples.rx.$1basics.practice;

import com.samples.rx.$1basics.AbstractObservers;
import com.samples.rx.$1basics.domain.User;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

@SuppressWarnings("ALL")
public class $4BehaviorSubjectPractice1 extends AbstractObservers {


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
         * Place subscriptions in between onNext(s) that after the execution the result will look like
         *
         * For Me: Subscribed to FetchUsers
         *      For Me : (Cecilia Mac) emitted
         *      For Me : (Matilda Linux) emitted
         *      For Me : (Elena Android) emitted
         * For Spy: Subscribed to FetchUsers
         *      For Spy : (Elena Android) emitted
         *      For Me : (Tanya Pepsi) emitted
         *      For Spy : (Tanya Pepsi) emitted
         *      For Me : (Elizabeth Oracle) emitted
         *      For Spy : (Elizabeth Oracle) emitted
         * For You: Subscribed to FetchUsers
         *      For You : (Elizabeth Oracle) emitted
         *      For Me : (Meghan Tesla) emitted
         *      For Spy : (Meghan Tesla) emitted
         *      For You : (Meghan Tesla) emitted
         *
         */


        BehaviorSubject<User> userPublishSubject = BehaviorSubject.create();

        userPublishSubject.onNext(new User("Marine", "Cisco"));
        userPublishSubject.onNext(new User("Cecilia", "Mac"));
        userPublishSubject.onNext(new User("Matilda", "Linux"));
        userPublishSubject.onNext(new User("Elena", "Android"));
        userPublishSubject.onNext(new User("Tanya", "Pepsi"));
        userPublishSubject.onNext(new User("Elizabeth", "Oracle"));
        userPublishSubject.onNext(new User("Meghan", "Tesla"));


    }

}
