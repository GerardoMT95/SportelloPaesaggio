package it.eng.tz.puglia.servizi_esterni.scheduler;

@Deprecated
abstract class CustomRunnable implements Runnable {

	@SuppressWarnings("unused")
	private Object parameter;

	
	public CustomRunnable(Object parameter) {
		this.parameter = parameter;       		// store parameter for later use
	}

	
	@Override
	public void run() {	}


	
	//  TO START THIS RUNNABLE: ---> run this somewhere in your code:
	
	//	(Custom)Runnable r = new CustomRunnable(param_value);
	//	new Thread(r).start();

	// 	oppure

	//	Thread t = new Thread(new CustomRunnable(param_value));
	//	t.start();
	
}