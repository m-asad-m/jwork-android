package muhammad_asad_muyassir.jwork_android;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Class JobStatusRequest adalah class yang melakukan http request
 * untuk mengubah status invoice
 *
 * @author Muhammad As'ad Muyassir
 * @version 27-06-2021
 */
public class JobStatusRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/invoice/invoiceStatus/";
    private Map<String, String> params;

    /**
     * Untuk membuat request ubah status job
     *
     * @param id            invoice id
     * @param status        status invoice
     * @param listener      listener request
     * @param errorListener error listener request
     */
    public JobStatusRequest(String id, String status, Response.Listener<String> listener, Response.ErrorListener errorListener)
    {
        super(Method.PUT, URL+id, listener, errorListener);
        params = new HashMap<>();
        params.put("status", status);
    }

    /**
     * Untuk melakukan return seluruh parameter yang ingin dikirimkan
     *
     * @return Map dari parameter
     */
    @Override
    protected Map<String, String> getParams()
    {
        return params;
    }
}
