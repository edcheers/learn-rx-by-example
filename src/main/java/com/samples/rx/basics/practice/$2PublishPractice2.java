package com.samples.rx.basics.practice;

import com.samples.rx.basics.AbstractObservers;
import com.samples.rx.basics.domain.User;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

@SuppressWarnings("ALL")
public class $2PublishPractice2 extends AbstractObservers {


    public static Observer<User> fetchUsersForMe() {
        return null;
    }

    public static Observer<User> fetchUsersForYou() {
        return null;
    }


    public static void main(String[] args) {

        /**
         * Create and place subscriptions in between onNext(s) that after the execution the result will look like
         *
         * For Me: Subscribed to FetchUsers
         *      For Me : (Matilda Linux) emitted
         *      For Me : (Elena Android) emitted
         * For You: Subscribed to FetchUsers
         *      For Me : (Tanya Pepsi) emitted
         *      For You : (Tanya Pepsi) emitted
         *      For Me : (Elizabeth Oracle) emitted
         *      For You : (Elizabeth Oracle) emitted
         *      For Me : (Meghan Tesla) emitted
         *      For You : (Meghan Tesla) emitted
         *
         */


        PublishSubject<User> userPublishSubject = PublishSubject.create();

        userPublishSubject.onNext(new User("Marine", "Cisco"));
        userPublishSubject.onNext(new User("Cecilia", "Mac"));
        userPublishSubject.onNext(new User("Matilda", "Linux"));
        userPublishSubject.onNext(new User("Elena", "Android"));
        userPublishSubject.onNext(new User("Tanya", "Pepsi"));
        userPublishSubject.onNext(new User("Elizabeth", "Oracle"));
        userPublishSubject.onNext(new User("Meghan", "Tesla"));


    }

}
