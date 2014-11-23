package com.astrolome;

import com.viewpagerindicator.IconPagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FragmentAdapter extends FragmentStatePagerAdapter implements IconPagerAdapter{
	
	 protected static final String[] MY_CONTENT = new String[] { "Planets", "Elements", "Aspects", };

	protected static final int[] TAB_ICONS = new int[] {
//        R.drawable.star_selector,
        R.drawable.tab_planets_selector,
        R.drawable.tab_elements_selector,
        R.drawable.tab_aspects_selector
		};
	
	private int mCount = MY_CONTENT.length;

	public FragmentAdapter(FragmentManager fm) {
		super(fm);
	
	}

	@Override
	public int getIconResId(int index) {
		 return TAB_ICONS[index % TAB_ICONS.length];
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = null;
		switch (position) {
//		case 0:
//			fragment = new BirthChartMain();
//			break;
		case 0:
			fragment = new PlanetFragment();
			break;
		case 1:
			fragment = new HouseFragment();
			break;
		case 2:
			fragment = new AspectsFragment();
		default:
			break;
		}
		return fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mCount;
	}
	
	  @Override
	    public CharSequence getPageTitle(int position){
		 return FragmentAdapter.MY_CONTENT[position % MY_CONTENT.length];
//	        String title = "";
//	        switch(position){
//	        case 0:
//	            title = "First";
//	            break;
//	        case 1:
//	            title = "Second";
//	            break;
//	        case 2:
//	            title = "Third";
//	            break;
//	        }
//	        return title;
	    }
	  
	  public void setCount(int count) {
	        if (count > 0 && count <= 10) {
	            mCount = count;
	            notifyDataSetChanged();
	        }
	    }
	


}
