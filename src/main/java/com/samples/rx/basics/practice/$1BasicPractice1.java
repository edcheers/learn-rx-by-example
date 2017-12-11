package com.samples.rx.basics.practice;

import com.samples.rx.basics.AbstractObservers;
import com.samples.rx.basics.domain.User;
import io.reactivex.Observable;

import java.util.ArrayList;
import java.util.List;

public class $1BasicPractice1 extends AbstractObservers {

    private static List<User> searchForUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("Alex", "Cisco"));
        users.add(new User("Scott", "Mac"));
        users.add(new User("David", "Linux"));
        users.add(new User("Josh", "Android"));

        return users;
    }

    public static Observable<User> asyncSearchForUsers() {
        /**
         * Call searchForUsers() and make sure all users are emitted
         */
        return Observable.empty();
    }

    public static Observable<List<User>> asyncSearchForUserList() {
        /**
         * Call searchForUserList() and make sure all users are emitted
         */
        return Observable.empty();
    }


    public static void main(String[] args) {

        asyncSearchForUsers()
                .subscribe(userObserver());

        asyncSearchForUserList()
                .subscribe(userListObserver());

    }

}
