/***
 * Excerpted from "Pragmatic Unit Testing in Java with JUnit",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/utj2 for more book information.
***/
package me.study.testpractice.iloveyouboss;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Answer {
   private final int i;
   private final Question question;

   public Answer(Question question, int i) {
      this.question = question;
      this.i = i;
   }

   public Answer(Question characteristic, String matchingValue) {
      this.question = characteristic;
      this.i = characteristic.indexOf(matchingValue);
   }
   
   public String getQuestionText() {
      return question.getText();
   }

   public boolean match(int expected) {
      return question.match(expected, i);
   }

   public boolean match(Answer otherAnswer) {
      return question.match(i, otherAnswer.i);
   }

   public Question getCharacteristic() {
      return question;
   }
}
