package muhammad_asad_muyassir.jwork_android;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Class BonusRequest adalah class yang melakukan http request
 * untuk mendapatkan data bonus
 *
 * @author Muhammad As'ad Muyassir
 * @version 27-06-2021
 */
public class BonusRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/bonus/ref/";

    /**
     * Untuk membuat request bonus
     *
     * @param referralCode  kode referral
     * @param listener      listener request
     * @param errorListener error listener request
     */
    public BonusRequest(String referralCode, Response.Listener<String> listener, Response.ErrorListener errorListener)
    {
        super(Method.GET, URL+referralCode, listener, errorListener);
    }
}
