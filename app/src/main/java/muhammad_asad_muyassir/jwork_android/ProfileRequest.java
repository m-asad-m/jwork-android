package muhammad_asad_muyassir.jwork_android;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Class ProfileRequest adalah class yang melakukan http request
 * untuk mendapatkan data profile pencari pekerjaan
 *
 * @author Muhammad As'ad Muyassir
 * @version 27-06-2021
 */
public class ProfileRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/jobseeker/";

    /**
     * Untuk membuat request data pencari pekerjaan
     *
     * @param id            id pencari pekerjaan
     * @param listener      listener request
     * @param errorListener error listener request
     */
    public ProfileRequest(int id, Response.Listener<String> listener, Response.ErrorListener errorListener)
    {
        super(Request.Method.POST, URL + id, listener, errorListener);
    }
}
