package com.napworks.screenlock.adapterPackage;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.View;
import android.widget.TextView;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.napworks.screenlock.R;
import com.napworks.screenlock.interfacePackage.AppLockInterface;
import com.napworks.screenlock.modelPackage.AppDetailsModel;
import com.napworks.screenlock.utilPackage.ApkInfoExtractor;
import com.napworks.screenlock.utilPackage.CommonMethods;

import java.util.ArrayList;
import java.util.List;

public class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.ViewHolder>{

    Context context1;
    ArrayList<AppDetailsModel> appList;
    AppLockInterface  appLockClick;

    public AppsAdapter(Context context, ArrayList<AppDetailsModel> list , AppLockInterface  onAppLockClick){

        context1 = context;

        appList = list;

        appLockClick = onAppLockClick;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public CardView cardView;
        public ImageView imageView;
        public ImageView lockImage;
        public TextView textView_App_Name;
        public TextView textView_App_Package_Name;

        public ViewHolder (View view){

            super(view);

            cardView = (CardView) view.findViewById(R.id.card_view);
            imageView = (ImageView) view.findViewById(R.id.imageview);
            lockImage = (ImageView) view.findViewById(R.id.lockImage);
            textView_App_Name = (TextView) view.findViewById(R.id.Apk_Name);
            textView_App_Package_Name = (TextView) view.findViewById(R.id.Apk_Package_Name);
        }
    }

    @Override
    public AppsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View view2 = LayoutInflater.from(context1).inflate(R.layout.cardview_layout,parent,false);

        ViewHolder viewHolder = new ViewHolder(view2);

        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position){

        ApkInfoExtractor apkInfoExtractor = new ApkInfoExtractor(context1);

        final String ApplicationPackageName =  appList.get(position).getPackageName();
        String ApplicationLabelName =   apkInfoExtractor.GetAppName(ApplicationPackageName);
        Drawable drawable = apkInfoExtractor.getAppIconByPackageName(ApplicationPackageName);

        if(ApplicationLabelName.equals("Automation Test"))
        {
            Log.e("Data ","drawable  => " + drawable);
        }

        viewHolder.textView_App_Name.setText(ApplicationLabelName);
        viewHolder.textView_App_Package_Name.setText(ApplicationPackageName);
        viewHolder.imageView.setImageDrawable(drawable);

        if(appList.get(position).isSelect() == 1)
        {
            viewHolder.lockImage.setColorFilter(context1.getColor(R.color.purple_700));
        }
        else
        {
            viewHolder.lockImage.setColorFilter(context1.getColor(R.color.black));
        }

        viewHolder.lockImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Toast.makeText(context1,ApplicationPackageName + " Clicked Lock", Toast.LENGTH_LONG).show();
                appLockClick.onAppLockClick("1" ,appList.get(viewHolder.getAdapterPosition()),viewHolder.getAdapterPosition());
//                Intent intent = context1.getPackageManager().getLaunchIntentForPackage(ApplicationPackageName);
//                if(intent != null)
//                {
//                    context1.startActivity(intent);
//                }
//                else
//                {
//
//                    Toast.makeText(context1,ApplicationPackageName + " Error, Please Try Again.", Toast.LENGTH_LONG).show();
//                }
            }
        });
    }

    @Override
    public int getItemCount()
    {

        return appList.size();
    }
}