package muhammad_asad_muyassir.jwork_android;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Class JobFetchRequest adalah class yang melakukan http request
 * untuk mendapatkan data pekerjaan
 *
 * @author Muhammad As'ad Muyassir
 * @version 27-06-2021
 */
public class JobFetchRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/invoice/jobseeker/";

    /**
     * Untuk membuat request job
     *
     * @param jobseekerID   id jobseeker
     * @param listener      listener request
     */
    public JobFetchRequest(int jobseekerID, Response.Listener<String> listener)
    {
        super(Method.GET, URL+jobseekerID, listener, null);
    }
}
