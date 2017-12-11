package com.samples.rx.basics.learning;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

public class $2Publish {

    public static void main(String[] args) {

        // emitting from the location you subscribe to
        publishExample1();

    }

    private static void publishExample1() {
        PublishSubject<String> source = PublishSubject.create();

        source.subscribe(getFirstObserver());

        source.onNext("Apple");
        source.onNext("Orange");
        source.onNext("Banana");

        source.subscribe(getSecondObserver());

        source.onNext("Pen");
        source.onNext("Chocolate");
        source.onComplete();

    }

    private static Observer<? super String> getFirstObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("---------------------");
                System.out.println("Observer 1 subscribed");
                System.out.println("---------------------");
            }

            @Override
            public void onNext(String  item) {
                System.out.println("Observer 1 emitted " + item);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                System.out.println("Observer 1 completed...");
            }
        };
    }


    private static Observer<? super String> getSecondObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("---------------------");
                System.out.println("+ Observer 2 subscribed");
                System.out.println("---------------------");
            }

            @Override
            public void onNext(String  item) {
                System.out.println("+ Observer 2 emitted " + item);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                System.out.println("+ Observer 2 completed...");
            }
        };
    }


}
