package ptpn12.amanat.asem.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("user_fullname")
    @Expose
    private String userFullname;
    @SerializedName("user_nip")
    @Expose
    private Integer userNip;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("user_jabatan")
    @Expose
    private String userJabatan;
    @SerializedName("hak_akses_id")
    @Expose
    private Integer hakAksesId;
    @SerializedName("unit_id")
    @Expose
    private Integer unitId;
    @SerializedName("user_pass")
    @Expose
    private String userPass;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("unit_desc")
    @Expose
    private String unitDesc;
    @SerializedName("sub_unit_id")
    @Expose
    private Integer subUnitId;
    @SerializedName("afdeling_id")
    @Expose
    private Integer afdelingId;

    @SerializedName("hak_akses_desc")
    @Expose
    private String hakAksesDesc;

    public Integer getSubUnitId() {
        return subUnitId;
    }

    public void setSubUnitId(Integer subUnitId) {
        this.subUnitId = subUnitId;
    }

    public Integer getAfdelingId() {
        return afdelingId;
    }

    public void setAfdelingId(Integer afdelingId) {
        this.afdelingId = afdelingId;
    }

    public String getHakAksesDesc() {
        return hakAksesDesc;
    }

    public void setHakAksesDesc(String hakAksesDesc) {
        this.hakAksesDesc = hakAksesDesc;
    }

    public String getUnitDesc() {
        return unitDesc;
    }

    public void setUnitDesc(String unitDesc) {
        this.unitDesc = unitDesc;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserFullname() {
        return userFullname;
    }

    public void setUserFullname(String userFullname) {
        this.userFullname = userFullname;
    }

    public Integer getUserNip() {
        return userNip;
    }

    public void setUserNip(Integer userNip) {
        this.userNip = userNip;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserJabatan() {
        return userJabatan;
    }

    public void setUserJabatan(String userJabatan) {
        this.userJabatan = userJabatan;
    }

    public Integer getHakAksesId() {
        return hakAksesId;
    }

    public void setHakAksesId(Integer hakAksesId) {
        this.hakAksesId = hakAksesId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
