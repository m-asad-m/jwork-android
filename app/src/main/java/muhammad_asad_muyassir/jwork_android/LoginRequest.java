package muhammad_asad_muyassir.jwork_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Class LoginRequest adalah class yang melakukan http request
 * untuk login pencari pekerjaan
 *
 * @author Muhammad As'ad Muyassir
 * @version 27-06-2021
 */
public class LoginRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/jobseeker/login";
    private Map<String, String> params;

    /**
     * Untuk membuat request login
     *
     * @param email         email pencari pekerjaan
     * @param password      password pencari pekerjaan
     * @param listener      listener request
     * @param errorListener error listener request
     */
    public LoginRequest(String email, String password, Response.Listener<String> listener, Response.ErrorListener errorListener)
    {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
    }

    /**
     * Untuk melakukan return seluruh parameter yang ingin dikirimkan
     *
     * @return Map dari parameter
     */
    @Override
    protected Map<String, String> getParams() throws AuthFailureError
    {
        return params;
    }
}
