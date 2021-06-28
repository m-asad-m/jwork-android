package muhammad_asad_muyassir.jwork_android;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Class SharedPrefManager adalah class yang menyimpan
 * data statis ke lokal database
 *
 * @author Muhammad As'ad Muyassir
 * @version 27-06-2021
 */
public class SharedPrefManager {
    public static final String SP_JWORK = "JWork";
    public static final String SP_ID = "spId";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    /**
     * Constructor untuk objek dari class SharedPrefManager
     *
     * @param context   conteks dari activity
     */
    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(SP_JWORK, Context.MODE_PRIVATE);
        spEditor = sp.edit();
        spEditor.apply();
    }

    /**
     * Untuk menyimpan data integer ke dalam lokal database
     *
     * @param keySP key data yang ingin disimpan
     * @param value data yang ingin disimpan
     */
    public void setSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    /**
     * Untuk mengambil data integer pencari pekerjaan dari lokal database
     *
     * @return integer data
     */
    public int getSPInt(String keySP){
        return sp.getInt(keySP, 0);
    }

    /**
     * Untuk mengambil data id pencari pekerjaan dari lokal database
     *
     * @return integer id pencari pekerjaan
     */
    public int getSPId(){
        return getSPInt(SP_ID);
    }
}
