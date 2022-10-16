public interface startjob{
    public Message invokejob();

    default void runprocess()
    {
        Message pubmessage = invokejob();
        pubmessage.nextlevel = joblevel.proceed;
        pubmessage.nextseq = 1;
        MyKafkaProducer mp = new MyKafkaProducer();
        mp.pubMessage(pubmessage);
        return;
    }

}
