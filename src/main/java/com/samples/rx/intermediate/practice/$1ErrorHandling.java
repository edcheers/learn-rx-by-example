package com.samples.rx.intermediate.practice;

@SuppressWarnings("ALL")
public class $1ErrorHandling {


    public static void main(String[] args) {

        /**
         * Practice 1
         *
         * 1- Create a list of 5 accounts
         * 2- accounts are "Account 1" with $100, "Account 2" with $120, "Account 3" with $90,"Account 4" with $101, "null" with $1000,
         * 3- iterate through account and set the score for any account with less than $100 to 2
         * 4- iterate through account and set the score for any account with more than $100 to 5
         * 5- filter the ones that have account amount of less than $50
         * 6- create a second subscription
         * 7- any value that is more than $500 needs to be sent to the new subscription
         * 8- print out in the second subscription all the emitted values
         * 9- create an error subscription
         * 10- While emitting the events from the first subscription any account with name set to null should report to error susbscription
         * 11- Error subscription should log the error
         */



    }

}
