/*
 * jsock framework https://github.com/nnpa/jsock open source
 * Each line should be prefixed with  * 
 */
package jsock.patterns;


/**
 *
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public interface JObserver {
   public JObserverSubject subject = null;
   public abstract void update();
}
