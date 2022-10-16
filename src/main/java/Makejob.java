import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Makejob {
    public static String jobname;
    public static joblevel level;
    public static int seq;
    public static String kafkaserver;
    public static boolean isfin=true;
    public static String getTopic()
    {
        return jobname+level.toString()+seq;
    }
    public Makejob() throws IOException {
        /*  Example(start)
            jobname : test
            joblevel :start
            seq : 1
            kafkaserver :localhost:9092
        */

        /*  Example(proceed)
            jobname : test
            joblevel :proceed
            seq : 3
            kafkaserver :localhost:9092
            isfin : last
        */

        /*  Example(end)
            jobname : test
            joblevel :end
            seq : 1
            kafkaserver :localhost:9092
        */
        URL resource = getClass().getClassLoader().getResource("jobconfig.xml");
        Path path = new File(resource.getPath()).toPath();
        List<String> contents = Files.readAllLines(path);
        jobname = contents.get(0);
        if (contents.get(1).equals("start")) {
            level = joblevel.start;
        }
        if (contents.get(1).equals("proceed")) {
            level = joblevel.proceed;
            isfin = contents.get(4).equals("last");
        }
        if (contents.get(1).equals("end")) {
            level = joblevel.end;
        }
        seq = Integer.parseInt(contents.get(2));
        kafkaserver = contents.get(3);
    }

    public void run() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (level.equals("start")) {
            startjob job = (startjob) Class.forName(jobname).getConstructor().newInstance();
            job.runprocess();
        }
        if (level.equals("proceed")) {
            proceedjob job = (proceedjob) Class.forName(jobname).getConstructor().newInstance();
            job.runprocess();
        }
        if (level.equals("end")) {
            endjob job = (endjob) Class.forName(jobname).getConstructor().newInstance();
            job.runprocess();
        }

    }

}
