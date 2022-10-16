import java.util.*;

public interface startjob{
    public List<String> invokejob();

    default void runprocess()
    {
        Message pubmessage = new Message(Makejob.getTopic()+joblevel.proceed+1);
        pubmessage.messagelist = invokejob();
        pubmessage.nextlevel = joblevel.proceed;
        pubmessage.nextseq = 1;
        MyKafkaProducer mp = new MyKafkaProducer();
        mp.pubMessage(pubmessage);
        return;
    }

}
