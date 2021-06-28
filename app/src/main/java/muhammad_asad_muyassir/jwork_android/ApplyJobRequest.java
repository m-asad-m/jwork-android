package muhammad_asad_muyassir.jwork_android;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Class ApplyJobRequest adalah class yang melakukan http request
 * untuk apply job
 *
 * @author Muhammad As'ad Muyassir
 * @version 27-06-2021
 */
public class ApplyJobRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/invoice/";
    private Map<String, String> params;

    /**
     * Untuk membuat request apply job dengan tipe pembayaran bank
     *
     * @param jobIdList     list id job
     * @param jobseekerId   id jobseeker
     * @param listener      listener request
     * @param errorListener error listener request
     */
    public ApplyJobRequest(String jobIdList, String jobseekerId, Response.Listener<String> listener, Response.ErrorListener errorListener)
    {
        super(Method.POST, URL+"createBankPayment", listener, errorListener);
        params = new HashMap<>();
        params.put("jobIdList", jobIdList);
        params.put("jobseekerId", jobseekerId);
    }

    /**
     * Untuk membuat request apply job dengan tipe pembayaran E-Wallet
     *
     * @param jobIdList     list id job
     * @param jobseekerId   id jobseeker
     * @param referralCode  kode referral
     * @param listener      listener request
     * @param errorListener error listener request
     */
    public ApplyJobRequest(String jobIdList, String jobseekerId, String referralCode, Response.Listener<String> listener, Response.ErrorListener errorListener)
    {
        super(Method.POST, URL+"createEWalletPayment", listener, errorListener);
        params = new HashMap<>();
        params.put("jobIdList", jobIdList);
        params.put("jobseekerId", jobseekerId);
        params.put("referralCode", referralCode);
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
