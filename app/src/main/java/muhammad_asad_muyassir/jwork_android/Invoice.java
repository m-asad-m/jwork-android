package muhammad_asad_muyassir.jwork_android;

import java.util.ArrayList;

/**
 * Class Invoice adalah class yang menyimpan data invoice
 *
 * @author Muhammad As'ad Muyassir
 * @version 27-05-2021
 */
public class Invoice {
    private int id;
    private ArrayList<String> jobsName;
    private ArrayList<Integer> jobsFee;
    private String date;
    protected int totalFee;
    private String jobseekerName;
    private String paymentType;
    private String invoiceStatus;
    private String referralCode;

    /**
     * Constructor untuk objek dari class Invoice
     * @param id            id invoice
     * @param jobsName      array list nama pekerjaan
     * @param date          tanggal invoice
     */
    public Invoice(int id, ArrayList<String> jobsName, ArrayList<Integer> jobsFee, String date, int totalFee, String jobseekerName, String paymentType, String invoiceStatus)
    {
        this.id = id;
        this.jobsName = jobsName;
        this.jobsFee = jobsFee;
        this.date = date.substring(0, 10);
        this.totalFee = totalFee;
        this.jobseekerName = jobseekerName;
        this.paymentType = paymentType;
        this.invoiceStatus = invoiceStatus;
    }

    /**
     * metode untuk mendapatkan id invoice
     * @return id invoice
     */
    public int getId()
    {
        return id;
    }

    /**
     * metode untuk mendapatkan nama pekerjaan
     * @return nama pekerjaan
     */
    public ArrayList<String> getJobsName()
    {
        return jobsName;
    }

    /**
     * metode untuk mendapatkan bayaran pekerjaan
     * @return bayaran pekerjaan
     */
    public ArrayList<Integer> getJobsFee()
    {
        return jobsFee;
    }

    /**
     * metode untuk mendapatkan tanggal dibuat invoice
     * @return tanggal invoice dibuat
     */
    public String getDate()
    {
        return date;
    }

    /**
     * metode untuk mendapatkan total bayaran
     * @return total bayaran
     */
    public int getTotalFee()
    {
        return totalFee;
    }

    /**
     * metode untuk mendapatkan data pekerja
     * @return objek pekerja
     */
    public String getJobseekerName()
    {
        return jobseekerName;
    }

    /**
     * metode untuk mendapatkan data tipe pembayaran
     * @return objek tipe pembayaran
     */
    public String getPaymentType()
    {
        return paymentType;
    }

    /**
     * metode untuk mendapatkan data status invoice
     * @return objek status invoice
     */
    public String getInvoiceStatus()
    {
        return invoiceStatus;
    }


    /**
     * metode untuk mendapatkan string referral code
     * @return string referral code
     */
    public String getReferralCode()
    {
        return referralCode;
    }

    /**
     * metode untuk mengubah id invoice
     * @param id id invoice
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * metode untuk mengubah nama pekerjaan
     * @param jobsName array list nama pekerjaan
     */
    public void setJobsName(ArrayList<String> jobsName)
    {
        this.jobsName = jobsName;
    }

    /**
     * metode untuk mengubah bayaran pekerjaan
     * @param jobsFee array list bayaran pekerjaan
     */
    public void setJobsFee(ArrayList<Integer> jobsFee)
    {
        this.jobsFee = jobsFee;
    }

    /**
     * metode untuk mengubah tanggal invoice
     * @param date string gregorian calendar tanggal invoice
     */
    public void setDate(String date)
    {
        this.date = date;
    }

    /**
     * metode untuk mengubah total bayaran
     * @param totalFee total bayaran
     */
    public void setTotalFee(int totalFee)
    {
        this.totalFee = totalFee;
    }

    /**
     * metode untuk mengubah data pekerja
     * @param jobseekerName string pekerja
     */
    public void setJobseekerName(String jobseekerName)
    {
        this.jobseekerName = jobseekerName;
    }

    /**
     * metode untuk mengubah tipe pembayaran
     * @param paymentType string tipe pembayaran
     */
    public void setPaymentType(String paymentType)
    {
        this.paymentType = paymentType;
    }

    /**
     * metode untuk mengubah data status invoice
     * @param invoiceStatus string status invoice
     */
    public void setInvoiceStatus(String invoiceStatus)
    {
        this.invoiceStatus = invoiceStatus;
    }

    /**
     * metode untuk mengubah data referral code
     * @param referralCode string referral code
     */
    public void setReferralCode(String referralCode)
    {
        this.referralCode = referralCode;
    }
}
