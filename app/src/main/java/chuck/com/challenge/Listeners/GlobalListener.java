package chuck.com.challenge.listeners;

/**
 * Created by Laurence on 17/09/2016.
 */
public interface GlobalListener {

    void goToActivity(Class clazz);

    void goToActivity(Class clazz, int flags);
}