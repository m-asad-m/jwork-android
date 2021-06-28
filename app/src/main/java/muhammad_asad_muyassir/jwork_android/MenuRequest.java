package muhammad_asad_muyassir.jwork_android;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Class MenuRequest adalah class yang melakukan http request
 * untuk mendapatkan list semua job
 *
 * @author Muhammad As'ad Muyassir
 * @version 27-06-2021
 */
public class MenuRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/job";

    /**
     * Untuk membuat request semua job
     *
     * @param listener listener request
     */
    public MenuRequest(Response.Listener<String> listener)
    {
        super(Method.GET, URL, listener, null);
    }
}