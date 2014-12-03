package helper;

import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Custom {
	
	Context context;
	
	public Custom(Context c){
		this.context = c;
	}

	public byte[] image(int id) {
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
		return bos.toByteArray();
	}

}
