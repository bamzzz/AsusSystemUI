/*

public class MainActivity extends Activity {

	//Move your declarations here if you intend on using them after the onCreate() method
	private TextView foodLabel;
	private TextView drinkLabel;
	private RelativeLayout mealLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Inflate your objects
		foodLabel = (TextView) findViewById(R.id.food);
		drinkLabel = (TextView) findViewById(R.id.drink);
		mealLayout = (RelativeLayout) findViewById(R.id.layout);

		//We change the color to RED for the first time as the program loads
		mealLayout.setBackgroundColor(Color.RED);


		//Create the timer object which will run the desired operation on a schedule or at a given time
		Timer timer = new Timer();

		//Create a task which the timer will execute.  This should be an implementation of the TimerTask interface.
		//I have created an inner class below which fits the bill.
		MyTimer mt = new MyTimer();

		//We schedule the timer task to run after 1000 ms and continue to run every 1000 ms.
		timer.schedule(mt, 1000, 1000);
	}

	//An inner class which is an implementation of the TImerTask interface to be used by the Timer.
	class MyTimer extends TimerTask {

		public void run() {

			//This runs in a background thread.
			//We cannot call the UI from this thread, so we must call the main UI thread and pass a runnable
			runOnUiThread(new Runnable() {

				public void run() {
					Random rand = new Random();
					//The random generator creates values between [0,256) for use as RGB values used below to create a random color
					//We call the RelativeLayout object and we change the color.  The first parameter in argb() is the alpha.
					mealLayout.setBackgroundColor(Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
				}
			});
		}

	}
}*/
