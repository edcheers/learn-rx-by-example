package com.samples.rx.basics.learning;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.AsyncSubject;

public class $3AsyncSubject {

    public static void main(String[] args) {

        // emitting the last value
        asyncSubjectExample1();

    }

    private static void asyncSubjectExample1() {
        AsyncSubject<String> source = AsyncSubject.create();

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
