package com.samples.rx.basics.practice;

import com.samples.rx.basics.AbstractObservers;

@SuppressWarnings("ALL")
public class $5ReplaySubjectPractice1 extends AbstractObservers {


    public static void main(String[] args) {

        /**
         * 1- Create a ReplaySubject
         * 2- async subject should emit 10 string
         * 3- Strings are "a","b","c","d","e","f","g","h","i","j"
         * 4- create 2 observers in form of methods
         *      4-1- First observer's emits "a","b","c","d","e","f","g","h","i","j"
         *      4-2- Second observer's emits "a","b","c","d","e","f","g","h","i","j"
         *      4-3- Third observer's emits "a","b","c","d","e","f","g","h","i","j"
         * 5- these observers are not placed next the the previous subscription
         */

    }

}
