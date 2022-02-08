package com.performance.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
//import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class Controller {
	//private static final AtomicLong count = new AtomicLong(0L);
	private List<leakobject> objects = new ArrayList<>();
    static List<Byte[]> memoryBuffer;
    static final int OneG = 1024*1024*1024;
    static {
        memoryBuffer = new ArrayList<>();
    }



    @RequestMapping("/")
    @ResponseBody
    public String profilewelcome() {
        return "<html>\n" + "<header><title>Java Profiling</title></header>\n" +
          "<body>\n" + "<b>Profiling Endpoints</b> <br> /generatethreads - Spawn 10 inactive threads <br> /cpuload?time=120&load=0.8 - Maintain 80% CPU for 120 Seconds <br> /deadlock - Trigger Deadlock <br> /generateobjects - Create Memory Leak<br> /addmemory - consume heap memory <br> /release - release memory<br>\n" + "</body>\n" + "</html>";
    }



	@RequestMapping("/generatethreads")
	public String threads() {
		ExecutorService service = Executors.newFixedThreadPool(10, new CustomizableThreadFactory("LabThread-"));
		for(int i=0;i<=10;i++) {
			service.execute(new Runnable() {
				
				@Override
				public void run() {}
			});
		}
		return "Ten Threads!";
	}
	@RequestMapping("/generateobjects")
	public String generateobjects() {
		for(int x=0;x<=1000;x++) {
			//Long uid = Long.valueOf(count.addAndGet(1L));
			String message1 = "ababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierf";
			String message2 = "ababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierfababorjoiejfoierf";
			String message = message1 + message2;
			objects.add(new leakobject(message));
			
		}
		return "Simulating objects leaking in memory!";
	}

		@RequestMapping("/deadlock")
		public String deadLockTest(){
			Logger logger = LoggerFactory.getLogger(Controller.class);
			Object lockA = new Object();
			Object lockB = new Object();	
			Thread threadA = new Thread(new threadA(lockA,lockB),"LockedthreadA-");
			Thread threadB = new Thread(new threadB(lockA,lockB),"LockedthreadB-");
			threadA.start();
			threadB.start();
			try {
				threadA.join();
				threadB.join();
			} catch (InterruptedException e) {
				logger.error("error!",Thread.currentThread().getName(),e);
				e.printStackTrace();
			}
			return "Simulating deadlock!";
		}




		@RequestMapping("/cpuload")
        public HashMap<String,String> intensiveCalc(@RequestParam("time") String time, @RequestParam("load") double load) throws Exception {
        int numCore = Runtime.getRuntime().availableProcessors();
        int numThreadsPerCore = 2;
        // double load = 0.8;
        final long duration = Integer.parseInt(time) * 1000; //millis

        for (int thread = 0; thread < numCore * numThreadsPerCore; thread++) {
            new BusyThread("High-CPU-Thread" + thread, load, duration).start();
        }

        HashMap<String, String> result = new HashMap<String, String>();
        result.put("core", String.valueOf(numCore));
        result.put("load", String.valueOf(load));
        result.put("time", time);

        return result;
    }

    private static class BusyThread extends Thread {
        private double load;
        private long duration;

        /**
         * Constructor which creates the thread
         * @param name Name of this thread
         * @param load Load % that this thread should generate
         * @param duration Duration that this thread should generate the load for
         */
        public BusyThread(String name, double load, long duration) {
            super(name);
            this.load = load;
            this.duration = duration;
        }

        /**
         * Generates the load when run
         */
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            try {
                // Loop for the given duration
                while (System.currentTimeMillis() - startTime < duration) {
                    // Every 100ms, sleep for the percentage of unladen time
                    if (System.currentTimeMillis() % 100 == 0) {
                        Thread.sleep((long) Math.floor((1 - load) * 100));
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    

    @RequestMapping("/addmemory")
    public String AddLoad() {
        Byte[] oneGByte = new Byte[OneG/10];
        memoryBuffer.add(oneGByte);
        Runtime rt = Runtime.getRuntime();
        StringBuffer memoryInfo = new StringBuffer();
        memoryInfo.append("Total Memory: ").append(rt.totalMemory()/(1024*1024)).append(" Free Memory: ")
                .append(rt.freeMemory() / (1024*1024));
        return memoryInfo.toString();
    }

    @RequestMapping("/releasememory")
    public String Release() {
        memoryBuffer.clear();
        return "Released";
    }

	}
	
	
	