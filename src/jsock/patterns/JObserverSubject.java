/*
 * jsock framework https://github.com/Padaboo/jsock open source
 * Each line should be prefixed with  * 
 */

package jsock.patterns;

import jsock.patterns.JObserver;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class JObserverSubject {
   private List<JObserver> observers = new ArrayList<JObserver>();

   private int state;

   public int getState() {
      return state;
   }

   public void setState(int state) {
      this.state = state;
      notifyAllObservers();
   }

   public void attach(JObserver observer){
      observers.add(observer);  
   }

   public void notifyAllObservers(){
      for (JObserver observer : observers) {
         observer.update();
      }
   }  
}
