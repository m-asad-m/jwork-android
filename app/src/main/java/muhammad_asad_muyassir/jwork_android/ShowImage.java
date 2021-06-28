package muhammad_asad_muyassir.jwork_android;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Class ShowImage adalah class yang digunakan untuk
 * menampilkan gambar dari URL
 *
 * @author Muhammad As'ad Muyassir
 * @version 27-06-2021
 */
public class ShowImage extends AsyncTask<String, Void, Bitmap> {
    private ImageView imageView;
    private int defaultDrawable;
    private final String url;

    /**
     * Constructor untuk objek dari class ShowImage
     *
     * @param imageView         objek ImageView yang ingin diubah gambarnya
     * @param filePath          path tempat file di server
     * @param fileName          nama file di server
     * @param defaultDrawable   id drawable untuk gambar default
     */
    public ShowImage(ImageView imageView, String filePath, String fileName, int defaultDrawable) {
        url = "http://10.0.2.2:8080/img/" + filePath + '/' + fileName;
        this.imageView = imageView;
        this.defaultDrawable = defaultDrawable;
    }

    /**
     * Untuk memulai proses pengambilan gambar dan menampilkannya
     */
    public void show() {
        execute(url);
    }

    /**
     * Untuk mengambil gambar dari URL dan menyimpannya dalam objek Bitmap
     *
     * @param urls  link url yang ingin diambil gambarnya
     * @return      objek Bitmap gambar
     */
    @Override
    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    /**
     * Untuk menampilkan gambar saat gambar sudah tersedia
     *
     * @param result    objek Bitmap gambar
     */
    @Override
    protected void onPostExecute(Bitmap result) {
        if(result != null) {
            imageView.setImageBitmap(result);
        } else {
            imageView.setImageResource(defaultDrawable);
        }
    }
}
