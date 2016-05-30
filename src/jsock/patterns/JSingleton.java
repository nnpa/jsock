/*
 * jsock framework https://github.com/Padaboo/jsock open source
 * Each line should be prefixed with  * 
 */

package jsock.patterns;

/**
 * 
 * @author padaboo I.B Aleksandrov jetananas@yandex.ru
 */
public class JSingleton {
    private static volatile JSingleton instance;

    public static JSingleton getInstance() {
        JSingleton localInstance = instance;
        
        if (localInstance == null) {
            synchronized (JSingleton.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new JSingleton();
                }
            }
        }
        return localInstance;
    }
}