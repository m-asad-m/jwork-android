package MuhammadAsadMuyassir.jwork_android;

/**
 * Class Job adalah class yang menyimpan data pekerjaan.
 *
 * @author Muhammad As'ad Muyassir
 * @version 27-05-2021
 */
public class Location {
    private String province, city, description;

    /**
     * Constructor untuk objek dari class Location
     * @param province    nama provinsi
     * @param city        nama kota
     * @param description deskripsi lokasi
     */
    public Location(String province, String city, String description)
    {
        this.province = province;
        this.city = city;
        this.description = description;
    }

    /**
     * metode untuk mendapatkan nama provinsi
     * @return nama provinsi dari objek
     */
    public String getProvince()
    {
        return province;
    }

    /**
     * metode untuk mendapatkan nama kota
     * @return nama kota dari objek
     */
    public String getCity()
    {
        return city;
    }

    /**
     * metode untuk mendapatkan deskripsi dari lokasi
     * @return deskripsi dari objek
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * metode untuk mengubah nama provinsi dari lokasi
     * @param province nama provinsi
     */
    public void setProvince(String province)
    {
        this.province = province;
    }

    /**
     * metode untuk mengubah nama kota dari lokasi
     * @param city nama kota
     */
    public void setCity(String city)
    {
        this.city = city;
    }

    /**
     * metode untuk mengubah deskripsi dari lokasi
     * @param description deskripsi lokasi
     */
    public void setDescription(String description)
    {
        this.description = description;
    }
}
