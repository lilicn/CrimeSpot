package edu.vanderbilt.cs278.safespot.test;

import static org.mockito.Mockito.*;

import java.util.List;

import android.test.AndroidTestCase;
import android.test.mock.MockContext;
import android.view.View;
import android.view.ViewGroup;
import edu.vanderbilt.cs278.safespot.ArrayAdapterItem;

public class ArrayAdapterItemTest extends AndroidTestCase {
	ArrayAdapterItem testArrayAdapter = null;
	MockContext mockCtx = null;
	List<String> data = null;
	View view = null;
	ViewGroup viewGroup = null;
	@SuppressWarnings("unchecked")
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		mockCtx = new MockContext();
		data = mock(List.class);
		view = mock(View.class);
		viewGroup = mock(ViewGroup.class);	
	}
	
	public void testGetView(){
		testArrayAdapter = new ArrayAdapterItem(mockCtx, 0, data);
		when(data.get(0)).thenReturn("test");
		testArrayAdapter.getView(0, view, viewGroup);
		verify(data.get(0));	
	}
	
}
