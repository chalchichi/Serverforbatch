public interface endjob {
    public void invokejob(Message submessage);

    default void runprocess()
    {
        MyKafkaConsumer consumer = new MyKafkaConsumer();
        Message submessage = consumer.sub(Makejob.getTopic());
        invokejob(submessage);
        return;
    }
}
