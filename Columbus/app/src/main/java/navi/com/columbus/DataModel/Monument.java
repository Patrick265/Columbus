package navi.com.columbus.DataModel;

public class Monument
{
    private String name;
    private String description;
    private String creator;
    private String soundURL;
    private String imageURL;
    private String longitude;
    private String latitude;

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
    }

    public static class Builder {
        private String name;
        private String description;
        private String creator;
        private String soundURL;
        private String imageURL;
        private String longitude;
        private String latitude;
        private int constructionYear;
        private boolean isVisited;

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

        public Builder longitude(String longitude)
        {
            this.longitude = longitude;
            return this;
        }

        public Builder latitude(String latitude)
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

        public Monument build()
        {
            return new Monument(this);
        }
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

    public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
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

    public String getLongitude()
    {
        return longitude;
    }

    public String getLatitude()
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
                "name='" + name + '\'' +
                ", constructionYear=" + constructionYear +
                ", description='" + description + '\'' +
                ", creator='" + creator + '\'' +
                ", isVisited=" + isVisited +
                ", soundURL='" + soundURL + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
