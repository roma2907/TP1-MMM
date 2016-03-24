package ramage.istic.fr.firstapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by ramage on 18/01/16.
 */
public class User implements Parcelable {

    private String id;
    private String name;
    private String lastname;
    private String date;
    private String city;
    private String department;

    public User(String id,String name,String lastName,String date,String city,String department){
        this.id=id;
        this.name=name;
        this.lastname=lastName;
        this.date=date;
        this.city=city;
        this.department=department;
    }

    public User(String name,String lastName,String date,String city,String department){
        this.name=name;
        this.lastname=lastName;
        this.date=date;
        this.city=city;
        this.department=department;
    }

    protected User(Parcel in) {
        id = in.readString();
        name = in.readString();
        lastname = in.readString();
        date = in.readString();
        city = in.readString();
        department = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getLastname() {
        return lastname;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getCity() {
        return city;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public int describeContents() {
        return 0;

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(lastname);
        dest.writeString(date);
        dest.writeString(city);
        dest.writeString(department);
    }
}
