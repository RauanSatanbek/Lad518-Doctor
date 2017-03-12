package com.example.rauansatanbek.lab518_task1;

import android.app.ActionBar;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    ArrayList<Info> info;
    Context context;
    interface ShowTimePicker {void showTimePicker(View v, EditText doctorFromTime, EditText doctorToTime, TextView respond);}
    interface RemoveItem {void removeItem(int position);}
    ShowTimePicker showTimePicker;
    RemoveItem removeItem;
    int fragmentId;
    private List<ViewHolder> viewHolders;
    private JSONArray Procedures;
    private JSONArray Districts;
    private JSONArray Cities;
    private JSONArray Specialitites;
    private JSONArray Medtests;

    RecyclerViewAdapter(ArrayList<Info> info, Context context, int fragmentId) {
        this.info = info;
        this.context = context;
        this.fragmentId = fragmentId;
        showTimePicker = (ShowTimePicker) context;
        removeItem = (RemoveItem) context;
        viewHolders = new ArrayList<>();
        try {
            Procedures = new JSONArray(loadJSONFromAsset("Procedures"));
            Districts = new JSONArray(loadJSONFromAsset("Districts"));
            Cities = new JSONArray(loadJSONFromAsset("Cities"));
            Specialitites = new JSONArray(loadJSONFromAsset("Specialitites"));
            Medtests = new JSONArray(loadJSONFromAsset("Medtests"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //loadJSONFromAsset
        public String loadJSONFromAsset(String name) {
            String json = null;
            try {
                InputStream is = context.getAssets().open(name + ".json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
            return json;
        }
    //Search from json
        public JSONObject searchFromJSON(JSONArray array, int id) throws JSONException {
            for(int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                if(object.getInt("id") == id) {
                    return object;
                }
            }
            return new JSONObject();
        }
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //title,  answers,  address,  dateFromUser,  money, ArrayList<String> specialities,   status
        viewHolders.add(holder);
        //procedure
            int procedure_id = info.get(position).procedure;
            try {
                holder.procedure.setText(searchFromJSON(Procedures, procedure_id).getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        //answers_count
          holder.answers_count.setText(info.get(position).answers_count + "\nотзывов");
        //setText to Districts
            int city_id = info.get(position).city;
            String Districts_names = "";
            String coma = ", ";
            try {
                JSONArray districts_array = searchFromJSON(Cities, city_id).getJSONArray("districts");
                for(int i = 0; i < districts_array.length(); i++) {
                    int districts_id = districts_array.getInt(i);
                    if(i + 1 == districts_array.length()) coma = "";
                    Districts_names += searchFromJSON(Districts, districts_id).getString("name") + coma;
                }
            } catch (JSONException e) { e.printStackTrace(); }
            String addressTitle = "<font color='#4E1686'>Район(ы): </font>";
            holder.districts.setText(Html.fromHtml(addressTitle + Districts_names));
        holder.dateFromUser.setText("16 декабрья\n" + info.get(position).time_from + " - " + info.get(position).time_to);
        holder.btn_price.setText(info.get(position).price + "тг");
        //set to every respond position of View
            holder.respond.setHint(position + "");
        //specialities_list
            ArrayList<String> specialities_list = info.get(position).specialities;
            holder.specialities.removeAllViews();
            String specialities_name = "";
            for(int i = 0; i < specialities_list.size(); i++) {
                int specialities_id = Integer.parseInt(specialities_list.get(i));
                try {
                    Log.d("MyLogs", "specialities_id = " + specialities_id + ":" + searchFromJSON(Specialitites, specialities_id).toString());
                    specialities_name = searchFromJSON(Specialitites, specialities_id).getString("name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                TextView textView = new TextView(context);
                textView.setText(specialities_name);
                textView.setBackgroundResource(R.drawable.specialities);
                LinearLayout.LayoutParams llp =  new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
                llp.setMargins(0, 0, 4, 0);
                holder.specialities.addView(textView, llp);
            }
        switch (info.get(position).type) {
            case 0:
                holder.imageView.setImageResource(R.drawable.doctoroncall_hover);
                holder.imageViewText.setText(R.string.doctorhour_hover_text);
                break;
            case 1:
                holder.imageView.setImageResource(R.drawable.doctorhour_hover);
                holder.imageViewText.setText(R.string.doctorhour_hover_text);
                break;
            case 2:
                holder.imageView.setImageResource(R.drawable.medtests_hover);
                holder.imageViewText.setText(R.string.medtests_hover_text);
                break;
            case 3:
                holder.imageView.setImageResource(R.drawable.procedures_hover);
                holder.imageViewText.setText(R.string.procedures_hover_text);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return info.size();
    }
    public Info getItem(int position) {
        return info.get(position);
    }
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView imageViewText, procedure, answers_count, districts, dateFromUser, btn_price,tv_price, waiting,write_message, respond;
        RowLayout specialities;
        EditText doctorToTime, doctorFromTime;

        public ViewHolder(View v) {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.imageView);
            imageViewText = (TextView) v.findViewById(R.id.imageViewText);
            procedure = (TextView) v.findViewById(R.id.procedure);
            answers_count = (TextView) v.findViewById(R.id.answers_count);
            dateFromUser = (TextView) v.findViewById(R.id.dateFromUser);
            btn_price = (TextView) v.findViewById(R.id.btn_price);
            districts = (TextView) v.findViewById(R.id.districts);
            tv_price = (TextView) v.findViewById(R.id.tv_price);
            waiting = (TextView) v.findViewById(R.id.waiting);
            specialities = (RowLayout) v.findViewById(R.id.specialities);
            //ET TV clickable = true
                respond = (TextView) v.findViewById(R.id.respond);
                doctorToTime = (EditText) v.findViewById(R.id.doctorToTime);
                doctorFromTime = (EditText) v.findViewById(R.id.doctorFromTime);
                write_message = (TextView) v.findViewById(R.id.write_message);

                respond.setOnClickListener(this);
                doctorFromTime.setOnClickListener(this);
                doctorToTime.setOnClickListener(this);
                write_message.setOnClickListener(this);
            if(fragmentId == 1) {
                btn_price.setVisibility(View.VISIBLE);
                tv_price.setVisibility(View.GONE);
                waiting.setVisibility(View.GONE);
                write_message.setVisibility(View.GONE);
                v.setOnClickListener(this);
            } else if (fragmentId == 2) {
                btn_price.setVisibility(View.GONE);
                tv_price.setVisibility(View.VISIBLE);
                waiting.setVisibility(View.VISIBLE);
                write_message.setVisibility(View.GONE);
            } else if (fragmentId == 3) {
                btn_price.setVisibility(View.GONE);
                tv_price.setVisibility(View.VISIBLE);
                waiting.setVisibility(View.GONE);
                write_message.setVisibility(View.VISIBLE);
            }
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.doctorFromTime:
                case R.id.doctorToTime:
                    showTimePicker.showTimePicker(v, doctorFromTime, doctorToTime, respond);
                    break;
                case R.id.write_message:
                    break;
                case R.id.respond:
                    Log.d("MyLogs", "" + respond.getLinksClickable());
                    if(respond.getLinksClickable()) {
                        int position = Integer.parseInt(respond.getHint().toString());
                        for (int i = position + 1; i < viewHolders.size(); i++) {
                            int j = Integer.parseInt(viewHolders.get(i).respond.getHint().toString());
                            viewHolders.get(i).respond.setHint((j - 1) + "");
                        }
                        viewHolders.remove(position);
                        removeItem.removeItem(position);
                    }
                    break;
                default:
                    RelativeLayout rl_time = (RelativeLayout) v.findViewById(R.id.rl_time);
                    Animation anim = AnimationUtils.loadAnimation(context, R.anim.show_scale);
                    Log.d("MyLogs", "HI");
                    if(rl_time.getVisibility() == View.GONE) {
                        rl_time.startAnimation(anim);
                        rl_time.setVisibility(View.VISIBLE);
                    } else {
                        rl_time.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    }
}
