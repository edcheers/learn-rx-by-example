package com.samples.rx.$1basics.learning;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class $6BasicsMap {

    public static void main(String[] args) {
        map();

    }


    private static void map() {

        /**
         * .map:
         * ------
         * The Map operator applies a function of your choosing to each item emitted by the source Observable,
         * and returns an Observable that emits the results of these function applications.
         *
         * Please read: http://reactivex.io/documentation/operators/map.html
         */

        /**
         * fromArray:
         * ----------
         * convert various other objects and data types into Observables
         *
         * Please read: http://reactivex.io/documentation/operators/from.html
         */

        /**
         * following example shows items in the array iterated through and each one is then
         * converted to an Observable and then emitted on subscription.
         * map then converts a to (a) and b to (b) and so on.
         *
         * You should see the following result when executing this code
         *
         * subscribed
         * (a)
         * (b)
         * (c)
         * (d)
         * (e)
         * completed
         */

        Observable.fromArray("a", "b", "c", "d", "e")
                .map(individualAlphabet -> "(" + individualAlphabet + ")")
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("subscribed");
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println(s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("completed");
                    }
                });

        System.out.println();

        /**
         * following example shows multiple map(s) are chained together to process the observable object emitted
         * You should see the following result in the output
         *
         * subscribed
         * (+(a)+)
         * (+(b)+)
         * (+(c)+)
         * (+(d)+)
         * (+(e)+)
         * completed
         */

        Observable.fromArray("a", "b", "c", "d", "e")
                .map(individualAlphabet -> "(" + individualAlphabet + ")")
                .map(individualAlphabet -> "+" + individualAlphabet + "+")
                .map(individualAlphabet -> "(" + individualAlphabet + ")")
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("subscribed");
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println(s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("completed");
                    }
                });

    }


}
