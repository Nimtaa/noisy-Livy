import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import org.apache.spark.api.java.function.*;
import org.apache.livy.*;

    public class PiJob implements Job<Double>, Function<Integer, Integer>,
            Function2<Integer, Integer, Integer> {
        private  final int samples;
        public PiJob(int samples) {
            this.samples = samples;
        }
        @Override
        public Double call(JobContext ctx) throws Exception {
            List<Integer> sampleList = new ArrayList<Integer>();
            for (int i = 0; i < samples; i++) {
                sampleList.add(i + 1);
            }
            return 4.0d * ctx.sc().parallelize(sampleList).map(this).reduce(this) / samples;
        }
        @Override
        public Integer call(Integer v1) {
            double x = Math.random();
            double y = Math.random();
            return (x * x + y * y < 1) ? 1 : 0;
        }
        @Override
        public Integer call(Integer v1, Integer v2) {
            return v1 + v2;
        }
        public static void main(String[] args) {

            ServiceLoader<LivyClientFactory> loader = ServiceLoader.load(LivyClientFactory.class, PiJob.class.getClassLoader());
            System.out.println(loader);
            Iterator iterator =  loader.iterator();
            while (iterator.hasNext()){
                System.out.println(iterator.next());
                System.out.println("***");
            }


        }
    }




