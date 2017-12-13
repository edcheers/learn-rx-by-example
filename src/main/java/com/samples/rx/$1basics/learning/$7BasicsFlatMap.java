package com.samples.rx.$1basics.learning;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import java.util.Arrays;
import java.util.List;

public class $7BasicsFlatMap {

    public static void main(String[] args) {

        flatmap();
        flatMapChain();
        flatMapInFlatMap();
        elegantFlatMapInFlatMap();

    }


    private static void flatmap() {

        /**
         * flatmap:
         * ------
         * transform the items emitted by an Observable into Observables, then flatten the emissions from those into
         * a single Observable
         *
         * http://reactivex.io/documentation/operators/flatmap.html
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

        List<String> alphabets = Arrays.asList("a", "b", "c", "d", "e");

        Observable.fromIterable(alphabets)
                .flatMap(individualAlphabet -> addParentheses(individualAlphabet))
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

    private static void elegantFlatMapInFlatMap() {
        List<String> alphabets = Arrays.asList("a", "b", "c", "d", "e");

        System.out.println();

        /**
         * But the way the above code is written is not clean and therefore not recommended
         * It is better to create a method that does both and execute it like the following code
         * The following code should produce:
         *
         * subscribed
         * (+(a)+)
         * (+(b)+)
         * (+(c)+)
         * (+(d)+)
         * (+(e)+)
         * completed
         */

        Observable.fromIterable(alphabets)
                .flatMap($7BasicsFlatMap::addMyWrapper)
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

    private static void flatMapChain() {

        List<String> alphabets = Arrays.asList("a", "b", "c", "d", "e");

        /**
         * following example shows multiple flatmaps(s) are chained together to process the observable object emitted
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

        Observable.fromIterable(alphabets)
                .flatMap($7BasicsFlatMap::addParentheses)
                .flatMap($7BasicsFlatMap::addPlus)
                .flatMap($7BasicsFlatMap::addParentheses)
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

    private static void flatMapInFlatMap() {

        List<String> alphabets = Arrays.asList("a", "b", "c", "d", "e");

        System.out.println();

        /**
         * You can have fun inner chaining with flatmap
         * The following code produces
         *
         * subscribed
         * +(a)+
         * +(b)+
         * +(c)+
         * +(d)+
         * +(e)+
         * completed
         *
         */

        Observable.fromIterable(alphabets)
                .flatMap(alphabet ->
                        addParentheses(alphabet)
                                .flatMap(result -> addPlus(result))
                )
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


    private static Observable<? extends String> addMyWrapper(String individualAlphabet) {
        return Observable.just(individualAlphabet)
                .flatMap($7BasicsFlatMap::addParentheses)
                .flatMap($7BasicsFlatMap::addPlus)
                .flatMap($7BasicsFlatMap::addStar);
    }

    private static Observable<? extends String> addStar(String individualAlphabet) {
        return Observable.just("(" + individualAlphabet + ")");
    }


    private static Observable<? extends String> addParentheses(String individualAlphabet) {
        return Observable.just("(" + individualAlphabet + ")");
    }

    private static Observable<? extends String> addPlus(String individualAlphabet) {
        return Observable.just("+" + individualAlphabet + "+");
    }


}
