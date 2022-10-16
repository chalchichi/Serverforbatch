public interface proceedjob{
    public Message invokejob(Message submessage);

    default void runprocess()
    {
        MyKafkaConsumer consumer = new MyKafkaConsumer();
        Message submessage = consumer.sub(Makejob.getTopic());
        Message pubmessage = invokejob(submessage);
        if(Makejob.isfin)
        {
            pubmessage.nextlevel = joblevel.end;
            pubmessage.nextseq = 1;
        }
        else {
            pubmessage.nextlevel = joblevel.proceed;
            pubmessage.nextseq = submessage.nextseq+1;
        }
        MyKafkaProducer mp = new MyKafkaProducer();
        mp.pubMessage(pubmessage);
        return;
    }
}
