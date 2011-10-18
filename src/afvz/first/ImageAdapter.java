package afvz.first;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
    		R.drawable.btn1, R.drawable.btn2, R.drawable.btn3, R.drawable.btn4, R.drawable.btn5,
    		R.drawable.btn6, R.drawable.btn7, R.drawable.btn8, R.drawable.btn9, R.drawable.btn10,
    		R.drawable.btn11, R.drawable.btn12, R.drawable.btn13, R.drawable.btn14, R.drawable.btn15,
    		R.drawable.btn16, R.drawable.btn17, R.drawable.btn18, R.drawable.btn19, R.drawable.btn20,
    		R.drawable.btn21, R.drawable.btn22, R.drawable.btn23, R.drawable.btn24, 
    };
}
