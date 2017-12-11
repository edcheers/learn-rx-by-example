package com.samples.rx.basics.practice;

import com.samples.rx.basics.domain.User;
import io.reactivex.Observable;
import io.reactivex.Observer;

import java.util.ArrayList;
import java.util.List;

public class $1BasicPractice2 {

    private static List<User> searchForUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("Bill", "Gates"));
        users.add(new User("Steve", "Jobs"));
        users.add(new User("Larry", "Ellison"));
        users.add(new User("Mark", "Zuckerburg"));

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
         * Call searchForUsers() and make sure all users are emitted
         */
        return Observable.empty();
    }

    public static Observer<User> myUsersObserver() {
        /**
         * Implement observer that observes and prints [Last Name]-[First Name] i.e Gates-Bill
         */
        return null;
    }

    public static Observer<List<User>> myUserListsObserver() {
        /**
         * Implement observer that observes and prints initials [Last Name Initial]-[First Name-Initial] i.e G-B
         */
        return null;
    }


    public static void main(String[] args) {


        asyncSearchForUsers()
                .subscribe(myUsersObserver());

        asyncSearchForUserList()
                .subscribe(myUserListsObserver());

    }

}
