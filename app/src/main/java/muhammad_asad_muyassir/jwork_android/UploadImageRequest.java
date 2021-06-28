package muhammad_asad_muyassir.jwork_android;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Class UploadImageRequest adalah class yang melakukan http request
 * untuk mengupload gambar
 *
 * @author Muhammad As'ad Muyassir
 * @version 27-06-2021
 */
public class UploadImageRequest extends Request<String> {
    private static final String URL = "http://10.0.2.2:8080/img/";
    private final Response.Listener<String> listener;
    byte[] byteStream;
    String contentType;

    /**
     * Untuk membuat request upload gambar
     *
     * @param filePath      path tempat file di server
     * @param fileName      nama file di server
     * @param byteStream    byte seluruh data gambar
     * @param contentType   tipe konten/gambar yang dikirim
     * @param listener      listener request
     * @param errorListener error listener request
     */
    public UploadImageRequest(String filePath, String fileName, byte[] byteStream, String contentType, Response.Listener<String> listener, Response.ErrorListener errorListener)
    {
        super(Method.POST, URL + filePath + '/' + fileName, errorListener);
        this.listener = listener;
        this.byteStream = byteStream;
        this.contentType = contentType;
    }

    /**
     * Untuk melakukan return tipe konten
     *
     * @return string tipe konten
     */
    @Override
    public String getBodyContentType()
    {
        return contentType;
    }

    /**
     * Untuk melakukan return data yang akan dimasukkan
     * dalam http body
     *
     * @return byte http body
     */
    @Override
    public byte[] getBody()
    {
        return byteStream;
    }

    /**
     * Untuk melakukan return response yang diterima
     *
     * @param response  objek NetworkResponse yang diterima
     * @return          objek Response yang telah diproses
     */
    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response)
    {
        try {
            return Response.success(new String(response.data, HttpHeaderParser.parseCharset(response.headers)), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Untuk meghubungkan listener supaya bisa mendapatkan
     * response dari server
     *
     * @param response  String response yang diterima
     */
    @Override
    protected void deliverResponse(String response)
    {
        listener.onResponse(response);
    }
}
