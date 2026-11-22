package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ParceledListSlice<T extends Parcelable> extends BaseParceledListSlice<T> {
    public static final Parcelable.ClassLoaderCreator<ParceledListSlice> CREATOR = new Parcelable.ClassLoaderCreator<ParceledListSlice>() {

        @Override
        public ParceledListSlice createFromParcel(Parcel parcel) {
            return null;
        }

        @Override
        public ParceledListSlice[] newArray(int i) {
            return new ParceledListSlice[0];
        }

        @Override
        public ParceledListSlice createFromParcel(Parcel parcel, ClassLoader classLoader) {
            return null;
        }
    };

    public ParceledListSlice(List<T> list) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
