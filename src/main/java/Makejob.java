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
    String workname;
    public static String getTopic()
    {
        return jobname+level.toString()+seq;
    }
    public Makejob() throws IOException {
        /*  Example(start)
            workname = work1
            jobname = test
            joblevel = start
            seq = 1
            kafkaserver = localhost:9092
        */

        /*  Example(proceed)
            workname = work2
            jobname = test
            joblevel = proceed
            seq = 3
            kafkaserver = localhost:9092
            isfin = last
        */

        /*  Example(end)
            workname = work3
            jobname = test
            joblevel = end
            seq = 1
            kafkaserver = localhost:9092
        */
        URL resource = getClass().getClassLoader().getResource("jobconfig.properties");
        Path path = new File(resource.getPath()).toPath();
        List<String> contents = Files.readAllLines(path);
        workname = contents.get(0);
        jobname = contents.get(1);
        if (contents.get(2).equals("start")) {
            level = joblevel.start;
        }
        if (contents.get(2).equals("proceed")) {
            level = joblevel.proceed;
            isfin = contents.get(5).equals("last");
        }
        if (contents.get(2).equals("end")) {
            level = joblevel.end;
        }
        seq = Integer.parseInt(contents.get(3));
        kafkaserver = contents.get(4);
    }

    public void run() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (level.equals("start")) {
            startjob job = (startjob) Class.forName(workname).getConstructor().newInstance();
            job.runprocess();
        }
        if (level.equals("proceed")) {
            proceedjob job = (proceedjob) Class.forName(workname).getConstructor().newInstance();
            job.runprocess();
        }
        if (level.equals("end")) {
            endjob job = (endjob) Class.forName(workname).getConstructor().newInstance();
            job.runprocess();
        }

    }

}
