package com.samples.rx.$1basics.learning;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import java.util.Arrays;

public class $1Basics {

    public static void main(String[] args) {

//        singleItemAutoCreate();
//        singleItemBespokeCreation();
        multipleItemAutoCreate();
//        multipleItemBespokeCreate1();
//        multipleItemBespokeCreate2();

    }

    private static void singleItemBespokeCreation() {
        System.out.println("--------------------------------- Example 2");

        // Observing and emitting one item
        String item1 = "apple";

        // bespoke creation
        Observable.create((ObservableOnSubscribe<String>) e -> {

            e.onNext(item1);
            e.onComplete();

        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Subscribed to the observable");
            }

            @Override
            public void onNext(String s) {
                System.out.println("Object " + s + " was emitted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Error " + e.getMessage() + " happened");
            }

            @Override
            public void onComplete() {
                System.out.println("Observer completed it's work");
            }
        });
    }

    private static void singleItemAutoCreate() {
        // Observing and emitting one item
        String item1 = "apple";

        System.out.println("--------------------------------- Example 1");

        // simple creation
        Observable.just(item1)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("Subscribed to the observable");
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("Object " + s + " was emitted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Error " + e.getMessage() + " happened");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Observer completed it's work");
                    }
                });
    }

    private static void multipleItemAutoCreate() {

        System.out.println("--------------------------------- Example 3");

        // simple creation
        Observable.fromArray("apple", "orange", "banana", "chocolate")
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("Subscribed to the observable");
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("Object " + s + " was emitted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Error " + e.getMessage() + " happened");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Observer completed it's work");
                    }
                });
    }

    private static void multipleItemBespokeCreate1() {

        System.out.println("--------------------------------- Example 3");

        Observable.create((ObservableOnSubscribe<String>) e -> {

            e.onNext("apple");
            e.onNext("orange");
            e.onNext("banana");
            e.onNext("chocolate");
            e.onComplete();

        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Subscribed to the observable");
            }

            @Override
            public void onNext(String s) {
                System.out.println("Object " + s + " was emitted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Error " + e.getMessage() + " happened");
            }

            @Override
            public void onComplete() {
                System.out.println("Observer completed it's work");
            }
        });
    }

    private static void multipleItemBespokeCreate2() {

        System.out.println("--------------------------------- Example 5");

        Observable.create((ObservableOnSubscribe<String>) e -> {

            String items[] = {"apple", "orange", "banana", "chocolate"};
            Arrays.stream(items).forEach(e::onNext);
            e.onComplete();

        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Subscribed to the observable");
            }

            @Override
            public void onNext(String s) {
                System.out.println("Object " + s + " was emitted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Error " + e.getMessage() + " happened");
            }

            @Override
            public void onComplete() {
                System.out.println("Observer completed it's work");
            }
        });
    }


}
