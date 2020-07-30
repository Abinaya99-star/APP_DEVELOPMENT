package com.example.mini;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
public class RegAct extends Fragment {
    ViewFlipper vflipper;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        Toast.makeText(getActivity(),"Welcome",Toast.LENGTH_LONG).show();
        View v= inflater.inflate(R.layout.fragment_home, container, false);
        int images[]={R.drawable.pic,R.drawable.picc,R.drawable.piccc};
        vflipper=(ViewFlipper)v.findViewById(R.id.vflipper);
        for (int image:images)
        {
            flipperimage(image);
        }
        return v;
    }
    public void flipperimage(int image)
    {
        ImageView iv=new ImageView(getActivity());
        iv.setBackgroundResource(image);
        vflipper.addView(iv);
        vflipper.setFlipInterval(1000);
        vflipper.setAutoStart(true);
        vflipper.setInAnimation(getActivity(),android.R.anim.slide_in_left);
        vflipper.setOutAnimation(getActivity(),android.R.anim.slide_out_right);
    }
}


