package navi.com.columbus.DataModel;

import java.util.List;

public class Route
{

    private String name;
    private String description;
    private double length;
    private boolean finished;
    private List<Monument> monumentList;

    public Route(Builder builder)
    {
        this.name = builder.name;
        this.length = builder.length;
        this.description = builder.description;
        this.finished = builder.finished;
        this.monumentList = builder.monumentList;
    }

    public static class Builder
    {
        private String name;
        private String description;
        private double length;
        private List<Monument> monumentList;
        private boolean finished;

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

        public Builder length(double length)
        {
            this.length = length;
            return this;
        }

        public Builder finished(boolean finished)
        {
            this.finished = finished;
            return this;
        }

        public Builder routeList(List<Monument> routeList)
        {
            this.monumentList = routeList;
            return this;
        }

        public Route build()
        {
            return new Route(this);
        }
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public double getLength()
    {
        return length;
    }

    public void setLength(double length)
    {
        this.length = length;
    }

    public boolean isFinished()
    {
        return finished;
    }

    public void setFinished(boolean finished)
    {
        this.finished = finished;
    }

    public List<Monument> getMonumentList() {return monumentList;}

    public void setMonumentList(List<Monument> monumentList) {this.monumentList = monumentList;}
    @Override
    public String toString()
    {
        return "Route{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", length=" + length +
                ", finished=" + finished +
                ", amount of monuments =" + monumentList.size() +
                '}';
    }
}