package navi.com.columbus.DataModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Route implements Serializable
{

    private String name;
    private String description;
    private double length;
    private boolean finished;
    private ArrayList<Monument> monumentList;

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
        private ArrayList<Monument> monumentList;
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

        public Builder routeList(ArrayList<Monument> routeList)
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

    public ArrayList<Monument> getMonumentList() {return monumentList;}

    public void setMonumentList(ArrayList<Monument> monumentList) {this.monumentList = monumentList;}
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