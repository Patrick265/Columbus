package navi.com.columbus.DataModel;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.Date;

public class Monument implements Serializable
{
    private int id;
    private String name;
    private String description;
    private String creator;
    private String soundURL;
    private String imageURL;
    private double longitude;
    private double latitude;

    private int constructionYear;
    private boolean isVisited;


    public Monument(Builder builder)
    {
        this.name = builder.name;
        this.description = builder.description;
        this.creator = builder.creator;
        this.soundURL = builder.soundURL;
        this.imageURL = builder.imageURL;
        this.longitude = builder.longitude;
        this.latitude = builder.latitude;
        this.constructionYear = builder.constructionYear;
        this.isVisited = builder.isVisited;
        this.id = builder.id;
    }

    public static class Builder {
        private String name;
        private String description;
        private String creator;
        private String soundURL;
        private String imageURL;
        private double longitude;
        private double latitude;
        private int constructionYear;
        private boolean isVisited;
        private int id;
        public Builder name(String name)
        {
            this.name = name;
            return this;
        }

        public Builder description(String description)
        {
            this.description = description;
            return this;
        }

        public Builder creator(String creator)
        {
            this.creator = creator;
            return this;
        }

        public Builder soundURL(String soundURL)
        {
            this.soundURL = soundURL;
            return this;
        }

        public Builder imageURL(String imageURL)
        {
            this.imageURL = imageURL;
            return this;
        }

        public Builder longitude(double longitude)
        {
            this.longitude = longitude;
            return this;
        }

        public Builder latitude(double latitude)
        {
            this.latitude = latitude;
            return this;
        }

        public Builder constructionYear(int constructionYear)
        {
            this.constructionYear = constructionYear;
            return this;
        }

        public Builder isVisited(boolean isVisited)
        {
            this.isVisited = isVisited;
            return this;
        }

        public Builder id(int id)
        {
            this.id = id;
            return this;
        }
        public Monument build()
        {
            return new Monument(this);
        }
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setCreator(String creator)
    {
        this.creator = creator;
    }

    public void setSoundURL(String soundURL)
    {
        this.soundURL = soundURL;
    }

    public void setImageURL(String imageURL)
    {
        this.imageURL = imageURL;
    }

    public void setLongitude(double longitude)
    {
        if(longitude <180.0 && longitude >-180.0)
        {
            this.longitude = longitude;
        }
    }

    public void setLatitude(double latitude)
    {
        if(latitude < 90.0 && latitude > -90.0)
        {
            this.latitude = latitude;
        }
    }

    public void setConstructionYear(int constructionYear)
    {
        this.constructionYear = constructionYear;
    }

    public void setVisited(boolean visited)
    {
        isVisited = visited;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public String getCreator()
    {
        return creator;
    }

    public String getSoundURL()
    {
        return soundURL;
    }

    public String getImageURL()
    {
        return imageURL;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public int getConstructionYear()
    {
        return constructionYear;
    }

    public boolean isVisited()
    {
        return isVisited;
    }

    @Override
    public String toString()
    {
        return "Monument{" +
                "id= "+ id +
                ", name='" + name + '\'' +
                ", constructionYear=" + constructionYear +
                ", description='" + description + '\'' +
                ", creator='" + creator + '\'' +
                ", isVisited=" + isVisited +
                ", soundURL='" + soundURL + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
