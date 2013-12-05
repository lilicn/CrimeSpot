package edu.vanderbilt.cs278.safespot.test;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.test.AndroidTestCase;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import edu.vanderbilt.cs278.safespot.ArrayAdapterItem;

public class ArrayAdapterItemTest extends AndroidTestCase {
	ArrayAdapterItem testArrayAdapter = null;
	Context ctx = null;
	List<String> data = null;
	View view = null;
	ViewGroup viewGroup = null;
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		ctx = getContext();
		data = new ArrayList<String>();
		view = new View(ctx);
		viewGroup = new LinearLayout(ctx);
	}
	
	public void testGetView(){
		testArrayAdapter = new ArrayAdapterItem(ctx, 0, data);
		View rtn = testArrayAdapter.getView(0, view, viewGroup);
		assertNotNull(rtn);
	}
	
}
