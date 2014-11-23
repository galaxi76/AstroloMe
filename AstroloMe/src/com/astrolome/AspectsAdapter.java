package com.astrolome;


import com.astrolome.Constants.*;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AspectsAdapter extends ArrayAdapter<HashMap<String, String>>{
	
	private Context mContext;
	private ArrayList<HashMap<String, String>> itemsList;
	private int aspectsRow;
	 String planOne;
	 String transit;
	 String planTwo;
	 String descrip;
	 LayoutInflater inflater;
	 
//	ArrayList<HashMap<String,String>> items =
//	        new ArrayList<HashMap<String,String>>();
 
	public AspectsAdapter(Context context,   int aspectsRow, ArrayList<HashMap<String, String>> items) {
        super(context, aspectsRow, items);
        this.aspectsRow = aspectsRow;
        this.mContext = context;
        this.itemsList = items;
    }
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        
        if(convertView == null) {
        	inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.aspects_row, parent, false);
            
            // initialize the view holder
            viewHolder = new ViewHolder();
            viewHolder.zodIcon = (ImageView) convertView.findViewById(R.id.aspectIcon);
            viewHolder.planetOne = (TextView) convertView.findViewById(R.id.planetOneText);
            viewHolder.transit = (TextView)convertView.findViewById(R.id.transitAspect);
            viewHolder.planetTwo = (TextView)convertView.findViewById(R.id.planetTwoText);
            viewHolder.tvDescription = (TextView) convertView.findViewById(R.id.aspectsExplanation);
            viewHolder.openDetail = (Button)convertView.findViewById(R.id.aspectsDetailsBtn);
            convertView.setTag(viewHolder);         
          
            viewHolder.openDetail.setTag(position);
            viewHolder.openDetail.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
//					Toast.makeText(mContext, viewHolder.planetOne.getText().toString()
//							+ viewHolder.transit.getText().toString() + 
//							viewHolder.planetTwo.getText().toString(), Toast.LENGTH_SHORT).show();
                 Toast toast = Toast.makeText(mContext, viewHolder.tvDescription.getText().toString(), Toast.LENGTH_SHORT);
                 toast.setGravity(Gravity.CENTER, 0, 0);
                 toast.show();
				}
            });
            
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        planOne = getPlanetName(itemsList.get(position).get("planet_id1"));
        transit = getTransit(itemsList.get(position).get("transit_aspect_id"));
        planTwo = getPlanetName(itemsList.get(position).get("planet_id2"));
        descrip = itemsList.get(position).get("content").toString();
        
        viewHolder.planetOne.setText(planOne);
        viewHolder.transit.setText(" " +  transit + " ");
        viewHolder.planetTwo.setText(planTwo);
        viewHolder.tvDescription.setText(descrip);
        viewHolder.zodIcon.setBackgroundResource(getAspectDrawable(transit));

        return convertView;
    }

//	Conjunction: 1
//	Opposition: 2
//	Trine: 3
//	Square: 4
//	Sextile: 5
//	SesquiSquare: 6
//	SemiSquare: 7
//	SemiSextile: 8
//	Quincunx: 9
//	 planet_id1, planet_id2, transit_aspect_id, degrees, minutes, seconds, content
	public String getTransit(String aspectNum){
		int an = Integer.valueOf(aspectNum);
		String aspectName = "";
		switch (an) {
		case 1:
			return aspectName = "Conjunction";
		case 2:
			return aspectName = "Opposition";
		case 3:
			return aspectName = "Trine";
		case 4:
			return aspectName = "Square";
		case 5:
			return aspectName = "Sextile";
		case 6:
			return aspectName = "SesquiSquare";
		case 7:
			return aspectName = "SemiSquare";
		case 8:
			return aspectName = "SemiSextile";
		case 9:
			return aspectName = "Quincunx";
		default:
			break;
		}
		return aspectName;
	}
	public String getPlanetName(String planetNum){
		int pn = Integer.valueOf(planetNum);
		String planetName = "";
		switch (pn) {
		case 13:
			planetName = "Sun";
			return planetName;
		case 14:
			return planetName = "Moon";
		case 15:
			return planetName = "Mercury";
		case 16:
			return planetName = "Venus";
		case 17:
			return planetName = "Mars";
		case 18:
			return planetName = "Jupiter";
		case 19:
			return planetName = "Saturn";
		case 20:
			return planetName = "Uranus";
		case 21:
			return planetName = "Neptune";
		case 22:
			return planetName = "Pluto";
		case 26:
			return planetName = "Ascendant";
		default:
			break;
		}
		return planetName;		
	}
	
	public int getAspectDrawable(String aspectName){
		
		int aspectIcon = 0;
		if(aspectName.equals("Conjunction")){
			return aspectIcon = R.drawable.icon_conjunction;
		}
		if(aspectName.equals("Opposition")){
			return aspectIcon = R.drawable.icon_opposition;
		}
		if(aspectName.equals("Trine")){
			return aspectIcon = R.drawable.icon_trine;
		}
		if(aspectName.equals("Square")){
			return aspectIcon = R.drawable.icon_squarejpg;
		}
		if(aspectName.equals("Sextile")){
			return aspectIcon = R.drawable.icon_sextile;
		}
		if(aspectName.equals("SesquiSquare")){
			return aspectIcon = R.drawable.icon_sesquisquare;
		}
		if(aspectName.equals("SemiSquare")){
			return aspectIcon = R.drawable.icon_semisquare;
		}
		if(aspectName.equals("SemiSextile")){
			return aspectIcon = R.drawable.icon_semisextile;
		}
		if(aspectName.equals("Quincunx")){
			return aspectIcon = R.drawable.icon_quincunx;
		}
		return aspectIcon;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return itemsList.size();
	}

	@Override
	public HashMap<String, String> getItem(int position) {
		// TODO Auto-generated method stub
		return itemsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
    
    /**
     * The view holder design pattern prevents using findViewById()
     * repeatedly in the getView() method of the adapter.
     * 
     * @see http://developer.android.com/training/improving-layouts/smooth-scrolling.html#ViewHolder
     */
    private static class ViewHolder {
        ImageView zodIcon;
        TextView planetOne;
        TextView transit;
        TextView planetTwo;
        TextView tvDescription;
        Button openDetail;
    }
  }

