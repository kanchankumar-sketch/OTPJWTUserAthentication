package in.reinventing.otpauthentication.externalservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

@Service
public class ThreadPollService {

	private ExecutorService executor = Executors.newFixedThreadPool(4); 
	
	public void submitTaks(Runnable task) {
		this.executor.execute(task);
	}
}
