import java.util.*;
import java.util.ArrayList;

public class Message {


    private String topicname;
    List<String> messagelist = new ArrayList<String>();
    joblevel nextlevel;
    int nextseq = 0;
    boolean isfin = true;
    public Message(String topicname)
    {
        topicname = topicname;
    }

    public void setMessagelist(List<String> messagelist) {
        this.messagelist = messagelist;
    }

    public void setLevel(joblevel nextlevel) {
        this.nextlevel = nextlevel;
    }

    public void setSeq(int seq) {
        this.nextseq = seq;
    }

    public String gettopic()
    {
        return topicname+nextlevel.toString()+nextseq;
    }

    public void setIsfin(boolean tf)
    {
        this.isfin = tf;
    }

    public void setTopicname(String topicname) {
        this.topicname = topicname;
    }
}
