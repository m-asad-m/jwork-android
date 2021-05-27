package MuhammadAsadMuyassir.jwork_android;

/**
 * Class Job adalah class yang menyimpan data pekerjaan.
 *
 * @author Muhammad As'ad Muyassir
 * @version 27-05-2021
 */
public class Recruiter {
    private int id;
    private String name, email, phoneNumber;
    private Location location;

    /**
     * Constructor untuk objek dari class Recruiter
     * @param id          id recruiter
     * @param name        nama recruiter
     * @param email       email recruiter
     * @param phoneNumber nomor telepon recruiter
     * @param location    lokasi recruiter
     */
    public Recruiter(int id, String name, String email, String phoneNumber, Location location)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.location = location;
    }

    /**
     * metode untuk mendapatkan id recruiter
     * @return id recruiter
     */
    public int getId()
    {
        return id;
    }

    /**
     * metode untuk mendapatkan nama recruiter
     * @return nama recruiter
     */
    public String getName()
    {
        return name;
    }

    /**
     * metode untuk mendapatkan email recruiter
     * @return email recruiter
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * metode untuk mendapatkan nomor telepon recruiter
     * @return nomor telepon recruiter
     */
    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    /**
     * metode untuk mendapatkan lokasi recruiter
     * @return objek lokasi recruiter
     */
    public Location getLocation()
    {
        return location;
    }

    /**
     * metode untuk mengubah id recruiter
     * @param id id recruiter
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * metode untuk mengubah nama recruiter
     * @param name nama recruiter
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * metode untuk mengubah email recruiter
     * @param email email recruiter
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * metode untuk mengubah nomor telepon recruiter
     * @param phoneNumber nomor telepon recruiter
     */
    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    /**
     * metode untuk mengubah lokasi recruiter
     * @param location objek lokasi recruiter
     */
    public void setLocation(Location location)
    {
        this.location = location;
    }
}
