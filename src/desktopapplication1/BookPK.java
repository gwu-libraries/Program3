/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package desktopapplication1;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Administrator
 */
@Embeddable
public class BookPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "Project_id")
    private int projectid;
    @Basic(optional = false)
    @Column(name = "Barcode")
    private String barcode;

    public BookPK() {
    }

    public BookPK(int projectid, String barcode) {
        this.projectid = projectid;
        this.barcode = barcode;
    }

    public int getProjectid() {
        return projectid;
    }

    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) projectid;
        hash += (barcode != null ? barcode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BookPK)) {
            return false;
        }
        BookPK other = (BookPK) object;
        if (this.projectid != other.projectid) {
            return false;
        }
        if ((this.barcode == null && other.barcode != null) || (this.barcode != null && !this.barcode.equals(other.barcode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "desktopapplication1.BookPK[projectid=" + projectid + ", barcode=" + barcode + "]";
    }

}
