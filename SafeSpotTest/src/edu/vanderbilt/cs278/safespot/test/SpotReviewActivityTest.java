package edu.vanderbilt.cs278.safespot.test;

import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import edu.vanderbilt.cs278.safespot.SpotReviewActivity;
import edu.vanderbilt.cs278.safespot.Util;

public class SpotReviewActivityTest extends ActivityUnitTestCase<SpotReviewActivity> {
	private int btnId_Rate;
	private int btnId_Send;
	//private int btnId_Map;
	private int btnId_Star;
	private int btnId_EditTxt;
	private int list_Comment;
	private SpotReviewActivity activity;
	private Instrumentation mInstrumentation;
	
	
	public SpotReviewActivityTest() {
		super(SpotReviewActivity.class);
	}
	
	@Override
	  protected void setUp() throws Exception {
	    super.setUp();
	    mInstrumentation = getInstrumentation();
	    Intent intent = new Intent(mInstrumentation.getTargetContext(),
	    		SpotReviewActivity.class);
	    startActivity(intent, null, null);       
	    activity = getActivity();
	  }
	
	public void testLayout() {
		btnId_Rate = edu.vanderbilt.cs278.safespot.R.id.btn_submit;
		btnId_Send = edu.vanderbilt.cs278.safespot.R.id.comment_submit;
		//btnId_Map = edu.vanderbilt.cs278.safespot.R.id.map;
		btnId_Star = edu.vanderbilt.cs278.safespot.R.id.ratingBar1;
		btnId_EditTxt = edu.vanderbilt.cs278.safespot.R.id.ratingBar2;
		list_Comment = edu.vanderbilt.cs278.safespot.R.id.ListView1;	
		
	    assertNotNull(activity.findViewById(btnId_Rate));
	    Button btn_rate = (Button) activity.findViewById(btnId_Rate);
	    assertEquals("Incorrect label of the button", "RATE", btn_rate.getText());
	    
	    assertNotNull(activity.findViewById(btnId_Send));
	    Button btn_send = (Button) activity.findViewById(btnId_Send);
	    assertEquals("Incorrect label of the button", "Send", btn_send.getText());
	    
	    //assertNotNull(activity.findViewById(btnId_Map));
	    
	    assertNotNull(activity.findViewById(btnId_Star));
	    RatingBar ratingbar= (RatingBar) activity.findViewById(btnId_Star);
	    assertEquals("Incorrect default star", 4, ratingbar.getNumStars());
	    
	    assertNotNull(activity.findViewById(btnId_EditTxt));
	    EditText edit_comment= (EditText) activity.findViewById(btnId_EditTxt);
	    assertEquals("Incorrect default star", "", edit_comment.getText());
	    
	    assertNotNull(activity.findViewById(list_Comment));
	    ListView list_comment= (ListView) activity.findViewById(list_Comment);
	    assertEquals("Incorrect default star", 0, list_comment.getCount());
    
	}
	
	
	public void testIntentTriggerViaOnClick() {
		btnId_Rate = edu.vanderbilt.cs278.safespot.R.id.btn_submit;
		btnId_Send = edu.vanderbilt.cs278.safespot.R.id.comment_submit;
		btnId_EditTxt = edu.vanderbilt.cs278.safespot.R.id.ratingBar2;
		Button btn_rate = (Button) activity.findViewById(btnId_Rate);
		Button btn_send = (Button) activity.findViewById(btnId_Send);
	    assertNotNull("Button not allowed to be null", btn_rate);
	    assertNotNull("Button not allowed to be null", btn_send);
	    

	    btn_rate.performClick();
	  
	    // Check the intent which was started
	    Intent triggeredIntent_rate = getStartedActivityIntent();
	    assertNotNull("Intent was null", triggeredIntent_rate);
	    String msgType_rate = triggeredIntent_rate.getExtras().getString(Util.REQUEST_TYPE);
	    assertEquals("Incorrect data passed via the intent",
	    		Util.SEND_STAR, msgType_rate);
	    
	    assertNotNull(activity.findViewById(btnId_EditTxt));
	    final EditText edit_comment= (EditText) activity.findViewById(btnId_EditTxt);
	    if(edit_comment.getText().toString()== ""){
		    Intent triggeredIntent_send = getStartedActivityIntent();
		    assertNotNull("Intent was null", triggeredIntent_send);
	    }
	    
	    activity.runOnUiThread(new Runnable() {
	          public void run() {
	        	  edit_comment.setText("test");
	          }
	      });
	    
	    mInstrumentation.waitForIdleSync();
	    
	    Intent triggeredIntent_send = getStartedActivityIntent();
	    assertNotNull("Intent was null", triggeredIntent_rate);
	    String msgType_send = triggeredIntent_send.getExtras().getString(Util.REQUEST_TYPE);
	    assertEquals("Incorrect data passed via the intent",
	    		Util.SEND_REVIEW, msgType_send);
    
	  }

}
