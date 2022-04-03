/***
 * Excerpted from "Pragmatic Unit Testing in Java with JUnit",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/utj2 for more book information.
***/
package me.study.testpractice.iloveyouboss;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Weight {
   MUST_MATCH(Integer.MAX_VALUE),
   VERY_IMPORTANT(5000),
   IMPORTANT(1000),
   WOULD_PREFER(100),
   DONT_CARE(0);
   
   private final int value;
}
