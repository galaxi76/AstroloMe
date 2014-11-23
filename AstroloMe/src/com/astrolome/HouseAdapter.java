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

public class HouseAdapter extends ArrayAdapter<HashMap<String, String>>{
	
	private Context mContext;
	private ArrayList<HashMap<String, String>> itemsList;
	private int housesRow;
	 String houseTitle;
	 String houseDeg;
	 String zodiacName;
	 String descrip;
	 LayoutInflater inflater;
	 
//	ArrayList<HashMap<String,String>> items =
//	        new ArrayList<HashMap<String,String>>();
 
	public HouseAdapter(Context context,   int housesRow, ArrayList<HashMap<String, String>> items) {
        super(context, housesRow, items);
        this.housesRow = housesRow;
        this.mContext = context;
        this.itemsList = items;
    }
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        
        if(convertView == null) {
        	inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.houses_row, parent, false);
            
            // initialize the view holder
            viewHolder = new ViewHolder();
            viewHolder.zodIcon = (ImageView) convertView.findViewById(R.id.zodiacIcon);
            viewHolder.houseName = (TextView) convertView.findViewById(R.id.houseName);
            viewHolder.houseDegree = (TextView)convertView.findViewById(R.id.houseDegree);
            viewHolder.zodiacName = (TextView)convertView.findViewById(R.id.zodiacName);
            viewHolder.tvDescription = (TextView) convertView.findViewById(R.id.houseExplanation);
            viewHolder.openDetail = (Button)convertView.findViewById(R.id.houseDetailsBtn);
            convertView.setTag(viewHolder);         
          
            viewHolder.openDetail.setTag(position);
            viewHolder.openDetail.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Toast toast = Toast.makeText(mContext, viewHolder.houseName.getText().toString() +
							viewHolder.houseDegree.getText().toString() + 
							viewHolder.zodiacName.getText().toString(), Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				
//					 final AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
////					 View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_details, null);
//					 View view =  inflater.inflate(R.layout.dialog_details, null);
//					 dialog.setView(view);
//					 
//					 TextView explan = (TextView)view.findViewById(R.id.explanationDetail);
//					 Button ok = (Button)view.findViewById(R.id.ok);
//					 dialog.setTitle(viewHolder.houseName.getText() + 
//							 " " + viewHolder.houseDegree.getText() + " " + viewHolder.zodiacName.getText());
//                     dialog.setIcon(viewHolder.zodIcon.getBackground());
//                     explan.setText(descrip);
//                     dialog.create();
//                     ok.setOnClickListener(new View.OnClickListener() {
//						
//						@Override
//						public void onClick(View v) {
//							 ((DialogInterface) dialog).dismiss();
//                             return;							
//						}
//					});
//                       
//                     dialog.show();
//                 
				}
            });
            
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        houseTitle = itemsList.get(position).get("house_name");
//        houseTitle = getHouse(itemsList.get(position).get("house_id"));
        houseDeg = itemsList.get(position).get("degrees")+"\u00B0";
        zodiacName = getZodiacName(itemsList.get(position).get("zodiac_id"));
        descrip = itemsList.get(position).get("content").toString();
        
        viewHolder.houseName.setText(houseTitle);
        viewHolder.houseDegree.setText(" " +  houseDeg + " ");
        viewHolder.zodiacName.setText(zodiacName);
        viewHolder.tvDescription.setText(descrip);
        viewHolder.zodIcon.setBackgroundResource(getZodiacDrawable(zodiacName));

        return convertView;
    }
//	 View myDialog = View.inflate(mContext, R.layout.dialog_details, null);
//	 View view = getLayoutInflater().inflate(R.layout.dialog_main, null);
//	 dialog.setContentView(R.layout.dialog_details);
//	 dialog.addContentView(view, params)
//	 LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//	 View view = li.inflate(R.layout.dialog_details, null);
//	 dialog.setView(view);
//	public String getHouse(String houseNum){
//		int hn = Integer.valueOf(houseNum);
//		String houseName = "";
//		switch (hn) {
//		case 1:
//			houseName = "Ascendant";
//			return houseName;
//		case 2:
//			return houseName = "Second House";
//		case 3:
//			return houseName = "Second House";
//		case 4:
//			return houseName = "Imum Coeli";
//		case 5:
//			return houseName = "Second House";
//		case 6:
//			return houseName = "Second House";
//		case 7:
//			return houseName = "Second House";
//		case 8:
//			return houseName = "Second House";
//		case 9:
//			return houseName = "Second House";
//		case 10:
//			return houseName = "Second House";
//		case 11:
//			return houseName = "Second House";
//		case 12:
//			return houseName = "Second House";
//		default:
//			break;
//		}
//		return houseName;		
//	}
	
	public String getZodiacName(String zodiacNum){
		int zn = Integer.valueOf(zodiacNum);
		String zodiacName = "";
		switch (zn) {
		case 1:
			return zodiacName = "Aries";
		case 2:
			return zodiacName = "Taurus";
		case 3:
			return zodiacName = "Gemini";
		case 4:
			return zodiacName = "Cancer";
		case 5:
			return zodiacName = "Leo";
		case 6:
			return zodiacName = "Virgo";
		case 7:
			return zodiacName = "Libra";
		case 8:
			return zodiacName = "Scorpio";
		case 9:
			return zodiacName = "Sagittarius";
		case 10:
			return zodiacName = "Capricorn";
		case 11:
			return zodiacName = "Aquarius";
		case 12:
			return zodiacName = "Pisces";
		default:
			break;
		}
		return zodiacName;
	}
	
	public int getZodiacDrawable(String zodiacSign){
		Resources resources;
		int zodiacIcon = 0;
		if(zodiacSign.equals("Aries")){
			return zodiacIcon = R.drawable.sign_aries;
		}
		if(zodiacSign.equals("Taurus")){
			return zodiacIcon = R.drawable.sign_taurus;
		}
		if(zodiacSign.equals("Gemini")){
			return zodiacIcon = R.drawable.sign_gemini;
		}
		if(zodiacSign.equals("Cancer")){
			return zodiacIcon = R.drawable.sign_cancer;
		}
		if(zodiacSign.equals("Leo")){
			return zodiacIcon = R.drawable.sign_leo;
		}
		if(zodiacSign.equals("Virgo")){
			return zodiacIcon = R.drawable.sign_virgo;
		}
		if(zodiacSign.equals("Libra")){
			return zodiacIcon = R.drawable.sign_libra;
		}
		if(zodiacSign.equals("Scorpio")){
			return zodiacIcon = R.drawable.sign_scorpio;
		}
		if(zodiacSign.equals("Sagittarius")){
			return zodiacIcon = R.drawable.sign_sagittarius;
		}
		if(zodiacSign.equals("Capricorn")){
			return zodiacIcon = R.drawable.sign_capricorn;
		}
		if(zodiacSign.equals("Aquarius")){
			return zodiacIcon = R.drawable.sign_aquarius;
		}
		if(zodiacSign.equals("Pisces")){
			return zodiacIcon = R.drawable.sign_pisces;
		}
		
		
		return zodiacIcon;
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
        TextView houseName;
        TextView houseDegree;
        TextView zodiacName;
        TextView tvDescription;
        Button openDetail;
    }
  }

