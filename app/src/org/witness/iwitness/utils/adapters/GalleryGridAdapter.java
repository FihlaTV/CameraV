package org.witness.iwitness.utils.adapters;

import java.util.List;

import org.json.JSONException;
import org.witness.informacam.models.media.IMedia;
import org.witness.informacam.utils.Constants.App;
import org.witness.informacam.utils.Constants.Models;
import org.witness.iwitness.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GalleryGridAdapter extends BaseAdapter {
	List<? super IMedia> media;
	LayoutInflater li;
	Activity a;

	private final static String LOG = App.LOG;

	public GalleryGridAdapter(Activity a, List<? super IMedia> media) throws NullPointerException {
		this.media = media;
		this.a = a;
		li = LayoutInflater.from(a);
	}

	@Override
	public int getCount() {
		return media.size();
	}

	@Override
	public Object getItem(int position) {
		return media.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		IMedia m = (IMedia) media.get(position);
		View view = li.inflate(R.layout.adapter_gallery_grid, null);
		
		ImageView iv = (ImageView) view.findViewById(R.id.gallery_thumb);
		LinearLayout iv_holder = (LinearLayout) view.findViewById(R.id.gallery_thumb_holder);
		if(m.isNew) {
			iv_holder.setBackgroundDrawable(a.getResources().getDrawable(R.drawable.extras_is_new_background));
		}

		try {
			Bitmap bitmap = m.getBitmap(m.bitmapThumb);
			iv.setImageBitmap(bitmap);
		} catch(NullPointerException e) {
			iv.setImageDrawable(a.getResources().getDrawable(R.drawable.ic_action_video));
		}

		try {
			if(!m.getBoolean(Models.IMediaManifest.Sort.IS_SHOWING)) {
				view.setVisibility(View.GONE);
			}
		} catch (JSONException e) {
			Log.e(LOG, e.toString());
			e.printStackTrace();
		}

		return view;
	}

}