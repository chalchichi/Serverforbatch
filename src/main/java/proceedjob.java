import java.util.*;

public interface proceedjob{


    /**
     * @implSpec sub message -> pub message`s list of String
     */
    public List<String> invokejob(Message submessage);

    default  void runprocess()
    {
        /*
            consumer`s topic(sub) : this job`s topic ( make by config )
            producer`s topic(pub) : next job`s topic ( make by subscribed message )
         */
        MyKafkaConsumer consumer = new MyKafkaConsumer();
        Message submessage = consumer.sub(Makejob.getTopic());
        List<String> messagelist = invokejob(submessage);
        Message pubmessage = null;
        if(Makejob.isfin)
        {
            pubmessage = new Message(Makejob.getTopic()+joblevel.end+1);
            pubmessage.nextlevel = joblevel.end;
            pubmessage.nextseq = 1;
        }
        else
        {
            pubmessage = new Message(Makejob.getTopic()+joblevel.end+(submessage.nextseq+1));
            pubmessage.nextlevel = joblevel.proceed;
            pubmessage.nextseq = submessage.nextseq+1;
        }
        MyKafkaProducer mp = new MyKafkaProducer();
        mp.pubMessage(pubmessage);
        return;
    }
}
